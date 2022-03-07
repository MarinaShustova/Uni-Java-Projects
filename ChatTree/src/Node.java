import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Node extends Thread {
    private int parent_port;        //Do I really need them??
    private InetAddress parent_IP;
    private boolean is_root;

    private DatagramSocket socket_r = null;
    private DatagramSocket socket_s = null;

    private String name = "";
    private int loss;
    private int port;
    private boolean was_added = false;
    private boolean contains;
    private ArrayList<Message> messages = new ArrayList<>();
    private int messages_len = 15;
    private HashMap<Message, Integer> to_send = new HashMap<>();
    private HashMap<Integer, String> users = new HashMap<>();
    private UUID my_uuid;
    private int my_hash;
    private ArrayList<Pair> family = new ArrayList<>();
    private byte[] buf_r = new byte[50], buf_s, recv_byte;
    private ByteBuffer int_buff = ByteBuffer.allocate(Integer.BYTES);
    private int expected_len = 5, actual_len = 0;

    Node(String n, int l, int p){
        name = n;
        loss = l;
        port = p;
        my_uuid = UUID.randomUUID();
        my_hash = my_uuid.hashCode();
        is_root = true;
        users.put(my_hash, "me");
        start();
    }

    Node(String n, int l, int p, int pp, InetAddress pIP){
        name = n;
        loss = l;
        port = p;
        parent_port = pp;
        parent_IP = pIP;
        my_uuid = UUID.randomUUID();
        my_hash = my_uuid.hashCode();
        is_root = false;
        Pair pair = new Pair();
        pair.address = parent_IP;
        pair.port = parent_port;
        family.add(pair);
        users.put(my_hash, "me");
        start();
    }

    public void send(String message) throws IOException {
        for (Pair p : family){
            Message m = new Message();
            m.is_valid = true;
            m.is_this_ack = false;
            m.message = message;
            m.user_hash = my_hash;
            m.message_hash = (message + Integer.toString(my_hash)).hashCode();
            m.addr_dst = p;
            Pair pp = new Pair();
            pp.address = InetAddress.getLocalHost();//socket_r.getInetAddress();
            pp.port = socket_r.getLocalPort();
            m.addr_src = pp;
            if(!was_added) {
                messages.add(m);
                was_added = true;
            }
            to_send.put(m,0);
        }
        was_added = false;
        Sender sender = new Sender();
        for (Pair disconnected : sender.update(socket_s, to_send)){
            family.remove(disconnected);
            System.out.println("User from "+disconnected.address+"\\"+disconnected.port+" left");
        }
    }

    public void recieve() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf_r, buf_r.length);
        socket_r.receive(packet);
        recv_byte = packet.getData();
        actual_len = recv_byte.length;
        while (actual_len < expected_len){
            socket_r.receive(packet);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write(recv_byte);
            outputStream.write(packet.getData());
            recv_byte = outputStream.toByteArray();
            actual_len = recv_byte.length;
        }

        Receiver rec = new Receiver(loss);
        Message m = rec.update(recv_byte, packet.getAddress());

        if(m.is_valid) {
            if (m.is_this_ack){
                for(Message mm : to_send.keySet()){
                    if((m.message_hash == mm.message_hash)&&((m.addr_src.address.toString().equals( mm.addr_dst.address.toString()))&&(m.addr_src.port == mm.addr_dst.port))){
                        to_send.remove(mm);
                        break;
                    }
                }
            }
            else{
                for(Message mm:messages) {
                    contains = mm.message_hash == m.message_hash;
                }
                if (!contains) {
                    if (messages.size() < messages_len)
                        messages.add(m);
                    else {
                        messages.remove(0);
                        messages.add(m);
                    }
                    if (!contains(m.addr_src)) {
                        family.add(m.addr_src);
//                    System.out.println("New family member:"+m.user_hash+" from "+m.addr_src.address+"\\"+m.addr_src.port);
                    }
                    if (!users.containsKey(m.user_hash)) {
                        users.put(m.user_hash, "User" + (users.size() + 1));
                    }
                        System.out.println("[" + users.get(m.user_hash) + "]:" + m.message);
                }
                send_ack(m.message_hash, m.addr_src);
                for (Pair p : family){
                    if(!((m.addr_src.address.toString().equals( p.address.toString()))&&(m.addr_src.port == p.port))){
                        Message nm = new Message();
                        nm.is_valid = true;
                        nm.is_this_ack = false;
                        nm.message = m.message;
                        nm.user_hash = m.user_hash;
                        nm.message_hash = m.message_hash;
                        nm.addr_dst = p;
                        Pair pp = new Pair();
                        pp.address = InetAddress.getLocalHost();//socket_r.getInetAddress();
                        pp.port = socket_r.getLocalPort();
                        nm.addr_src = pp;
                        to_send.put(nm,0);
                    }
                }
                Sender sender = new Sender();
                sender.update(socket_s, to_send);
            }
        }

        Arrays.fill(recv_byte, (byte)0);
    }

    @Override
    public void run(){
        try {
            socket_r = new DatagramSocket(port);
            socket_s = new DatagramSocket();
//            send(name);
            new Thread(()->{
                try {
                    while(true) {
                        sleep(500);
                        Sender sender = new Sender();
                        for (Pair disconnected : sender.update(socket_s, to_send)) {
                            family.remove(disconnected);
                            System.out.println("User from " + disconnected.address + "\\" + disconnected.port + " left");
                        }
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }).start();

            new Thread(() -> {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String userWord;
                    try {
                        userWord = consoleReader.readLine();
                        if (!userWord.isEmpty()) {
                            if (userWord.equals("/list")) {
                                for (Message m:messages){
                                    System.out.println("["+users.get(m.user_hash)+"]:"+m.message);
                                }
                                break;
                            } else {
                                send(userWord);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

            while(true) {
                try {
                    recieve();
                }catch (SocketTimeoutException ee){
                    ee.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            socket_r.close();
            socket_s.close();
        }
    }

    public boolean contains(Pair p){
        for (Pair pp : family){
            if ((pp.address.toString().equals( p.address.toString()))&&(pp.port == p.port)) {
                return true;
            }
        }
        return false;
    }

    public void send_ack(int msg_hash, Pair p) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

        int_buff.clear();
        int_buff.putInt(-1);                   //it is ack
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(socket_r.getLocalPort());
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(my_hash);
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(0);                     //msg len
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(msg_hash);
        outputStream.write(int_buff.array());

        buf_s = outputStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(buf_s, buf_s.length, p.address, p.port);
        socket_s.send(packet);
    }
}
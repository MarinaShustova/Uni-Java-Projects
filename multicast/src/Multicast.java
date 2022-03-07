import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Multicast {
    private MulticastSocket socket_r = null;
    private DatagramSocket socket_s = null;
    private byte[] buf_r = new byte[10], buf_s, recv_byte;
    private InetAddress group = null;
    private HashMap<SocketAddress, Long> joined_add = new HashMap<>();
    private ArrayList<SocketAddress> disconnected = new ArrayList<>();
    private int expected_len = 6, actual_len = 0;
    private long ttl = 10;

    public void renew(){
        Date date = new Date();
        for(SocketAddress key : joined_add.keySet()){
            if((date.getTime() - joined_add.get(key))>5000*joined_add.size()){
       //         System.out.println(date.getTime() - joined_add.get(key));
                disconnected.add(key);
            }
        }
        for(SocketAddress k:disconnected){
            System.out.println(k.toString()+" disconnected");
            joined_add.remove(k);
        }
        disconnected.clear();
        return;
    }
    public void recieve() throws SocketTimeoutException, IOException {
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
            String received = new String(recv_byte, 0, recv_byte.length);
            Arrays.fill(recv_byte, (byte)0);
            if (!joined_add.containsKey(packet.getSocketAddress())) {
                System.out.println(received + " from " + packet.getSocketAddress().toString());
                Date date = new Date();
                joined_add.put(packet.getSocketAddress(), date.getTime());
//                for(SocketAddress e : joined_add.keySet())
//                    System.out.println(e.toString() + " " + joined_add.get(e));
            } else{
                Date date = new Date();
                joined_add.put(packet.getSocketAddress(), date.getTime());
//                for(SocketAddress e : joined_add.keySet())
//                    System.out.println(e.toString() + " " + joined_add.get(e));
            }
            return;
    }
     public void send(String multicastMessage) throws IOException {
        buf_s = multicastMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(buf_s, buf_s.length, group, 4446);
        socket_s.send(packet);
        return;
    }
    public void multicast (String[] args) throws IOException {
        socket_r = new MulticastSocket(4446);
        socket_r.setSoTimeout(3000);
        socket_s = new DatagramSocket();
        group = InetAddress.getByName(args[0]);
        socket_r.joinGroup(group);
        send("Hello");
        while(true){
            try {
                    recieve();
                    renew();
            } catch (Exception ee) {
                    if (ee instanceof SocketTimeoutException) {
                        send("I'm alive");
       //                 --ttl;
                    }
                    else {
                        ee.printStackTrace();
                        break;
                    }
                }
        }
        socket_r.leaveGroup(group);
        socket_r.close();
        return;
    }
}

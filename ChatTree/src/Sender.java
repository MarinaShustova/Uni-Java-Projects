import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Sender {
    private ByteBuffer int_buff = ByteBuffer.allocate(Integer.BYTES);
    private ArrayList<Message> to_remove = new ArrayList<>();

    public synchronized ArrayList<Pair> update(DatagramSocket socket, HashMap<Message, Integer> to_send) throws IOException {
        ArrayList<Pair> disconnected = new ArrayList<>();
        for(Message message : to_send.keySet()) {
            if(to_send.get(message) == 0){
               // while(true)
                socket.send(make_message(message));
                Date date = new Date();
                message.time = date.getTime();
                to_send.put(message, 1);
            }
            else if (to_send.get(message) == 3){
                to_remove.add(message);
            }
            else {
                Date date = new Date();
                if(date.getTime() - message.time > 500){
                    socket.send(make_message(message));
                    message.time = date.getTime();
                    to_send.put(message, to_send.get(message)+1);
                }
            }
        }
        for(Message m:to_remove){
            disconnected.add(m.addr_dst);
            to_send.remove(m);
        }
        return disconnected;
    }

    public DatagramPacket make_message(Message message) throws IOException {
        byte[] buf_s;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

        int_buff.clear();
        int_buff.putInt(0);                   //it is ack
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(message.addr_src.port);
        outputStream.write(int_buff.array());

        int_buff.clear();
        int_buff.putInt(message.user_hash);
        outputStream.write(int_buff.array());

        int len = message.message.length();

        int_buff.clear();
        int_buff.putInt(len);
        outputStream.write(int_buff.array());

        outputStream.write(message.message.getBytes());

        int_buff.clear();
        int_buff.putInt(message.message_hash);
        outputStream.write(int_buff.array());

        buf_s = outputStream.toByteArray();
        DatagramPacket packet = new DatagramPacket(buf_s, buf_s.length, message.addr_dst.address, message.addr_dst.port);
        return packet;
    }
}

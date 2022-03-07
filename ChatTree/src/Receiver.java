import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Receiver {
    private int loss;
    private ByteBuffer int_buff = ByteBuffer.allocate(Integer.BYTES);

    Receiver(int l){
        loss = l;
    }

    public Message update(byte[] recv_byte, InetAddress addr){

        Random rnd = new Random(System.currentTimeMillis());
        int is_this_loss = rnd.nextInt(100);
        if(is_this_loss < loss){
            return new Message();
        }

        byte[] ack = Arrays.copyOfRange(recv_byte,0,Integer.BYTES);
        byte[] port = Arrays.copyOfRange(recv_byte,Integer.BYTES,Integer.BYTES*2);
        byte[] uuid = Arrays.copyOfRange(recv_byte,Integer.BYTES*2,Integer.BYTES*3);
        byte[] len = Arrays.copyOfRange(recv_byte,Integer.BYTES*3,Integer.BYTES*4);
        int_buff.clear();
        int_buff = int_buff.put(len);
        int_buff.position(0);
        int length = int_buff.getInt();
        byte[] msg = Arrays.copyOfRange(recv_byte,Integer.BYTES*4,Integer.BYTES*4+length);
        byte[] hash = Arrays.copyOfRange(recv_byte,Integer.BYTES*4+length,Integer.BYTES*5+length);

        String received = new String(msg, 0, msg.length);

        int_buff.clear();
        int_buff = int_buff.put(ack);
        int_buff.position(0);
        int is_ack = int_buff.getInt();

        int_buff.clear();
        int_buff = int_buff.put(hash);
        int_buff.position(0);
        int message_hash = int_buff.getInt();

        int_buff.clear();
        int_buff = int_buff.put(uuid);
        int_buff.position(0);
        int user_hash = int_buff.getInt();

        int_buff.clear();
        int_buff = int_buff.put(port);
        int_buff.position(0);

        Pair pair = new Pair();
        pair.address = addr;
        pair.port = int_buff.getInt();

        Date date = new Date();
        if (is_ack == -1){
            Message m = new Message(pair, true, user_hash, received, date.getTime(), message_hash);
            return m;
        }
        return new Message(pair, false, user_hash, received, date.getTime(), message_hash);
    }
}

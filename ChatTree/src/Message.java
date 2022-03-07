public class Message {
    public boolean is_valid;
    public Pair addr_src;
    public Pair addr_dst;
    public boolean is_this_ack;
    public int user_hash;
    public int message_hash;
    public String message;
    public long time;

    public Message(){
        is_valid = false;
    }

    public Message(boolean ack){
        is_valid = true;
        is_this_ack = ack;
    }

    public Message(Pair p_src, boolean ack, int h, String m, long t, int mh){
        is_valid = true;
        addr_src = p_src;
        is_this_ack = ack;
        user_hash = h;
        message = m;
        time = t;
        message_hash = mh;
    }
}

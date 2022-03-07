public class LogMessage {
    public int id;
    public static int ids = 0;
    public String message;
    public boolean is_message;

    LogMessage(String m, boolean status){
        id = ids+1;
        ++ids;
        message = m;
        is_message = status;
    }
}

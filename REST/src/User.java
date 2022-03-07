import java.util.Date;

public class User {
    String name;
    Integer id;
    String token;
    boolean online;
    boolean timeout;
    static int last_id = 0;
    public long last_request;

    public User(String n){
        name = n;
        online = true;
        timeout = false;
        id = last_id+1;
        ++last_id;
        token = "password"+id.toString();
        Date d = new Date();
        last_request = d.getTime();
    }
    public void change_status(){
        if(online)
            online = false;
        else
            online = true;
    }
    public void timed_out(){
        if(online)
            online = false;
        else
            online = true;
        timeout = true;
    }
}

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class Message implements Serializable {
    private String login;
    private String message;
    private String[] users;

    public Message(String l, String m){
        login = l;
        message = m;
    }

    public Message(String l, String m, String[] u){
        login = l;
        message = m;
        users = u;
    }

    public void setOnlineUsers(String[] u) {
        users = u;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public String[] getUsers() {
        return users;
    }
}
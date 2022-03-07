import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {
    private List<Message> history;


    public Chat() {
        Information inf = new Information();
        history = new ArrayList<Message>(inf.len);
    }

    public void add(Message message){
        Information inf = new Information();
        if (this.history.size() > inf.len){
            this.history.remove(0);
        }
        this.history.add(message);
    }

    public List<Message> getHistory(){
        return this.history;
    }

}
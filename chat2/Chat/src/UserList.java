import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserList {

    private Map<String, Client> onlineUsers = new HashMap<>();

    public void add(String l, Socket s, ObjectOutputStream oos, ObjectInputStream ois) {
        System.out.println( l +" joined the chat" );

        if (!onlineUsers.containsKey(l)) {
            onlineUsers.put(l , new Client(s, oos, ois));
        } else {
            int i = 1;
            while(onlineUsers.containsKey(l)) {
                l = l + i;
                i++;
            }
            onlineUsers.put(l , new Client(s, oos, ois));
        }
    }

    public void delete(String l) {
        onlineUsers.remove(l);
    }

    public String[] getUsers() {
        return onlineUsers.keySet().toArray(new String[0]);
    }

    public ArrayList<Client> getClientsList() {
        ArrayList<Client> clientsList = new ArrayList<Client>(onlineUsers.entrySet().size());

        String s = "";
        for(Map.Entry<String, Client> m : onlineUsers.entrySet()){
            clientsList.add(m.getValue());
            System.out.println(m.getKey());
            s = s + m.getKey();
        }
        return clientsList;
    }

}
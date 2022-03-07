import io.javalin.Javalin;
import io.javalin.websocket.WsSession;
import okhttp3.Response;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;

public class Server {

    private ArrayList<JSONObject> messages = new ArrayList<>();
    private Map<WsSession,User> users = new ConcurrentHashMap<>();
    private HashMap<LogMessage, Long> Log = new HashMap<>();

    //private static Map<WsSession, String> userUsernameMap = new ConcurrentHashMap<>();
    private static int nextUserNumber = 1; // Assign to username for next connecting user

    public void my_main() {
        Javalin.create()
                .ws("/login", ws -> {
                    ws.onConnect(session -> {
                    });
                    ws.onClose((session, status, message) -> {
                    });
                    ws.onMessage((session, message) -> {
                        System.out.println(message);
                        JSONObject request = new JSONObject(message);
                        JSONObject response = new JSONObject();

                        boolean found = false;
                        for (WsSession u : users.keySet()){
                            if((users.get(u).name.equals(request.get("username").toString()))&&users.get(u).online){
                                response.put("code", "401")
                                        .put("ERROR", "Username is already in use");
                                found = true;
                                break;
                            }
                        }
                        if(!found){
                            User user = new User(request.get("username").toString());
                            add_user(session, user);
                            add_log_message("New user "+request.get("username").toString()+" joined!", false);
                            System.out.println("New user "+request.get("username").toString()+" joined!");

                            response.put("id", user.id.toString())
                                    .put("code", "200");
                            response.put("username", user.name);
                            response.put("online", "true");
                            response.put("token", user.token);
                        }
                        session.send(response.toString());
                       // broadcastMessage(request.getString("username"), message);
                    });
                })
                .start(7071);
    }

    // Sends a message from one user to all users, along with a list of current usernames
//    private void broadcastMessage(String sender, String message) {
//        users.keySet().stream().filter(Session::isOpen).forEach(session -> {
//            session.send(
//                    new JSONObject()
//                            .put("code", "200")
//                            .put("userMessage", message)
//                            .put("userlist", users.values()).toString());
//        });
//    }
    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    private synchronized void add_message(JSONObject j){
        messages.add(j);
    }

    private synchronized void add_user(WsSession session, User u){
        users.put(session, u);
    }

    private synchronized void add_log_message(String m, boolean status){
        Date d = new Date();
        Log.put(new LogMessage(m, status),d.getTime());
    }

}
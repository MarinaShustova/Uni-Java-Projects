import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.examples.UndertowExample;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import org.json.JSONObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.Handlers.websocket;

/**
 * @author Stuart Douglas
 */
public class Server {
  //  private ServerSocket socket;
    private ArrayList<JSONObject> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private HashMap<LogMessage, Long> Log = new HashMap<>();

    public void my_main() {

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path()
                        .addPrefixPath("/myapp", websocket(new WebSocketConnectionCallback() {

                            @Override
                            public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
                                channel.getReceiveSetter().set(new AbstractReceiveListener() {

                                    @Override
                                    protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                        final String messageData = message.getData();
                                        for (WebSocketChannel session : channel.getPeerConnections()) {
                                            WebSockets.sendText(messageData, session, null);
                                        }
                                    }
                                });
                                channel.resumeReceives();
                            }

                        }))
                        .addPrefixPath("/login", new MyHandlerLogin())
                        .addPrefixPath("/logout", new MyHandlerLogout())
                        .addPrefixPath("/users", new MyHandlerUsers())
                        .addPrefixPath("/messages", new MyHandlerMessages())
                        .addPrefixPath("/update", Handlers.websocket((exchange, webSocketChannel) -> {
                            exchange.setResponseHeader("Content-Type", "application/json");
                            webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                                @Override
                                protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                    String token = exchange.getRequestHeaders().get("Authorization").get(0);
                                    boolean found = false;
                                    User user = null;
                                    for (User u :users){
                                        if(u.token.equals(token)){
                                            user = u;
                                            Date d = new Date();
                                            u.last_request = d.getTime();
                                            found = true;
                                            break;
                                        }
                                    }
                                    if(!found){
                                   //     exchange.senResponseHeaders(403, 0);
                                    }
                                    else {
                                        JSONObject response = new JSONObject();
                                        Date d = new Date();
                                        long time = d.getTime();
                                        ArrayList<String> upd = new ArrayList<>();
                                        for (LogMessage l : Log.keySet()) {
                                            if (time - Log.get(l) < 1000) {
                                                upd.add(l.message);
                                            }
                                        }
                                        //System.out.println(upd.size());
                                        if(upd.size()>0) {
                                            response.put("updates", upd);
                                            System.out.println(response.toString());
                                            //exchange.getResponseHeaders().add("Content-Type", "application/json");
                                            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
                                            OutputStream os = exchange.getResponseBody();
                                            os.write(response.toString().getBytes());
                                            os.close();
                                        }
                                        else{
                                            exchange.sendResponseHeaders(500, 0);
                                        }
                                    }
                                }
                            });
                            webSocketChannel.resumeReceives();
                        })))
                .build();

        server.start();
    }

}
//import io.undertow.Handlers;
//import io.undertow.websockets.core.AbstractReceiveListener;
//import io.undertow.websockets.core.BufferedTextMessage;
//import io.undertow.websockets.core.WebSocketChannel;
//import io.undertow.websockets.core.WebSockets;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//import com.sun.net.httpserver.HttpServer;
//
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.URLDecoder;
//import java.util.*;
//
//import static java.lang.Thread.sleep;
//
//public class Server {
//    private ServerSocket socket;
//    private ArrayList<JSONObject> messages = new ArrayList<>();
//    private ArrayList<User> users = new ArrayList<>();
//    private HashMap<LogMessage, Long> Log = new HashMap<>();
//
//    public void my_main(){
//        HttpServer server = null;
//        try {
//            server = HttpServer.create(new InetSocketAddress(8080), 0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        server.createContext("/login", new MyHandlerLogin());
//        server.createContext("/logout", new MyHandlerLogout());
//        server.createContext("/users", new MyHandlerUsers());//users?username=""&id=1
//        server.createContext("/messages", new MyHandlerMessages());
//        //server.createContext("/update", new MyHandlerUpdate());
//        server.createContext("/update", Handlers.websocket((exchange, webSocketChannel) -> {
//            exchange.setResponseHeader("Content-Type", "application/json");
//            webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
//                @Override
//                protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
//                    String token = exchange.getRequestHeaders().get("Authorization").get(0);
//                    boolean found = false;
//                    User user = null;
//                    for (User u :users){
//                        if(u.token.equals(token)){
//                            user = u;
//                            Date d = new Date();
//                            u.last_request = d.getTime();
//                            found = true;
//                            break;
//                        }
//                    }
//                    if(!found){
//                   //     exchange.senResponseHeaders(403, 0);
//                    }
//                    else {
//                        JSONObject response = new JSONObject();
//                        Date d = new Date();
//                        long time = d.getTime();
//                        ArrayList<String> upd = new ArrayList<>();
//                        for (LogMessage l : Log.keySet()) {
//                            if (time - Log.get(l) < 1000) {
//                                upd.add(l.message);
//                            }
//                        }
//                        //System.out.println(upd.size());
//                        if(upd.size()>0) {
//                            response.put("updates", upd);
//                            System.out.println(response.toString());
//                            //exchange.getResponseHeaders().add("Content-Type", "application/json");
//                            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                            OutputStream os = exchange.getResponseBody();
//                            os.write(response.toString().getBytes());
//                            os.close();
//                        }
//                        else{
//                            exchange.sendResponseHeaders(500, 0);
//                        }
//                    }
//                }
//            });
//            webSocketChannel.resumeReceives();
//        }));
//        server.setExecutor(null);
//        server.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        sleep(10000);
//                        for (User u : users) {
//                            Date d = new Date();
//                            if ((d.getTime() - u.last_request > 30000) && (u.online)) {
//                                //u.change_status();
//                                u.timed_out();
//                                Server.this.add_log_message("User " + u.name + " has timed out", false);
//                                System.out.println("User " + u.name + " has timed out");
//                            }
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//    public class MyHandlerLogin implements HttpHandler {
//
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//            if(t.getRequestURI().toString().length() - "/login".length() > 1){
//                t.sendResponseHeaders(400, 0);
//                return;
//            }
//            InputStream in = t.getRequestBody();
//            String inputString = read(in);
//
//            System.out.println(t.getRequestMethod());
//            System.out.println(inputString);
//
//            JSONObject request = new JSONObject(inputString);
//            JSONObject response = new JSONObject();
//
//            boolean found = false;
//            for (User u : users){
//                if((u.name.equals(request.get("username").toString()))&&u.online){
//                    t.getResponseHeaders().add("WWW-Authenticate", "Token realm='Username is already in use'");
//                    t.sendResponseHeaders(401, 0);
//                    found = true;
//                    break;
//                }
//            }
//            if(!found){
//                User user = new User(request.get("username").toString());
//                add_user(user);
//                add_log_message("New user "+request.get("username").toString()+" joined!", false);
//                System.out.println("New user "+request.get("username").toString()+" joined!");
//
//                t.getResponseHeaders().add("Content-Type", "application/json");
//                response.put("id", user.id.toString());
//                response.put("username", user.name);
//                response.put("online", "true");
//                response.put("token", user.token);
//
//                t.sendResponseHeaders(200, response.toString().getBytes().length);
//                OutputStream os = t.getResponseBody();
//                os.write(response.toString().getBytes());
//                os.close();
//            }
//        }
//    }
//
//    public class MyHandlerLogout implements HttpHandler{
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            if(exchange.getRequestURI().toString().length() - "/logout".length() > 1){
//                exchange.sendResponseHeaders(400, 0);
//                return;
//            }
//            String token = exchange.getRequestHeaders().get("Authorization").get(0);
//            boolean found = false;
//            for (User u :users){
//                if(u.token.equals(token)){
//                    System.out.println(exchange.getRequestMethod());
//                    add_log_message("User "+u.name+" logged out", false);
//                    System.out.println("User "+u.name+" logged out");
//                    u.change_status();
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    JSONObject response = new JSONObject();
//                    response.put("message", "bye!");
//                    exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                    OutputStream os = exchange.getResponseBody();
//                    os.write(response.toString().getBytes());
//                    os.close();
//                    found = true;
//                    break;
//                }
//            }
//            if(!found){
//                exchange.sendResponseHeaders(403, 0);
//            }
//        }
//    }
//
//    public class MyHandlerUsers implements HttpHandler {
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            String token = exchange.getRequestHeaders().get("Authorization").get(0);
//            boolean found = false;
//            for (User u : users) {
//                if (u.token.equals(token)) {
//                    found = true;
//                    Date d = new Date();
//                    u.last_request = d.getTime();
//                    break;
//                }
//            }
//            if (!found) {
//                exchange.sendResponseHeaders(403, 0);
//            }
//            else{
//                if (exchange.getRequestURI().getPath().equals("/users")) {
//                    System.out.println(exchange.getRequestMethod());
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    JSONObject response = new JSONObject();
//                    JSONArray list = new JSONArray();
//                    for (User us : users) {
//                        JSONObject single = new JSONObject();
//                        single.put("id", us.id.toString());
//                        single.put("username", us.name);
//                        if(!us.online && us.timeout)
//                            single.put("online", "null");
//                        else
//                            single.put("online", us.online);
//                        list.put(single);
//                    }
//                    response.put("users", list);
//                    exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                    OutputStream os = exchange.getResponseBody();
//                    os.write(response.toString().getBytes());
//                    os.close();
//
//                } else {
//                    System.out.println(exchange.getRequestMethod());
//                    boolean exist = false;
//                    char[] dst = new char[exchange.getRequestURI().getPath().length() - 7];
//                    exchange.getRequestURI().getPath().getChars(7, exchange.getRequestURI().getPath().length(), dst, 0);
//                    for (User us : users) {
//                        if (Arrays.equals(us.id.toString().toCharArray(), dst)) {
//                            exchange.getResponseHeaders().add("Content-Type", "application/json");
//                            JSONObject response = new JSONObject();
//                            response.put("id", us.id.toString());
//                            response.put("username", us.name);
//                            response.put("online", us.online);
//                            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                            OutputStream os = exchange.getResponseBody();
//                            os.write(response.toString().getBytes());
//                            os.close();
//                            exist = true;
//                        }
//                    }
//                    if(!exist){
//                        exchange.sendResponseHeaders(404, 0);
//                    }
//                }
//            }
//        }
//    }
//
//    public class MyHandlerMessages implements HttpHandler{
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            String token = exchange.getRequestHeaders().get("Authorization").get(0);
//            boolean found = false;
//            User user = null;
//            for (User u :users){
//                if(u.token.equals(token)){
//                    user = u;
//                    Date d = new Date();
//                    u.last_request = d.getTime();
//                    found = true;
//                    break;
//                }
//            }
//            if(!found){
//                exchange.sendResponseHeaders(403, 0);
//            }
//            else{
//                if (exchange.getRequestURI().toString().equals("/messages")) {
//                    InputStream in = exchange.getRequestBody();
//                    String inputString = read(in);
//
//                    System.out.println(exchange.getRequestMethod());
//                    System.out.println(inputString);
//
//                    JSONObject request = new JSONObject(inputString);
//                    request.put("name", user.name);
//                    add_message(request);
//                    add_log_message("["+user.name+"] : "+request.get("message"), true);
//
//                    System.out.println("["+user.name+"] : "+request.get("message"));
//
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    JSONObject response = new JSONObject();
//
//                    response.put("id", user.id.toString());
//                    response.put("message", request.get("message"));
//
//                    exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                    OutputStream os = exchange.getResponseBody();
//                    os.write(response.toString().getBytes());
//                    os.close();
//
//                } else {
//                    String query = exchange.getRequestURI().getQuery();
//                    if(query.isEmpty()){
//                        exchange.sendResponseHeaders(400, 0);
//                        return;
//                    }
//                    Map<String, String> params = splitQuery(query);
//                    Integer offset = Integer.parseInt(params.get("offset")), count = Integer.parseInt(params.get("count"));
//                    int size = messages.size();
//                    if(size == 0){
//                        exchange.sendResponseHeaders(500, 0);
//                        return;
//                    }
//                    if(offset <= size){
//                        JSONObject response = new JSONObject();
//                        JSONArray list = new JSONArray();
//                        //     System.out.println("offset = "+offset+" count = "+
//                        for(int i = offset-1; (i<offset + count-1); ++i){
//                            if(i == size)
//                                break;
//                            list.put(messages.get(i));
//                        }
//                        response.put("messages", list);
//                        exchange.getResponseHeaders().add("Content-Type", "application/json");
//                        exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                        OutputStream os = exchange.getResponseBody();
//                        os.write(response.toString().getBytes());
//                        os.close();
//                    }
//                }
//            }
//        }
//    }
//
//    public class MyHandlerUpdate implements HttpHandler{
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException {
//            String token = exchange.getRequestHeaders().get("Authorization").get(0);
//            boolean found = false;
//            User user = null;
//            for (User u :users){
//                if(u.token.equals(token)){
//                    user = u;
//                    Date d = new Date();
//                    u.last_request = d.getTime();
//                    found = true;
//                    break;
//                }
//            }
//            if(!found){
//                exchange.sendResponseHeaders(403, 0);
//            }
//            else {
//                JSONObject response = new JSONObject();
//                Date d = new Date();
//                long time = d.getTime();
//                ArrayList<String> upd = new ArrayList<>();
//                for (LogMessage l : Log.keySet()) {
//                    if (time - Log.get(l) < 1000) {
//                        upd.add(l.message);
//                    }
//                }
//                //System.out.println(upd.size());
//                if(upd.size()>0) {
//                    response.put("updates", upd);
//                    System.out.println(response.toString());
//                    exchange.getResponseHeaders().add("Content-Type", "application/json");
//                    exchange.sendResponseHeaders(200, response.toString().getBytes().length);
//                    OutputStream os = exchange.getResponseBody();
//                    os.write(response.toString().getBytes());
//                    os.close();
//                }
//                else{
//                    exchange.sendResponseHeaders(500, 0);
//                }
//            }
//        }
//    }
//
//    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
//        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
//        String[] pairs = query.split("&");
//        for (String pair : pairs) {
//            int idx = pair.indexOf("=");
//            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
//        }
//        return query_pairs;
//    }
//
//    private synchronized void add_message(JSONObject j){
//        messages.add(j);
//    }
//
//    private synchronized void add_user(User u){
//        users.add(u);
//    }
//
//    private synchronized void add_log_message(String m, boolean status){
//        Date d = new Date();
//        Log.put(new LogMessage(m, status),d.getTime());
//    }
//
//    private String read(InputStream input) {
//        ByteArrayOutputStream result = new ByteArrayOutputStream();
//        String message = "";
//        int length = 0;
//        byte[] buffer = new byte[64];
//        while (true) {
//            try {
//                length = input.read(buffer);
//                if (length == -1)
//                    break;
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//            result.write(buffer, 0, length);
//        }
//        try {
//            message = result.toString("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return message;
//    }
//}
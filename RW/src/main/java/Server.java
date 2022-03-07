import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.AbstractReceiveListener;
import io.undertow.websockets.core.BufferedTextMessage;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;
import io.undertow.websockets.spi.WebSocketHttpExchange;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Server extends Thread {
    private Undertow server;
    private String host = "localhost";
    private ArrayList<JSONObject> messages = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private HashMap<User, WebSocketChannel> connections = new HashMap<>();
    private String USERNAME_IN_USE = "ERROR 401 : USERNAME IS ALREADY IN USE";
    private String UNKNOWN_REQUEST = "ERROR 400 : UNKNOWN_REQUEST";
    private String UNKNOWN_TOKEN = "ERROR 403 : UNKNOWN_TOKEN";

    public Server() {
        HttpHandler pathHandler = new PathHandler(Handlers.path()
                .addPrefixPath("/login", Handlers.websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(final WebSocketHttpExchange exchange, WebSocketChannel webSocketChannel) {
                        webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                                String data = message.getData();
                                System.out.println(data);
                                JSONObject request = new JSONObject(data);
                                JSONObject response = new JSONObject();

                                boolean found = false;
                                for (User u : users) {
                                    if ((u.name.equals(request.get("username").toString())) && u.online) {
                                        found = true;
                                        response.put("type", "error")
                                                .put("message", USERNAME_IN_USE);
                                        WebSockets.sendText(response.toString(), channel, null);
                                        break;
                                    }
                                }
                                if (!found) {
                                    User user = new User(request.get("username").toString());
                                    add_user(user);
                                    change_connections(user,channel,true);
                                    System.out.println("New user " + request.get("username").toString() + " joined!");
                                    response.put("type", "login")
                                        .put("id", user.id.toString())
                                        .put("username", user.name)
                                        .put("online", "true")
                                        .put("token", user.token);
                                    WebSockets.sendText(response.toString(), channel, null);
                                    JSONObject jo = new JSONObject();
                                    jo.put("message", "New user " + request.get("username").toString() + " joined!")
                                            .put("type", "print");
                                    for (WebSocketChannel ws : connections.values()) {
                                        WebSockets.sendText(jo.toString(), ws, null);
                                    }
                                }
                            }
                        });
                        webSocketChannel.resumeReceives();
                    }
                }))
                .addPrefixPath("/users", Handlers.websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(final WebSocketHttpExchange exchange, WebSocketChannel webSocketChannel) {
                        webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                                JSONObject response = new JSONObject();
                                if (exchange.getRequestURI().equals("/users")) {
                                    JSONArray list = new JSONArray();
                                    for (User us : users) {
                                        JSONObject single = new JSONObject();
                                        single.put("username", us.name);
                                        if(!us.online && us.timeout)
                                            single.put("online", "null");
                                        else
                                            single.put("online", us.online);
                                        list.put(single);
                                    }
                                    response.put("data", list)
                                    .put("type", "users");
                                    WebSockets.sendText(response.toString(), channel, null);

                                } else {
                                    char[] dst = new char[exchange.getRequestURI().length() - 7];
                                    exchange.getRequestURI().getChars(7, exchange.getRequestURI().length(), dst, 0);
                                    for (User us : users) {
                                        if (Arrays.equals(us.id.toString().toCharArray(), dst)) {
                                            response.put("type", "user");
                                            response.put("username", us.name);
                                            response.put("online", us.online);
                                            WebSockets.sendText(response.toString(), channel, null);
                                        }
                                    }
                                }
                            }
                        });
                        webSocketChannel.resumeReceives();
                    }
                }))
                .addPrefixPath("/logout", Handlers.websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(final WebSocketHttpExchange exchange, WebSocketChannel webSocketChannel) {
                        webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                                String data = message.getData();
                                JSONObject msg = new JSONObject(data);
                                JSONObject response = new JSONObject();
                                if (exchange.getRequestURI().length() - "/logout".length() > 1) {
                                    response.put("type", "error")
                                            .put("message", UNKNOWN_REQUEST);
                                    WebSockets.sendText(response.toString(), channel, null);
                                    return;
                                }
                                String token = msg.getString("token");
                                boolean found = false;
                                for (User u : users) {
                                    if (u.token.equals(token)) {
                                        System.out.println("User " + u.name + " logged out");
                                        u.change_status();
                                        response.put("message", "bye!")
                                        .put("type", "logout");
                                        WebSockets.sendText(response.toString(), channel, null);
                                        change_connections(u,channel, false);
                                        JSONObject jo = new JSONObject();
                                        jo.put("message", "User " + u.name + " logged out")
                                                .put("type", "print");
                                        for (WebSocketChannel ws : connections.values()) {
                                            WebSockets.sendText(jo.toString(), ws, null);
                                        }
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) {
                                    response.put("type", "error")
                                            .put("message", UNKNOWN_TOKEN);
                                    WebSockets.sendText(response.toString(), channel, null);
                                }
                            }
                        });
                        webSocketChannel.resumeReceives();
                    }
                }))
                .addPrefixPath("/update", Handlers.websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(final WebSocketHttpExchange exchange, WebSocketChannel webSocketChannel) {
                        webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws IOException {
                                String data = message.getData();
                                JSONObject msg = new JSONObject(data);
                                String token = msg.getString("token");
                                for (User u : users) {
                                    if (u.token.equals(token)) {
                                        Date d = new Date();
                                        u.last_request = d.getTime();
                                        break;
                                    }
                                }
                            }
                        });
                        webSocketChannel.resumeReceives();
                    }
                }))
                .addPrefixPath("/messages", Handlers.websocket(new WebSocketConnectionCallback() {
                    @Override
                    public void onConnect(final WebSocketHttpExchange exchange, WebSocketChannel webSocketChannel) {
                        webSocketChannel.getReceiveSetter().set(new AbstractReceiveListener() {
                            @Override
                            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) throws UnsupportedEncodingException {
                                System.out.println(exchange.getRequestURI());
                                if (exchange.getRequestURI().equals("/messages")) {
                                    String data = message.getData();
                                    JSONObject msg = new JSONObject(data);
                                    add_message(msg);
                                    System.out.println("["+msg.getString("name")+"] : "+ msg.getString("message"));
                                    JSONObject jo = new JSONObject();
                                    jo.put("message", "["+msg.getString("name")+"] : "+ msg.getString("message"))
                                            .put("type", "print");
                                    for (WebSocketChannel ws : connections.values()) {
                                        WebSockets.sendText(jo.toString(), ws, null);
                                    }
                                }
                                else {
                                    System.out.println(exchange.getQueryString());
                                    String query = exchange.getQueryString();
                                    System.out.println(query);
                                    Map<String, String> params = splitQuery(query);
                                    Integer offset = Integer.parseInt(params.get("offset")), count = Integer.parseInt(params.get("count"));
                                    System.out.println("offset : "+offset.toString()+" count : "+count.toString());
                                    int size = messages.size();
                                    if(offset <= size){
                                        JSONObject response = new JSONObject();
                                        JSONArray list = new JSONArray();
                                        for(int i = offset-1; (i<offset + count-1); ++i){
                                            if(i == size)
                                                break;
                                            list.put(messages.get(i));
                                        }
                                        response.put("data", list).put("type", "list");
                                        WebSockets.sendText(response.toString(), channel, null);
                                    }
                                }
                            }
                        });
                        webSocketChannel.resumeReceives();
                    }
                })));
        server = Undertow.builder()
                .addHttpListener(8080, host, pathHandler)
                .build();
    }

    public void my_main() {
        server.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(10000);
                        for (User u : users) {
                            Date d = new Date();
                            if ((d.getTime() - u.last_request > 30000) && (u.online)) {
                                u.timed_out();
                                System.out.println("User " + u.name + " has timed out");
                                JSONObject jo = new JSONObject();
                                jo.put("message", "User "+u.name+" has timed out")
                                        .put("type", "print");
                                for (WebSocketChannel ws : connections.values()) {
                                    WebSockets.sendText(jo.toString(), ws, null);
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

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

    private synchronized void add_user(User u){
        users.add(u);
    }

    private synchronized void change_connections(User u, WebSocketChannel ch, boolean add){
        if (add)
            connections.put(u, ch);
        else
            connections.remove(u);
    }


}
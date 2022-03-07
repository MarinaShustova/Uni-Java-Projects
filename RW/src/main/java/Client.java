import okhttp3.*;
import okio.ByteString;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;

public class Client {

    private OkHttpClient client;
    private String my_token;
    private String my_name;
    private String host = "http://localhost:8080";
    private boolean authorized = false;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private MyWebSocketListener myWebSocketListener = new MyWebSocketListener();
    private WebSocket webSocket_login, webSocket_logout, webSocket_users, webSocket_messages, webSocket_update, webSocket_finduser, webSocket_get;
    private boolean first_time = true, first_message = true, first_users = true, first_find = true, first_get = true;


    private synchronized void change_state(){
        authorized = !authorized;
    }

    public Client() {
        new ConsoleReader();
        client = new OkHttpClient();
    }

    public void my_main(){
        System.out.println("Hello! Please enter your username");
    }

    public void login(String name){
            JSONObject req = new JSONObject();
            req.put("username", name);
            my_name = name;
                Request request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .url(host + "/login")
                        .build();
            webSocket_login = client.newWebSocket(request, myWebSocketListener);
            webSocket_login.send(req.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                        Client.this.update();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void logout(){
        JSONObject r = new JSONObject();
        r.put("token", my_token);
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(host + "/logout")
                .build();
        webSocket_logout = client.newWebSocket(request, myWebSocketListener);
        webSocket_logout.send(r.toString());
        change_state();
    }

    public void send_message(String message){
        JSONObject r = new JSONObject();
        r.put("message", message)
        .put("name", my_name);
        //RequestBody reqBody = RequestBody.create(JSON, r.toString());
        if (first_message) {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(host + "/messages?")
                    .build();
            webSocket_messages = client.newWebSocket(request, new MyWebSocketListener());
            first_message = false;
        }
        webSocket_messages.send(r.toString());
    }

    public void users(){
        JSONObject r = new JSONObject();
        if (first_users) {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(host + "/users")
                    .build();
            webSocket_users = client.newWebSocket(request, new MyWebSocketListener());
            first_users = false;
        }
        webSocket_users.send(r.toString());
    }

    public void find_user(String user){
        JSONObject r = new JSONObject();
        if (first_find) {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(host + "/users/" + user)
                    .build();
            webSocket_finduser = client.newWebSocket(request, new MyWebSocketListener());
            first_find = false;
        }
        webSocket_finduser.send(r.toString());
    }

    public void get_messages(String offset, String count){
        JSONObject r = new JSONObject();
        Integer off = (offset.isEmpty())?0:Integer.parseInt(offset), cou = (count.isEmpty())?10:Integer.parseInt(count);
        if(off < 1){
            System.out.println("Offset is less than 1, it will be set to 1");
            off = 1;
        }
        if (cou > 100){
            System.out.println("Enable to get more than 100 messages, only 100 messages will be shown");
            cou = 100;
        }
        if (first_get) {
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(host + "/messages?offset=" + off.toString() + "&count=" + cou.toString())
                    .build();
            webSocket_get = client.newWebSocket(request, new MyWebSocketListener());
            first_get = false;
        }
        webSocket_get.send(r.toString());
    }

    public void update(){
        JSONObject r = new JSONObject();
        r.put("token", my_token);
        if (first_time){
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(host + "/update")
                    .build();
            webSocket_update = client.newWebSocket(request, new MyWebSocketListener());
            first_time = false;
        }
        webSocket_update.send(r.toString());
    }

    private class MyWebSocketListener extends okhttp3.WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
           // webSocket.send("Hello");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            JSONObject jsonObject = new JSONObject(text);
            String type = jsonObject.getString("type");
            if (type.equals("print") || type.equals("error"))
                System.out.println(jsonObject.getString("message"));
            else if (type.equals("login")){
                my_token = jsonObject.getString("token");
                System.out.println("Success!");
                change_state();
            }
            else if (type.equals("logout")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("To join the chat again please enter your username");
            }
            else if (type.equals("list")){
                int size = jsonObject.getJSONArray("data").length();
                for (int i = 0; i < size; ++i){
                    System.out.println("["+jsonObject.getJSONArray("data").getJSONObject(i).get("name")+"] : "+jsonObject.getJSONArray("data").getJSONObject(i).get("message"));
                }
            }
            else if (type.equals("users")){
                int size = jsonObject.getJSONArray("data").length();
                for (int i = 0; i < size; ++i){
                    System.out.println("["+jsonObject.getJSONArray("data").getJSONObject(i).get("username")+"] : online : "+jsonObject.getJSONArray("data").getJSONObject(i).get("online"));
                }
            }
            else if (type.equals("user")){
                System.out.println("["+jsonObject.getString("username")+"] : online : "+jsonObject.get("online"));
            }
        }

        public void closeWebSocket(){
    //        this.ws.close(1000, null);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
          //  System.out.println("MESSAGE: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            //System.out.println("CLOSE: " + code + " " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            System.out.println(t.toString());
            t.printStackTrace();
            System.out.println("NETWORK ERROR");
        }


    }

    private class ConsoleReader extends Thread {

        ConsoleReader(){
            start();
        }

        public void run() {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String message;
                try {
                    message = consoleReader.readLine();
                    if (!message.isEmpty()) {
                        if(!authorized){
                            login(message);
                        }
                        else if(message.equals("/logout")){
                            logout();
                        }
                        else if(message.equals("/users")){
                            users();
                        }
                        else if(message.equals("/find user")){
                            System.out.println("Enter id");
                            message = consoleReader.readLine();
                            find_user(message);
                        }
                        else if(message.equals("/messages")){
                            String offset, count;
                            System.out.println("Enter the number of the first message");
                            message = consoleReader.readLine();
                            offset = message;
                            System.out.println("Enter the number of messages to show");
                            message = consoleReader.readLine();
                            count = message;
                            get_messages(offset, count);
                        }
                        else{
                            send_message(message);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
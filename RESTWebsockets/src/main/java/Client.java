import okhttp3.*;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;
import okio.ByteString;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static okhttp3.ws.WebSocket.BINARY;
import static okhttp3.ws.WebSocket.TEXT;

public final class Client extends Thread {

    private String my_token;
    private boolean authorized = false;

    private OkHttpClient client;
    private String host = "ws://localhost:7071";

    private synchronized void change_state(){
        authorized = !authorized;
    }

    Client(){
        new ConsoleReader();
    }

    public void run() {
        client = new OkHttpClient();
    }

    public void my_main() throws IOException {
        run();
        System.out.println("Hello! Please enter your username");
    }

    public void login(String name){
         final class WebSocketEcho implements WebSocketListener {
            private final ExecutorService writeExecutor = Executors.newSingleThreadExecutor();

            private void run() throws IOException {

                Request request = new Request.Builder()
                        .url(host + "/login")
                        .build();
                WebSocketCall.create(client, request).enqueue(this);

                // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
                client.dispatcher().executorService().shutdown();
            }

            @Override
            public void onOpen(final WebSocket webSocket, Response response) {
                writeExecutor.execute(() -> {
                    try {
                        JSONObject user = new JSONObject();
                        user.put("username", name);
                        RequestBody requestBody = RequestBody.create(TEXT,user.toString());
                        webSocket.sendMessage(requestBody);
                    } catch (IOException e) {
                        System.err.println("Unable to send messages: " + e.getMessage());
                    }
                });
            }

            @Override
            public void onMessage(ResponseBody message) throws IOException {
                if (message.contentType() == TEXT) {
                 //  System.out.println("MESSAGE: " + message.toString());
                    JSONObject resp = new JSONObject(message.string());
                 //   System.out.println(resp.getString("code"));
                    if(resp.getString("code").equals("200")) {
                        my_token = resp.get("token").toString();
                        System.out.println("Success!");
                        change_state();
                    }
                }
                message.close();
            }

            @Override
            public void onPong(Buffer payload) {
                System.out.println("PONG: " + payload.readUtf8());
            }

            @Override
            public void onClose(int code, String reason) {
                System.out.println("CLOSE: " + code + " " + reason);
                writeExecutor.shutdown();
            }

            @Override
            public void onFailure(IOException e, Response response) {
                e.printStackTrace();
                writeExecutor.shutdown();
            }
        }

        WebSocketEcho w = new WebSocketEcho();
        try {
            w.run();
        } catch (IOException e) {
   //         e.printStackTrace();
        }
//        try {
//            Response response;
//            JSONObject user = new JSONObject();
//            user.put("username", name);
//            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),user.toString());
//
//            Request request = new Request.Builder()
//                    .method("POST", requestBody)
//                    .addHeader("Content-Type", "application/json")
//                    .url(host+"/login")
//                    .post(requestBody)
//                    .build();
//            WebSocketCall.create(client, request);
//            response = client.newCall(request).execute();
//
//            if(response.code() == 200) {
//                JSONObject resp = new JSONObject(response.body());
//                my_token = resp.get("token").toString();
//                System.out.println("Success!");
//                change_state();
//            }
//            else if(response.code() == 401){
//                System.out.println("ERROR CODE 401 : This username is already taken");
//            }
//            else if(response.code() == 400){
//                System.out.println("ERROR CODE 400 : Unknown request");
//            }
//            else if(response.code() == 404){
//                System.out.println("ERROR CODE 404 : Not found");
//            }
//            else{
//                System.out.println("Unknowh error has occured, please thy again");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
                            //logout();
                            System.out.println("To join the chat again please enter your username");
                        }
                        else if(message.equals("/users")){
                          //  users();
                        }
                        else if(message.equals("/find user")){
                            System.out.println("Enter id");
                            message = consoleReader.readLine();
                         //   find_user(message);
                        }
                        else if(message.equals("/messages")){
                            String offset, count;
                            System.out.println("Enter the number of the first message");
                            message = consoleReader.readLine();
                            offset = message;
                            System.out.println("Enter the number of messages to show");
                            message = consoleReader.readLine();
                            count = message;
                         //   get_messages(offset, count);
                        }
                        else{
                         //   send_message(message);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
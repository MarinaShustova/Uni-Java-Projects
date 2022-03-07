import org.json.JSONObject;

import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

public class Client {

    private String my_token;
    private URL myURL = null;
    private byte[] buffer = new byte[64];
    private int length= 0;
    private boolean authorized = false;

    private synchronized void change_state(){
        authorized = !authorized;
    }

    public Client() {
        new ConsoleReader();
    }

    public void my_main(){
        System.out.println("Hello! Please enter your username");
    }

    public void login(String name){
        try {
            myURL = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            JSONObject response = new JSONObject();
            response.put("username", name);
            writer.write(response.toString());
            writer.flush();

            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JSONObject resp = new JSONObject(result.toString());
                my_token = resp.get("token").toString();
                System.out.println("Success!");
                change_state();
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
            else if(connection.getResponseCode() == 401){
                System.out.println("This username is already taken, please try again");
            }
            else if(connection.getResponseCode() == 400){
                System.out.println(connection.getResponseCode()+"Unknown request");
            }
            else{
                System.out.println("Unknowh error has occured, please thy again");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        try {
            myURL = new URL("http://localhost:8080/logout");
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", my_token);
            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    //                System.out.println(result.toString());
                }
                change_state();
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
            else if(connection.getResponseCode() == 400){
                System.out.println(connection.getResponseCode()+"Unknown request");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void users(){
        try {
            myURL = new URL("http://localhost:8080/users");
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            //connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", my_token);
            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    //    System.out.println(result.toString());
                }
                JSONObject j = new JSONObject(result.toString());
                int size = j.getJSONArray("users").length();
                for (int i = 0; i < size; ++i){
                    //System.out.println(j.getJSONArray("messages").getJSONObject(i).getString("name"));
                    System.out.println("["+j.getJSONArray("users").getJSONObject(i).get("username")+"] : online : "+j.getJSONArray("users").getJSONObject(i).get("online"));
                }
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
            else if(connection.getResponseCode() == 404){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get_messages(String offset, String count){
        try {
            Integer off = (offset.isEmpty())?0:Integer.parseInt(offset), cou = (count.isEmpty())?10:Integer.parseInt(count);
            if(off <= 0){
                System.out.println("Offset is less than 1, it will be set to 1");
                off = 1;
            }
            if (cou > 100){
                System.out.println("Enable to get more than 100 messages, only 100 messages will be shown");
                cou = 100;
            }
            myURL = new URL("http://localhost:8080/messages?offset="+off.toString()+"&count="+cou.toString());
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();

            connection.setRequestProperty("Authorization", my_token);

            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    //     System.out.println(result.toString());
                }
                JSONObject j = new JSONObject(result.toString());
                int size = j.getJSONArray("messages").length();
                for (int i = 0; i < size; ++i){
                    //System.out.println(j.getJSONArray("messages").getJSONObject(i).getString("name"));
                    System.out.println("["+j.getJSONArray("messages").getJSONObject(i).get("name")+"] : "+j.getJSONArray("messages").getJSONObject(i).get("message"));
                }
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
            else if(connection.getResponseCode() == 500){
                System.out.println(connection.getResponseCode()+" no messages are avaliable");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send_message(String message){
        try {
            myURL = new URL("http://localhost:8080/messages");
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setDoOutput(true);

            connection.setRequestProperty("Authorization", my_token);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            JSONObject response = new JSONObject();
            response.put("message", message);
            writer.write(response.toString());
            writer.flush();

            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    //                System.out.println(result.toString());
                }
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void find_user(String id){
        try {
            myURL = new URL("http://localhost:8080/users/"+id);
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            //connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", my_token);
            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    System.out.println(result.toString());
                }
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        try {
            myURL = new URL("http://localhost:8080/update");
            HttpURLConnection connection = (HttpURLConnection) myURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", my_token);
            if(connection.getResponseCode() == 200) {
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JSONObject j = new JSONObject(result.toString());
                int size = j.getJSONArray("updates").length();
                for (int i = 0; i < size; ++i){
                    System.out.println(j.getJSONArray("updates").getString(i));
                }
            }
            else if(connection.getResponseCode() == 403){
                System.out.println(connection.getResponseCode()+" "+connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                            System.out.println("To join the chat again please enter your username");
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
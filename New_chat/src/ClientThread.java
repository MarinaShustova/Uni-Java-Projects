import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private Socket socket;
    private Message m;
    private String login;
    private int rec = 0;
    private int send = 0;
    private boolean flag = false;
    private Timer timer;

    public ClientThread(Socket s) {
        socket = s;
        start();
    }

    public void run() {
        Information inf = new Information();
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ObjectInputStream IS = inputStream;
        final ObjectOutputStream OS = outputStream;
        while (true) {
            if (this.flag) {
                this.flag = false;
                break;
            }
            try {
                if (inputStream != null) {
                    m = (Message) inputStream.readObject();
                    login = m.getLogin();
                }
            } catch (SocketException e1) {
                System.out.println(login + " disconnected");
                Server.getUserList().delete(login);
                broadcast(Server.getUserList().getClientsList(), new Message("Server.", "The user " + login + " left the chat", Server.getUserList().getUsers()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if ((!m.getMessage().equals(inf.initial)) && (!m.getMessage().equals("exit"))) {
                System.out.println("[" + login + "]: " + m.getMessage());
                broadcast(Server.getUserList().getClientsList(), m);
                Server.getChat().add(m);
            } else {
                if (m.getMessage().equals("exit")) {
                    Server.getUserList().delete(login);
                    broadcast(Server.getUserList().getClientsList(), new Message("Server", "The user " + login + " left the chat", Server.getUserList().getUsers()));
                    //         timer.stop();
                }
                try {
                    if (outputStream != null) {
                        m.setOnlineUsers(Server.getUserList().getUsers());
                        Server.getUserList().add(login, socket, outputStream, inputStream);
                        outputStream.writeObject(m);
                        broadcast(Server.getUserList().getClientsList(), new Message("Server", "The user " + login + " joined the chat"));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void broadcast(ArrayList<Client> clientsArrayList, Message message) {
        try {
            for (Client client : clientsArrayList) {
                client.getoos().writeObject(message);
            }
        } catch (SocketException e) {
            System.out.println(login + " disconnected");
            Server.getUserList().delete(login);
            broadcast(Server.getUserList().getClientsList(), new Message("Server", "The user " + login + " left the chat", Server.getUserList().getUsers()));
            timer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

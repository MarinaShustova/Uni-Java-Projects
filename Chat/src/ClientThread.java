import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientThread extends Thread {

    private final static int DELAY = 30000;

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
     //   try {
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

//            timer = new Timer(DELAY, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    try {
//                        if (rec == send) {
//                            if (OS != null) {
//                                OS.writeObject(new Ping());
//                                ++send;
//                            }
//                        } else {
//                            throw new SocketException();
//                        }
//                    } catch (SocketException e1) {
//                        Server.getUserList().delete(login);
//                        broadcast(Server.getUserList().getClientsList(), new Message("Server", "The user " + login + " left the chat", Server.getUserList().getUsers()));
//                        flag = true;
//                        timer.stop();
//                    } catch (IOException ex2) {
//                        ex2.printStackTrace();
//                    }
//                }
//            });

 //           timer.start();
//            try {
//                if (outputStream != null) {
//                    outputStream.writeObject(new Ping());
//                    ++send;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
                }catch (SocketException e1){
                    System.out.println(login + " disconnected");
                    Server.getUserList().delete(login);
                    broadcast(Server.getUserList().getClientsList(), new Message("Server.", "The user " + login + " left the chat", Server.getUserList().getUsers()));
//                    timer.stop();
                }
                catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (m instanceof Ping) {
                    ++rec;
                } else if ((!m.getMessage().equals(inf.initial)) && (!m.getMessage().equals("exit"))) {
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
   //     }catch (SocketException e){

   //     }
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
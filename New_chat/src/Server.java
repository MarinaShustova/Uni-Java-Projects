import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    private static UserList list = new UserList();
    private static Chat chat = new Chat();

    public static void serverRun() {
        try {
            ServerSocket socketListener = new ServerSocket(1234);
            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                new ClientThread(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public synchronized static UserList getUserList() {
        return list;
    }

        public synchronized static Chat getChat() {
        return chat;
    }
}
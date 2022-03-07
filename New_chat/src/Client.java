import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Client(Socket s){
        socket = s;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            Thread remote = new Thread(new Reader());
            remote.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Reader implements Runnable{
        Object m;
        @Override
        public void run() {
            try{
                while ((m = (Message)ois.readObject()) != null){
                    System.out.println("[" + ((Message) m).getLogin() + "]: " + ((Message) m).getMessage());
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clientRun(){
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String login = "";
        String message = "";
        System.out.println("Please enter your login: ");
        try {
            login = consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                message = consoleReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (message.toLowerCase().equals("exit")) {
                System.out.println("[Client_part] You left the chat.");
                break;
            }
            try {
                oos.writeObject(new Message(login, message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            consoleReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getoos() {
        return oos;
    }

    public ObjectInputStream getois() {
        return ois;
    }

    public void setoos(ObjectOutputStream oo) {
        oos = oo;
    }

    public void setois(ObjectInputStream oi) {
        ois = oi;
    }

}

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(Socket s , ObjectOutputStream oo , ObjectInputStream oi ){
        socket = s;
        oos = oo;
        ois = oi;
    }

    public void clientRun(){
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String login = "";
        System.out.println("Please enter your login: ");
        try {
            login = consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(new Message(login, "in"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = "";
        ReadThread rt = new ReadThread();
        new Thread(rt).start();
        while (true) {
            try {
                message = consoleReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (message.toLowerCase().equalsIgnoreCase("exit")) {
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

    public class ReadThread implements Runnable {
        public void run(){
            Message m = new Message("a", "b");
            while (m != null){
                m = null;
                try {
                    m = (Message)ois.readObject();
                    if (m instanceof Ping){
                        oos.writeObject(new Ping());
                    }
                    else if(m.getMessage().equals("in")){
                    }
                    else if(m.getMessage().equals("exit")){
                        m = null;
                        return;
                    }
                    else if(m.getLogin().equals("Server.")){
                        return;
                    }
                    else
                        System.out.println("["+ m.getLogin() +"] " + m.getMessage());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

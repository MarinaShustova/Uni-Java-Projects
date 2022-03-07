import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0){
            if (args[0].equals("server"))
                Server.serverRun();
            else if (args[0].equals("client")){
                try {
                    Client client = new Client(new Socket("127.0.0.1", 1234));
                    client.clientRun();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Incorrect call");
        }
    }
}

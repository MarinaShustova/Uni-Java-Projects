import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args[0].equals("Server")) {
            Server s = new Server();
            s.my_main();
        }
        else if (args[0].equals("Client")){
            Client c = new Client();
            try {
                c.my_main();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

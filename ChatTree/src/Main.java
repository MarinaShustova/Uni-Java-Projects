import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 3) {
            Node n = new Node(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        }
        else  {
            try {
                Node n = new Node(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), InetAddress.getByName(args[4]));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
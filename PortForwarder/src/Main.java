import java.io.IOException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 3){
            Integer lport = Integer.parseInt(args[0]);
            String host = args[1];
            Integer rport = Integer.parseInt(args[2]);
            try {
                Forwarder frwd = new Forwarder(lport, host, rport);
                frwd.forward();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

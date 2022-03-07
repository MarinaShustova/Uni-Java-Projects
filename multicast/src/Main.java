import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Multicast prog = new Multicast();
        try {
            prog.multicast(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Automat automat = new Automat(args[0]);
            System.out.println(automat.isInLanguage(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

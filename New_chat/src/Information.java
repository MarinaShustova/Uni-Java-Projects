import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Information {
    public int port;
    public int len;
    public String initial;

    public Information() {
//        Properties prop = new Properties();
//        InputStream inf = getClass().getClassLoader().getResourceAsStream("conf.properties");
        try {
//            prop.load(inf);
            port = 80;
            len = 10;
            initial = "in";
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            inf.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
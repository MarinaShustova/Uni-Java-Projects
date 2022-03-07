import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Information {
    public int port;
    public int len;
    public String initial;

    public Information() {
        //  Properties prop = new Properties();
        //    FileInputStream inf = null;
        Properties prop = new Properties();
        InputStream inf = getClass().getClassLoader().getResourceAsStream("conf.properties");
        try {
            prop.load(inf);
            port = Integer.parseInt(prop.getProperty("port"));
            len = Integer.parseInt(prop.getProperty("len"));
            initial = prop.getProperty("initial");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
           // inf = new FileInputStream("conf.properties");
        //    prop.load(inf);

}
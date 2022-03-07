import factory.AccessoryStorage;
import factory.BodyStorage;
import factory.CarStorage;
import factory.EngineStorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Informator {
    public int bodyss;
    public int enginess;
    public int accessoryss;
    public int carss;
    public int suppliers;
    public int workers;
    public int dealers;
    public int dpause;
    public int cpause;
    public int dealpause;

    public  void getInf(){
        Properties prop =  new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("conf.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bodyss = Integer.parseInt(prop.getProperty("bodyss"));
        enginess = Integer.parseInt(prop.getProperty("enginess"));
        accessoryss = Integer.parseInt(prop.getProperty("accessoryss"));
        carss = Integer.parseInt(prop.getProperty("carss"));
        suppliers = Integer.parseInt(prop.getProperty("suppliers"));
        workers = Integer.parseInt(prop.getProperty("workers"));
        dealers = Integer.parseInt(prop.getProperty("dealers"));
        dpause = Integer.parseInt(prop.getProperty("dpause"));
        cpause = Integer.parseInt(prop.getProperty("cpause"));
        dealpause = Integer.parseInt(prop.getProperty("dealpause"));
    }
}

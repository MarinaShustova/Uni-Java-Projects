package factory;

public class Car implements Detail {
    private Accessory accessory;
    private Body body;
    private Engine engine;
    private int num = 0;

    public Car(int s, Accessory a, Body b, Engine e) {
        accessory = a;
        body = b;
        engine = e;
        num = s;
    }

    @Override
    public int getSerialNumber() {
        return num;
    }
}

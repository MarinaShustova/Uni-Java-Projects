package factory;

public class Engine implements Detail {
    int num;

    public Engine(int ber) {
        num = ber;
    }
    @Override
    public int getSerialNumber() {
        return num;
    }
}

package factory;

public class Body implements Detail {
    public  int num;

    public Body(int ber) {
        num = ber;
    }
    @Override
    public int getSerialNumber() {
        return num;
    }
}

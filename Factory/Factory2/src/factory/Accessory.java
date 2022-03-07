package factory;

public class Accessory implements Detail {
    int num;

    public Accessory(int ber) {
        num = ber;
    }
    @Override
    public int getSerialNumber() {
        return num;
    }

//    @Override
//    public String getInfo() {
//        return null;
//    }
}

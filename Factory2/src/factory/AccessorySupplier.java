package factory;
import threads.ManagedThread;
public class AccessorySupplier extends ManagedThread implements Producer {
    private AccessoryStorage storage;
    private long pause;
    public static int count;

    public AccessorySupplier(AccessoryStorage s, long p) {
        storage = s;
        pause = p;
        count = 0;
    }

    @Override
    public void produce() {
        Detail accessory = new Accessory(count++);
        storage.put((Accessory) accessory);
//        return accessory;
    }

    @Override
    public void run() {
        try {
            while (keepRunning()) {
                Thread.sleep(pause);
                produce();
            }
        }
        catch (InterruptedException interruptedException) {
            System.out.printf("%s is interrupted", Thread.currentThread().getName());
        }
    }
}

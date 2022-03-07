package factory;
import threads.ManagedThread;
public class EngineProducer extends ManagedThread implements Producer {
    private EngineStorage storage;
    private long pause;
    public int count;

    public EngineProducer(EngineStorage s, long p) {
        storage = s;
        pause = p;
        count = 0;
    }

    @Override
    public void produce() {
        Detail e = new Engine(count++);
        storage.put((Engine) e);
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


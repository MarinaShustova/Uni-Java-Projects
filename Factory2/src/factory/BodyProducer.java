package factory;
import threads.ManagedThread;
public class BodyProducer extends ManagedThread implements Producer{
    private BodyStorage storage;
    private long pause;
    public int count;

    public BodyProducer(BodyStorage s, long p) {
        storage = s;
        pause = p;
        count = 0;
    }

    @Override
    public void produce() {
        Detail body = new Body(count++);
        storage.put((Body) body);
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


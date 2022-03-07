package factory;
import threads.ManagedThread;
public class Worker extends ManagedThread implements Producer{

    AccessoryStorage accessoryS;
    BodyStorage bodyS;
    EngineStorage engineS;
    CarStorage carS;
    public static int num;
    private int pause;

    public Worker(AccessoryStorage a, BodyStorage b, EngineStorage e, CarStorage c, int p) {
        pause = p;
        accessoryS = a;
        bodyS = b;
        engineS = e;
        carS = c;
        num = 0;
    }

    @Override
    public void produce() {
        Detail car = new Car(num, accessoryS.get(), bodyS.get(), engineS.get());
        ++num;
        carS.put((Car) car);
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

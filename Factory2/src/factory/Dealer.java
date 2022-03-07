package factory;
import threads.*;
public class Dealer extends ManagedThread{
    private CarStorage carStorage;
    private int pause;
    public static int num;

    public Dealer(CarStorage c, int p){
        carStorage = c;
        pause = p;
    }

    @Override
    public void run() {
        try {
            while (keepRunning()) {
                Thread.sleep(pause);
                deal();
            }
        }
        catch (InterruptedException interruptedException) {
            System.out.printf("%s interrupted", Thread.currentThread().getName());
        }
    }

    public synchronized void deal(){
        num++;
        Car car = carStorage.get();
    }

}

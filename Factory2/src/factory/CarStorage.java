package factory;

import java.util.LinkedList;

public class CarStorage {
    private int capacity;
    public LinkedList<Car> queue;

    public CarStorage(int c) {
        capacity = c;
        queue = new LinkedList<>();
    }
    public synchronized void put (Car car){
        boolean f = true;
        while (f){
            if (queue.size() < capacity){
                queue.add(car);
                f = false;
                notifyAll();
                return;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Interrupted in wait");
            }
        }
    }

    public synchronized Car get(){
        Car c;
        while (true){
            if (!queue.isEmpty()) {
                c = queue.remove(0);
                notifyAll();
                return c;
            }
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted in wait");
            }
        }
    }
}

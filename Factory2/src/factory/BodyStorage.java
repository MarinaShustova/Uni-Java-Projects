package factory;

import java.util.LinkedList;

public class BodyStorage {
    private int capacity;
    public LinkedList<Body> queue;

    public BodyStorage(int c) {
        capacity = c;
        queue = new LinkedList<>();
    }
    public synchronized void put (Body body){
        boolean f = true;
        while (f){
            if (queue.size() < capacity){
                queue.add(body);
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

    public synchronized Body get(){
        Body b;
        while (true){
            if (!queue.isEmpty()) {
                b = queue.remove(0);
                notifyAll();
                return b;
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

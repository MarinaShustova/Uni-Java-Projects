package factory;

import java.util.LinkedList;

public class AccessoryStorage {
    private int capacity;
    public LinkedList<Accessory> queue;

    public AccessoryStorage(int c) {
        capacity = c;
        queue = new LinkedList<>();
    }
     public synchronized void put (Accessory accessory){
        boolean f = true;
        while (f){
            if (queue.size() < capacity){
                queue.add(accessory);
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

    public synchronized Accessory get(){
        Accessory acc;
        while (true){
            if (!queue.isEmpty()) {
                acc = queue.remove(0);
                notifyAll();
                return acc;
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

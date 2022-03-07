package factory;

import java.util.LinkedList;

public class EngineStorage {
    private int capacity;
    public LinkedList<Engine> queue;

    public EngineStorage(int c) {
        capacity = c;
        queue = new LinkedList<>();
    }
    public synchronized void put (Engine engine){
        boolean f = true;
        while (f){
            if (queue.size() < capacity){
                queue.add(engine);
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

    public synchronized Engine get(){
        Engine engine;
        while (true){
            if (!queue.isEmpty()) {
                engine = queue.remove(0);
                notifyAll();
                return engine;
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

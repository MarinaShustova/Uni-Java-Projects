import java.util.*;
public class Store {
    List queue = new ArrayList();

    public Store() {
    }
    public synchronized Object get()
    {
        if (queue.isEmpty())
            try {
                wait();
                return get();
            }
            catch (Exception ex) {
                System.err.println("Interrupted while wait");
                return null;
            }
        else
            return queue.remove(0);
    }
    public synchronized void put(Object o)
    {
        queue.add(o);
        notify();
    }
    public static void main(String[] args) {
        Store store1 = new Store();
        Getter getter = new Getter(store1);
        Putter putter = new Putter(store1, 10);
        Thread putterT = new Thread(putter);
        Thread getterT = new Thread(getter);
        putterT.start();
        getterT.start();
    }
}
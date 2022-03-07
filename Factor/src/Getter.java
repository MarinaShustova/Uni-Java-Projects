public class Getter implements Runnable {

    Store store;
    public Getter(Store s) {
        store =s;
    }
    public void run()
    {
        while(true)
        {
            Object o = store.get();
            System.out.println(" getter: "+o);
        }
    }
}
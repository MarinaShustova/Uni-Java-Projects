public class Putter implements Runnable {

    int count;
    Store s;
    public Putter(Store s, int c) {
        count = c;
        this.s = s;
    }
    public void run()
    {
        try {
            for (int i=0; i < count;i++)
            {
                Thread.sleep(1000);
                System.out.println("Putter: "+"Widjet#"+i);
                s.put("Widjet#"+i);
            }
        }
        catch (InterruptedException ex) {

        }
    }

}
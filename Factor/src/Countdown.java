public class Countdown implements Task {
    private String name;
    private int counter;
    private int timeQuant;

    public Countdown(String name, int count, int quant) {
        this.name = name;
        counter = count;
        timeQuant = quant;
    }
    public void performWork() throws InterruptedException
    {
        System.out.println(name+" " +counter);
        for (int i = counter-1; i >= 0; i--)
        {
            Thread.sleep(timeQuant);
//         if (new java.util.Random().nextInt(30)==5) {
//           Thread.currentThread().interrupt();
//         }
            System.out.println(name + " " +i);
        }
    }
    public String getName()
    {
        return name;
    }
}
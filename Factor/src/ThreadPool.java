import java.util.*;
public class ThreadPool implements TaskListener {
    private static final int THREAD_COUNT = 3;
    //private List allocatedThreads = new ArrayList();
    private List taskQueue = new LinkedList();
    private Set availableThreads = new HashSet();

    public void taskStarted(Task t)
    {
        System.out.println("Started: "+t.getName());
    }
    public void taskFinished(Task t)
    {
        System.out.println("Finished: "+t.getName());
    }
    public void taskInterrupted(Task t)
    {
        System.out.println("Interrupted: " + t.getName());
    }

    public void addTask(Task t)
    {
        addTask(t,this);
    }

    public void addTask(Task t, TaskListener l)
    {
        synchronized (taskQueue)
        {
            taskQueue.add(new ThreadPoolTask(t, l));
            taskQueue.notify();
        }
    }

    public ThreadPool() {
        for (int i=0;i<THREAD_COUNT;i++)
        {
            availableThreads.add(new PooledThread("Performer_"+i,taskQueue));
        }
        for (Iterator iter = availableThreads.iterator();iter.hasNext();)
        {
            ((Thread)iter.next()).start();
        }

    }
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i<10 ;i++)
        {
            threadPool.addTask(new Countdown("C"+i,10,(int)((i+1)/2.*1000)));
        }
//      while(true)
//      {
//         try {
//            Thread.sleep(100000);
//         }
//         catch (Exception ex) {
//
//         }
//
//      }
    }
}
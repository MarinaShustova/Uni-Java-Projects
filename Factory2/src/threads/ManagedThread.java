package threads;
public class ManagedThread extends Thread {
    ThreadState state = ThreadState.RUNNING;

    protected synchronized ThreadState getDesiredState() {
        return this.state;
    }

    public void mstop() {
        synchronized (this) {
            this.state = ThreadState.STOP;
            this.notifyAll();
        }
    }

    public void msuspend() {
        synchronized (this) {
            if (this.state != ThreadState.STOP) {
                this.state = ThreadState.SLEEP;
            }
        }
    }

    public void mresume() {
        synchronized (this) {
            if (this.state != ThreadState.STOP) {
                this.state = ThreadState.RUNNING;
                this.notifyAll();
            }
        }
    }

    protected boolean keepRunning() {
        synchronized (this) {
            if (this.state == ThreadState.RUNNING) {
                return true;
            } else {
                while (true) {
                    if (this.state == ThreadState.STOP) {
                        System.out.println(Thread.currentThread().getName() + " stopped");
                        return false;
                    } else if (this.state == ThreadState.SLEEP) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " fell asleep");
                            this.wait();
                        } catch (Exception ex) {
                            System.err.println(Thread.currentThread().getName() + " interrupted");
                            return false;
                        }
                    } else if (this.state == ThreadState.RUNNING) {
                        System.out.println("\u0001 resumed" + Thread.currentThread().getName());
                        return true;
                    }
                }
            }
        }
    }

}
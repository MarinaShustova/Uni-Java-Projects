package threads;
public enum ThreadState {
    RUNNING (0),
    SLEEP (1) ,
    STOP (2);
    int value;
    ThreadState(int val) {
        value= val;
    }

    public int getValue() {
        return value;
    }

    static ThreadState fromValue(int val) {
        if (val <0 || val>2 )
            return null;
        for (ThreadState s : ThreadState.values())  {
            if (s.getValue() == val)
                return s;
        }
        return null;
    }
}
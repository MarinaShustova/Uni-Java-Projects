import java.util.ArrayList;

public class CycleClose implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        int size = env.stack.size();
        if (env.stack.get(size-1) != 0.0)
        {
            env.current_pos = env.cycle_beg.get(env.curr_cycle);
            --env.curr_cycle;
         //   env.stack.remove(size-1);
        }
        else
        {
            if (env.skip == true) {
                env.skip = false;
                env.stack.remove((size - 1));
            }
            else {
                env.stack.remove((size - 1));
                env.cycle_beg.add(env.curr_cycle, -1);
                --env.curr_cycle;
            }
            ++env.current_pos;
        }
    }
}
import java.util.ArrayList;

public class CycleOpen implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.stack.get(env.stack.size()-1) != 0)
        {
            env.stack.remove(env.stack.size() - 1);
            ++env.curr_cycle;
            env.cycle_beg.add(env.curr_cycle, env.current_pos);
        }
        else
            env.skip = true;
        ++env.current_pos;
    }
}
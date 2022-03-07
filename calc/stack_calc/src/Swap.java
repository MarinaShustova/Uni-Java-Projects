import java.util.ArrayList;

public class Swap implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            Double a;
            int size = env.stack.size();
            a = env.stack.get(size - 1);
            env.stack.set(size - 1, env.stack.get(size - 2));
            env.stack.set(size - 2, a);
        }
        ++env.current_pos;
    }
}

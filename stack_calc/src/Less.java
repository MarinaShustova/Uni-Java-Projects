import java.util.ArrayList;

public class Less implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            int size = env.stack.size();
            Double a, b;
            a = env.stack.get(size - 1);
            env.stack.remove(size - 1);
            --size;
            b = env.stack.get(size - 1);
            env.stack.remove(size - 1);
            if (b < a)
                env.stack.add(1.0);
            else
                env.stack.add(0.0);
        }
        ++env.current_pos;
    }
}
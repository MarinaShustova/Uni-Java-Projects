import java.util.ArrayList;

public class Dup implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            Double a;
            int size = env.stack.size();
            a = env.stack.get(size - 1);
            env.stack.add(a);
        }
        ++env.current_pos;
    }
}

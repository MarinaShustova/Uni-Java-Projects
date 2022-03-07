import java.util.ArrayList;

public class Sqrt implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            Double a;
            a = env.stack.get(env.stack.size() - 1);
            env.stack.remove(env.stack.size() - 1);
            a = Math.sqrt(a);
            env.stack.add(a);
        }
        ++env.current_pos;
    }
}

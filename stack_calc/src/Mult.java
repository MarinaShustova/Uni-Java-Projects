import java.util.ArrayList;

public class Mult implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            Double a, b, c;
            a = env.stack.get(env.stack.size() - 1);
            env.stack.remove(env.stack.size() - 1);
            b = env.stack.get(env.stack.size() - 1);
            env.stack.remove(env.stack.size() - 1);
            c = a * b;
            env.stack.add(c);
        }
        ++env.current_pos;
    }
}

import java.util.ArrayList;

public class Print implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true) {
            int size = env.stack.size();
            System.out.println(env.stack.get(size - 1));
        }
        ++env.current_pos;
    }
}

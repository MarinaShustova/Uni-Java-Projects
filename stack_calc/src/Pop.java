import java.util.ArrayList;

public class Pop implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true)
        env.stack.remove(env.stack.size()-1);
        ++env.current_pos;
    }
}
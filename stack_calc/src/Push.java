import java.util.ArrayList;

public class Push implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if (env.skip != true)
        env.stack.add(env.val);
        env.current_pos+=2;
    }
}
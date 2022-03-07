import java.util.ArrayList;

public class Define implements Operation
{
    public void Do(ArrayList<String> comms, Environment env)
    {
        if(env.skip != true) {
            if (!env.params.containsKey(comms.get(env.current_pos + 1)))
                env.params.put(comms.get(env.current_pos + 1), Double.parseDouble(comms.get(env.current_pos + 2)));
            else
                env.params.replace(comms.get(env.current_pos + 1), Double.parseDouble(comms.get(env.current_pos + 2)));
        }
            env.current_pos += 3;
    }
}
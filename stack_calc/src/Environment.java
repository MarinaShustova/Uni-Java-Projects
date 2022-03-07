import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Environment
{
    public ArrayList<Double> stack;
    public Map<String, String> list;
    public Map<String, Double> params;
    public ArrayList<Integer> cycle_beg;
    public Double val;
    public boolean skip;
    public Integer curr_cycle, current_pos;
    public Environment() {
        stack = new ArrayList<>();
        list = new HashMap<>();
        params = new HashMap<>();
        cycle_beg = new ArrayList<>();
        curr_cycle = 0;
        cycle_beg.add(-1);
        current_pos = 0;
        val = 0.0;
        skip = false;
    }
}
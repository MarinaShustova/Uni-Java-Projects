import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

public class ExecutorTest {

    @org.junit.Test
    public void exec() {
        Executor ex1 = null;
        Executor ex2 = null;
        Executor ex3 = null;
        try {
            ex1 = new Executor();
            ex2 = new Executor();
            ex3 = new Executor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Environment env1 = new Environment();
        Environment env2 = new Environment();
        Environment env3 = new Environment();
        String[] b1 = {"2", "3", "+", "print"};
        String[] b2 = {"5", "1", "swap", "dup", "[", "dup", "rot", "*", "swap", "1", "-", "dup", "]", "drop", "print"};
        String[] b3 = {"0", "1",  "<", "[",  "1",  "print",  "0",  "]"};
        ArrayList<String> res1 = new ArrayList(Arrays.asList(b1));
        ArrayList<String> res2 = new ArrayList(Arrays.asList(b2));
        ArrayList<String> res3 = new ArrayList(Arrays.asList(b3));
        env1.stack = new ArrayList(Arrays.asList(5.0));
        env1.cycle_beg = new ArrayList(Arrays.asList(-1));
        env1.curr_cycle = 0;
        env1.current_pos = 4;
        env1.val = 3.0;
        env1.skip = false;
        env2.stack = new ArrayList(Arrays.asList(120.0));
        env2.cycle_beg = new ArrayList(Arrays.asList(-1, -1, 4, 4, 4, 4, 4));
        env2.curr_cycle = 0;
        env2.current_pos = 15;
        env2.val = 1.0;
        env2.skip = false;
        env3.stack = new ArrayList(Arrays.asList(1.0));
        env3.cycle_beg = new ArrayList(Arrays.asList(-1, -1, 3));
        env3.curr_cycle = 0;
        env3.current_pos = 8;
        env3.val = 0.0;
        env3.skip = false;
        if (ex1 == null) {
            System.out.println("Executor failed.");
            return;
        }
        if (ex2 == null) {
            System.out.println("Executor failed.");
            return;
        }
        if (ex3 == null) {
            System.out.println("Executor failed.");
            return;
        }
        ex1.exec(res1);
        assertEquals(ex1.env.stack, env1.stack);
        assertEquals(ex1.env.cycle_beg, env1.cycle_beg);
        assertEquals(ex1.env.curr_cycle, env1.curr_cycle);
        assertEquals(ex1.env.current_pos, env1.current_pos);
        assertEquals(ex1.env.val, env1.val);
        assertEquals(ex1.env.skip, env1.skip);
        ex2.exec(res2);
        assertEquals(ex2.env.stack, env2.stack);
        assertEquals(ex2.env.cycle_beg, env2.cycle_beg);
        assertEquals(ex2.env.curr_cycle, env2.curr_cycle);
        assertEquals(ex2.env.current_pos, env2.current_pos);
        assertEquals(ex2.env.val, env2.val);
        assertEquals(ex2.env.skip, env2.skip);
        ex3.exec(res3);
        assertEquals(ex3.env.stack, env3.stack);
        assertEquals(ex3.env.cycle_beg, env3.cycle_beg);
        assertEquals(ex3.env.curr_cycle, env3.curr_cycle);
        assertEquals(ex3.env.current_pos, env3.current_pos);
        assertEquals(ex3.env.val, env3.val);
        assertEquals(ex3.env.skip, env3.skip);
    }
}
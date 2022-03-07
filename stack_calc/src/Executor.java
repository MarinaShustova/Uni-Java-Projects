import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Executor
{
    Properties prop;
    Environment env;
    Executor() throws IOException {
        prop =  new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("conf.properties");
        prop.load(is);
        is.close();
        env = new Environment();
    }
    void exec(ArrayList<String> commandstr) {
        String lex;
        int size = commandstr.size();
        while (env.current_pos != size) {
            lex = commandstr.get(env.current_pos);
            if (isDouble(lex)) {
                env.val = Double.parseDouble(lex);
                lex = "push";
                --env.current_pos;
            }
            else if (env.params.containsKey(lex)) {
                env.val = env.params.get(lex);
                lex = "push";
                --env.current_pos;
            }
            if (!env.list.containsKey(lex))
                env.list.put(lex, prop.getProperty(lex));
            try {
                Class operation = Class.forName(env.list.get(lex));
                Object op = operation.getDeclaredConstructor().newInstance();
                Operation opp = (Operation) op;
                opp.Do(commandstr, env);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
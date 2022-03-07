import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

class StackCalc
{
    void run(String[] arg)
    {
        StackMaker commonstack = new StackMaker();
        if (arg.length != 0) {
            try (FileInputStream fin = new FileInputStream(arg[0]);
                 Scanner scan = new Scanner(fin)) {
                commonstack.MakeCommsList(scan);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            try (Scanner scan = new Scanner(System.in)) {
                commonstack.MakeCommsList(scan);
            }
        Executor executor = null;
        try {
            executor = new Executor();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (executor == null) {
            System.out.println("Executor failed.");
            return;
        }
        executor.exec(commonstack.comms);
    }
}

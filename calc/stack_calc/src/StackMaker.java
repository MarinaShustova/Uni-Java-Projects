import java.util.ArrayList;
import java.util.Scanner;

class StackMaker
{
    ArrayList<String> comms = new ArrayList<>();
    void MakeCommsList(Scanner in)
    {
        while (in.hasNext())
            comms.add(in.next());
    }

}
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.*;

public class StackMakerTest {

    @Test
    public void makeCommsList() {
        String data1 = "2 3 + print";
        String data2 = "5 1 swap dup [ dup rot * swap 1 - dup ] drop print";
        String data3 = "0 1 < [ 1 print 0 ]";
        String[] b1 = {"2", "3", "+", "print"};
        String[] b2 = {"5", "1", "swap", "dup", "[", "dup", "rot", "*", "swap", "1", "-", "dup", "]", "drop", "print"};
        String[] b3 = {"0", "1",  "<", "[",  "1",  "print",  "0",  "]"};
        ArrayList<String> res1 = new ArrayList(Arrays.asList(b1));
        ArrayList<String> res2 = new ArrayList(Arrays.asList(b2));
        ArrayList<String> res3 = new ArrayList(Arrays.asList(b3));
        InputStream stdin = System.in;
        StackMaker S1 = new StackMaker();
        StackMaker S2 = new StackMaker();
        StackMaker S3 = new StackMaker();
        try {
            System.setIn(new ByteArrayInputStream(data1.getBytes()));
            Scanner S = new Scanner(System.in);

            S1.MakeCommsList(S);
            S.close();
            System.setIn(new ByteArrayInputStream(data2.getBytes()));
            Scanner SS = new Scanner(System.in);

            S2.MakeCommsList(SS);
            SS.close();
            System.setIn(new ByteArrayInputStream(data3.getBytes()));
            Scanner SSS = new Scanner(System.in);

            S3.MakeCommsList(SSS);
            SSS.close();
        } finally {
            System.setIn(stdin);
        }
        assertEquals(res1, S1.comms);
        assertEquals(res2, S2.comms);
        assertEquals(res3, S3.comms);
    }
}
package searcher;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

import static java.util.Map.entry;
import static org.junit.Assert.*;

public class searcherTest {

//    @Test
//    public void parse() {
//
//    }

    @Test
    public void mkVect() {
        String data1 = "Twinkle twinkle little star how I wonder what you are";
        String data2 = "";
        String data3 = "Up above the world so high, like a diamond in the sky";
        Vector<String> buff1 = new Vector<>();
        Vector<String> buff2 = new Vector<>();
        Vector<String> buff3 = new Vector<>();
        Vector<String> ret1 = new Vector<>();
        Vector<String> ret2 = new Vector<>();
        Vector<String> ret3 = new Vector<>();
        String[] b1 = {"Twinkle", "twinkle", "little", "star", "how", "I", "wonder", "what", "you", "are"};
        String[] b2 = {};
        String[] b3 = {"Up", "above", "the", "world", "so",  "high,", "like", "a", "diamond", "in", "the", "sky"};
        ArrayList arrayList1 = new ArrayList(Arrays.asList(b1));
        ArrayList arrayList2 = new ArrayList(Arrays.asList(b2));
        ArrayList arrayList3 = new ArrayList(Arrays.asList(b3));
        buff1.addAll(arrayList1);
        buff2.addAll(arrayList2);
        buff3.addAll(arrayList3);
        InputStream stdin = System.in;
        try {
            //searcher S = new searcher();
            searcher.fkey = 0;
            System.setIn(new ByteArrayInputStream(data1.getBytes()));
            //Scanner scanner = new Scanner(System.in);
            searcher.mkVect(ret1);
            System.setIn(new ByteArrayInputStream(data2.getBytes()));
            searcher.mkVect(ret2);
            //Scanner scanner2 = new Scanner(System.in);
            System.setIn(new ByteArrayInputStream(data3.getBytes()));
            searcher.mkVect(ret3);
            //Scanner scanner = new Scanner(System.in);
        //    System.out.println(scanner.nextLine());
        } finally {
            System.setIn(stdin);
        }
        assertEquals(ret1, buff1);
        assertEquals(ret2, buff2);
        assertEquals(ret3, buff3);
    }

    @Test
    public void mkMap() {
        Vector<String> buff1 = new Vector<>();
        Vector<String> buff2 = new Vector<>();
        Vector<String> buff3 = new Vector<>();
        String[] b1 = {  "twinkle" , "twinkle" , "little" , "star" ,"how" , "I" , "wonder" , "what" , "you" , "are"  };
        String[] b2 = { "I", "think", "we", "are", "doomed", "I" , "think" , "we" , "are" , "doomed"  };
        String[] b3 = { "Work", "it", "harder", "Make", "it", "better", "Do", "it", "faster", "Makes", "us", "stronger", "Work", "it", "harder", "Make", "it", "better", "Do", "it", "faster", "Makes", "us", "stronger"};
        Map<String, Integer> ret1 = Map.ofEntries(  entry("twinkle", 2 ),entry( "little", 1 ), entry("star", 1 ), entry("how", 1 ),entry( "I", 1 ), entry("wonder", 1),entry("what", 1 ),entry("you", 1),entry("are", 1 ));
        Map<String, Integer> ret2 = Map.ofEntries( entry("I think we are doomed", 2), entry("think we are doomed I", 1 ), entry("we are doomed I think", 1), entry("are doomed I think we", 1 ), entry("doomed I think we are", 1));
        Map<String, Integer> ret3 = Map.ofEntries(  entry("Work", 2 ),entry("it", 6 ),entry( "harder", 2 ),entry( "Make", 2 ), entry("better", 2 ), entry("Do", 2 ), entry("faster", 2 ), entry("Makes", 2 ), entry("us", 2 ), entry("stronger", 2 ));
        ArrayList arrayList1 = new ArrayList(Arrays.asList(b1));
        ArrayList arrayList2 = new ArrayList(Arrays.asList(b2));
        ArrayList arrayList3 = new ArrayList(Arrays.asList(b3));
        buff1.addAll(arrayList1);
        buff2.addAll(arrayList2);
        buff3.addAll(arrayList3);
        searcher.nkey = 1;
        assertEquals(ret1, searcher.mkMap(buff1));
        searcher.nkey = 5;
        assertEquals(ret2, searcher.mkMap(buff2));
        searcher.nkey = 1;
        assertEquals(ret3, searcher.mkMap(buff3));
    }

    @Test
    public void srtMap() {
    }

//    @Test
//    public void print() {
//    }

//    @Test
//    public void doMain() {
//    }
}
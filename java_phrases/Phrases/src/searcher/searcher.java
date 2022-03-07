package searcher;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class searcher {
    static int fkey = 0, nkey = 2, mkey = 2;
    static String filename = new String();
    static void parse(String[] ar)
    {
        int amount, i = 0;
        amount = ar.length;
        while (amount>0)
        {
            if (ar[i].equals("-n")) {
                nkey = Integer.parseInt(ar[i + 1]);
                i += 2;
                --amount;
            }
            if (ar[i].equals("-m")) {
                mkey = Integer.parseInt(ar[i + 1]);
                i += 2;
                --amount;
            }
            if ((!ar[i].equals("-n"))&&(!ar[i].equals("-m"))&&(!ar[i].equals("-")))
            {
                filename = ar[i];
                fkey = 1;
                ++i;
            }
            --amount;
        }
        return;
    }

    static Vector mkVect(Vector buff)
    {
       // Vector<String> buff = new Vector<String>();
        if (fkey == 0)
        {
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext())
                buff.add(scan.next());
            scan.close();
        }
        if (fkey == 1)
        {
            try (FileInputStream fin = new FileInputStream(filename))
            {
                Scanner scan = new Scanner(fin);
                while (scan.hasNext())
                    buff.add(scan.next());
                fin.close();
                scan.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return buff;
    }

    static Map mkMap(Vector buff)
    {
        Map<String, Integer> collect = new HashMap<String, Integer>();
        StringBuilder tmp = new StringBuilder();
        int size = buff.size();
        for (int i = 0; i < size - nkey +1; ++i)
        {
            for (int j = 0; j < nkey; ++j)
            {
                if (j == 0)
                {
                    tmp.append(buff.firstElement());
                    buff.remove(0);
                }
                else
                {
                    tmp.append(" ");
                    tmp.append(buff.elementAt(j-1));
                }
            }
            if (collect.containsKey(tmp.toString()))
                collect.put(tmp.toString(), collect.get(tmp.toString())+1);
            else
                collect.put(tmp.toString(), 1);
            tmp.setLength(0);
        }
        return collect;
    }
    static List<Map.Entry<String, Integer>> srtMap(Map collect)
    {
        List<Map.Entry<String, Integer>> sort = new ArrayList<>(collect.entrySet());
        /* Collections.sort(sort, new Comparator<Map.Entry<String, Integer>>()
        {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        });*/
        Collections.sort(sort, (b, a) -> { return a.getValue().compareTo(b.getValue()); });

//        for (Map.Entry<String, Integer> entry : sort)
//        {
//            collect.put(entry.getKey(), entry.getValue());
//        }
        return sort;
    }
    static void print(List<Map.Entry<String, Integer>> collect)
    {
        collect.forEach(entry -> {
            if ((int)entry.getValue() >= mkey)
                System.out.println(entry.getKey() + " (" + entry.getValue() + ")");
        });
        return;
    }
    static void doMain(String[] ar)
    {
        Vector<String> buff = new Vector<String>();
        Map<String, Integer> collect = new HashMap<String, Integer>();
        parse(ar);
        buff = mkVect(buff);
        collect = mkMap(buff);
        List<Map.Entry<String, Integer>> sort;
        sort = srtMap(collect);
        print(sort);
    }

    public static void main(String[] args)
    {
        doMain(args);
    }
}
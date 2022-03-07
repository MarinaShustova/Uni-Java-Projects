import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Automat {
//    private ArrayList<Step> steps = new ArrayList<>();
    private ArrayList<Integer> finalStates = new ArrayList<>();
//    int currentState = 0;
    private Map<Integer, Map<Character, List<Integer>>> transitions = new HashMap<>();

    public Automat(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String s = scanner.nextLine();
        for (String retval : s.split(" "))
            finalStates.add(Integer.parseInt(retval));
        String transition;
        while (scanner.hasNextLine()){
            transition = scanner.nextLine();
            if (!transition.matches("\\d\\s\\w\\s\\d")) {
                System.out.println("Wrong format");
                break;
            }
            String[] subStrings = transition.split(" ");
            int stateNum = Integer.parseInt(subStrings[0]);
            Map event = transitions.computeIfAbsent(stateNum, k -> new HashMap<>());
            List list = (List) event.computeIfAbsent(subStrings[1].charAt(0), k -> new ArrayList<>());
            list.add(Integer.parseInt(subStrings[2]));
        }
        scanner.close();
    }

    public boolean isInLanguage(String path) throws FileNotFoundException {
        Stack<Point> stack = new Stack<>();
        stack.push(new Point(0 , 0));

        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String s = scanner.nextLine();

        while (!stack.empty()) {
            Point point = stack.pop();
            int state = point.x;
            int pos = point.y;

            List states = transitions.get(point.x).get(s.charAt(pos));
            if (states != null) {
                for (Object i : states) {
                    stack.push(new Point((Integer) i, pos + 1));
                }
                if (pos == s.length() - 1 && finalStates.contains(state)) {
                    return true;
                }
            }
        }
        return false;
    }
}

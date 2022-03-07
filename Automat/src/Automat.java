import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Automat {
    private ArrayList<Step> steps = new ArrayList<>();
    private ArrayList<Integer> finalStates = new ArrayList<>();
    int currentState = 0;
    public Automat(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        String s = scanner.nextLine();
        for (String retval : s.split(" "))
            finalStates.add(Integer.parseInt(retval));
        while (scanner.hasNextLine()){
            s = scanner.nextLine();
            if (!s.matches("\\d\\s\\w\\s\\d")) {
                System.out.println("Wrong format");
                break;
            }
            Step step = new Step(Integer.parseInt(String.valueOf(s.charAt(0))),
                    String.valueOf(s.charAt(2)),
                    Integer.parseInt(String.valueOf(s.charAt(4))));
            steps.add(step);
        }
        scanner.close();
    }
    public boolean isInLanguage(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        int character;
        while ((character = fileInputStream.read()) != -1){
            for (Step s : steps){
                if ((s.from == currentState)&&(s.at.equals(String.valueOf((char)character)))){
                    currentState = s.to;
                    break;
                }
            }
        }
        for (Integer i : finalStates){
            if (i == currentState){
                return true;
            }
        }
        return false;
    }
}

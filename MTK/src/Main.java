import java.io.*;

public class Main {
    public static void main(String[] args) {
        Reader reader;
        try {
            reader = new FileReader("examples.txt");
            Lexer lexer = new Lexer(reader);
            Parser parser = new Parser(lexer);
            int res = parser.Calculate();
            System.out.println(res);
        } catch (LexerException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

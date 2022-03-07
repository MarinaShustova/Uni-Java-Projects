import java.io.IOException;
import java.io.Reader;

public class Lexer {
    Reader reader;
    int current;
    public Lexer(Reader r) throws IOException {
        reader = r;
        current = reader.read();
    }

    Lexeme getLexeme() throws IOException, LexerException {
        while (Character.isWhitespace(current))
            current = reader.read();
        switch (current){
            case '(':
                current = reader.read();
                return new Lexeme(LexemeType.OPEN, "(");
            case ')':
                current = reader.read();
                return new Lexeme(LexemeType.CLOSE, ")");
            case '*':
                current = reader.read();
                return new Lexeme(LexemeType.MULT, "*");
            case '+':
                current = reader.read();
                return new Lexeme(LexemeType.PLUS, "+");
            case '-':
                current = reader.read();
                return new Lexeme(LexemeType.MINUS, "-");
            case '/':
                current = reader.read();
                return new Lexeme(LexemeType.DIV, "/");
            case '^':
                current = reader.read();
                return new Lexeme(LexemeType.POW, "^");
            case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
                Lexeme lexeme = new Lexeme(LexemeType.NUM, "");
                while (Character.isDigit(current)){
                    String num = lexeme.getText();
                    num += Character.getNumericValue(current);//Integer.toString(current);
                    lexeme.setText(num);
                    current = reader.read();
                }
                return lexeme;
            case -1:
                return new Lexeme(LexemeType.EOF, "EOF");
            default:
                throw new LexerException("Lexer error");
        }
    }
}

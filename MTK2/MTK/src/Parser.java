import java.io.IOException;

public class Parser {
    Lexer lexer;
    Lexeme current;

    Parser(Lexer l) throws IOException, LexerException {
        lexer = l;
        current = lexer.getLexeme();
    }

    public int Calculate() throws ParseException, IOException, LexerException {
        return parseExpr();
    }

    private int parseExpr() throws IOException, LexerException, ParseException {
        int temp = parseTerm();
        while ((current.getType() == LexemeType.PLUS) || (current.getType() == LexemeType.MINUS)){
            if (current.getType() == LexemeType.PLUS){
                current = lexer.getLexeme();
                temp += parseTerm();
            }
            if (current.getType() == LexemeType.MINUS){
                current = lexer.getLexeme();
                temp -= parseTerm();
            }
        }
        return temp;
    }

    private int parseTerm() throws ParseException, IOException, LexerException {
        int temp = parseFactor();
        while ((current.getType() == LexemeType.MULT) || (current.getType() == LexemeType.DIV)){
            if (current.getType() == LexemeType.MULT){
                current = lexer.getLexeme();
                temp *= parseTerm();
            }
            if (current.getType() == LexemeType.DIV){
                current = lexer.getLexeme();
                temp /= parseTerm();
            }
        }
        return temp;
    }

    private int parseFactor() throws ParseException, IOException, LexerException {
        int temp = parsePower();
        if (current.getType() == LexemeType.POW){
            current = lexer.getLexeme();
            temp = (int)Math.pow(temp, parseFactor());
        }
        return temp;
    }

    private int parsePower() throws IOException, LexerException, ParseException {
        if (current.getType() == LexemeType.MINUS){
            current = lexer.getLexeme();
            return -1*parseAtom();
        }
        return parseAtom();
    }

    private int parseAtom() throws IOException, LexerException, ParseException {
        if (current.getType() == LexemeType.NUM){
            int temp = Integer.parseInt(current.getText());
            current = lexer.getLexeme();
            return temp;
        }
        if (current.getType() == LexemeType.OPEN){
            current = lexer.getLexeme();
            int temp = parseExpr();
            if (current.getType() != LexemeType.CLOSE){
                throw new ParseException("Parse error");
            }
            current = lexer.getLexeme();
            return temp;
        }
        throw new ParseException("Parse error");
    }
}

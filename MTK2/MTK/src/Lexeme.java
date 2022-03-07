public class Lexeme {
    private LexemeType type;
    private String text;

    public Lexeme(LexemeType ty, String te){
        type = ty;
        text = te;
    }

    LexemeType getType(){
        return type;
    }
    void setType(LexemeType t){
        type = t;
    }
    String getText(){
        return text;
    }
    void setText(String t){
        text = t;
    }
}

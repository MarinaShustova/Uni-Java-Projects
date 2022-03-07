// Generated from C:/Users/1/IdeaProjects/Language/src/main/antlr4\RedusedMu.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class RedusedMuLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OR=1, AND=2, EQ=3, NEQ=4, GT=5, LT=6, GTEQ=7, LTEQ=8, PLUS=9, MINUS=10, 
		MULT=11, DIV=12, MOD=13, POW=14, NOT=15, SCOL=16, ASSIGN=17, OPAR=18, 
		CPAR=19, OBRACE=20, CBRACE=21, TRUE=22, FALSE=23, IF=24, ELSE=25, WHILE=26, 
		LOG=27, ID=28, INT=29, STRING=30, SPACE=31;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", "MINUS", 
			"MULT", "DIV", "MOD", "POW", "NOT", "SCOL", "ASSIGN", "OPAR", "CPAR", 
			"OBRACE", "CBRACE", "TRUE", "FALSE", "IF", "ELSE", "WHILE", "LOG", "ID", 
			"INT", "STRING", "SPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'||'", "'&&'", "'=='", "'!='", "'>'", "'<'", "'>='", "'<='", "'+'", 
			"'-'", "'*'", "'/'", "'%'", "'^'", "'!'", "';'", "'='", "'('", "')'", 
			"'{'", "'}'", "'true'", "'false'", "'if'", "'else'", "'while'", "'log'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OR", "AND", "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", "MINUS", 
			"MULT", "DIV", "MOD", "POW", "NOT", "SCOL", "ASSIGN", "OPAR", "CPAR", 
			"OBRACE", "CBRACE", "TRUE", "FALSE", "IF", "ELSE", "WHILE", "LOG", "ID", 
			"INT", "STRING", "SPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public RedusedMuLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "RedusedMu.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2!\u00a9\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3"+
		"\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3"+
		"\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3"+
		"\34\3\35\3\35\7\35\u0091\n\35\f\35\16\35\u0094\13\35\3\36\6\36\u0097\n"+
		"\36\r\36\16\36\u0098\3\37\3\37\3\37\3\37\7\37\u009f\n\37\f\37\16\37\u00a2"+
		"\13\37\3\37\3\37\3 \3 \3 \3 \2\2!\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!\3\2\7\5\2C\\aac|\6\2\62;C\\aac|\3"+
		"\2\62;\5\2\f\f\17\17$$\5\2\13\f\17\17\"\"\2\u00ac\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"+
		"\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3"+
		"\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3"+
		"\2\2\2\3A\3\2\2\2\5D\3\2\2\2\7G\3\2\2\2\tJ\3\2\2\2\13M\3\2\2\2\rO\3\2"+
		"\2\2\17Q\3\2\2\2\21T\3\2\2\2\23W\3\2\2\2\25Y\3\2\2\2\27[\3\2\2\2\31]\3"+
		"\2\2\2\33_\3\2\2\2\35a\3\2\2\2\37c\3\2\2\2!e\3\2\2\2#g\3\2\2\2%i\3\2\2"+
		"\2\'k\3\2\2\2)m\3\2\2\2+o\3\2\2\2-q\3\2\2\2/v\3\2\2\2\61|\3\2\2\2\63\177"+
		"\3\2\2\2\65\u0084\3\2\2\2\67\u008a\3\2\2\29\u008e\3\2\2\2;\u0096\3\2\2"+
		"\2=\u009a\3\2\2\2?\u00a5\3\2\2\2AB\7~\2\2BC\7~\2\2C\4\3\2\2\2DE\7(\2\2"+
		"EF\7(\2\2F\6\3\2\2\2GH\7?\2\2HI\7?\2\2I\b\3\2\2\2JK\7#\2\2KL\7?\2\2L\n"+
		"\3\2\2\2MN\7@\2\2N\f\3\2\2\2OP\7>\2\2P\16\3\2\2\2QR\7@\2\2RS\7?\2\2S\20"+
		"\3\2\2\2TU\7>\2\2UV\7?\2\2V\22\3\2\2\2WX\7-\2\2X\24\3\2\2\2YZ\7/\2\2Z"+
		"\26\3\2\2\2[\\\7,\2\2\\\30\3\2\2\2]^\7\61\2\2^\32\3\2\2\2_`\7\'\2\2`\34"+
		"\3\2\2\2ab\7`\2\2b\36\3\2\2\2cd\7#\2\2d \3\2\2\2ef\7=\2\2f\"\3\2\2\2g"+
		"h\7?\2\2h$\3\2\2\2ij\7*\2\2j&\3\2\2\2kl\7+\2\2l(\3\2\2\2mn\7}\2\2n*\3"+
		"\2\2\2op\7\177\2\2p,\3\2\2\2qr\7v\2\2rs\7t\2\2st\7w\2\2tu\7g\2\2u.\3\2"+
		"\2\2vw\7h\2\2wx\7c\2\2xy\7n\2\2yz\7u\2\2z{\7g\2\2{\60\3\2\2\2|}\7k\2\2"+
		"}~\7h\2\2~\62\3\2\2\2\177\u0080\7g\2\2\u0080\u0081\7n\2\2\u0081\u0082"+
		"\7u\2\2\u0082\u0083\7g\2\2\u0083\64\3\2\2\2\u0084\u0085\7y\2\2\u0085\u0086"+
		"\7j\2\2\u0086\u0087\7k\2\2\u0087\u0088\7n\2\2\u0088\u0089\7g\2\2\u0089"+
		"\66\3\2\2\2\u008a\u008b\7n\2\2\u008b\u008c\7q\2\2\u008c\u008d\7i\2\2\u008d"+
		"8\3\2\2\2\u008e\u0092\t\2\2\2\u008f\u0091\t\3\2\2\u0090\u008f\3\2\2\2"+
		"\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093:\3"+
		"\2\2\2\u0094\u0092\3\2\2\2\u0095\u0097\t\4\2\2\u0096\u0095\3\2\2\2\u0097"+
		"\u0098\3\2\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099<\3\2\2\2"+
		"\u009a\u00a0\7$\2\2\u009b\u009f\n\5\2\2\u009c\u009d\7$\2\2\u009d\u009f"+
		"\7$\2\2\u009e\u009b\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0"+
		"\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2\u00a0\3\2"+
		"\2\2\u00a3\u00a4\7$\2\2\u00a4>\3\2\2\2\u00a5\u00a6\t\6\2\2\u00a6\u00a7"+
		"\3\2\2\2\u00a7\u00a8\b \2\2\u00a8@\3\2\2\2\7\2\u0092\u0098\u009e\u00a0"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
// Generated from ReducedMu.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ReducedMuLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		EQ=1, NEQ=2, GT=3, LT=4, GTEQ=5, LTEQ=6, PLUS=7, MINUS=8, MULT=9, DIV=10, 
		SCOL=11, ASSIGN=12, OPAR=13, CPAR=14, OBRACE=15, CBRACE=16, TRUE=17, FALSE=18, 
		IF=19, ELSE=20, WHILE=21, LOG=22, BREAK=23, ID=24, INT=25, STRING=26, 
		COMMENT=27, SPACE=28;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", "MINUS", "MULT", "DIV", 
		"SCOL", "ASSIGN", "OPAR", "CPAR", "OBRACE", "CBRACE", "TRUE", "FALSE", 
		"IF", "ELSE", "WHILE", "LOG", "BREAK", "ID", "INT", "STRING", "COMMENT", 
		"SPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'=='", "'!='", "'>'", "'<'", "'>='", "'<='", "'+'", "'-'", "'*'", 
		"'/'", "';'", "'='", "'('", "')'", "'{'", "'}'", "'true'", "'false'", 
		"'if'", "'else'", "'while'", "'log'", "'break'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "EQ", "NEQ", "GT", "LT", "GTEQ", "LTEQ", "PLUS", "MINUS", "MULT", 
		"DIV", "SCOL", "ASSIGN", "OPAR", "CPAR", "OBRACE", "CBRACE", "TRUE", "FALSE", 
		"IF", "ELSE", "WHILE", "LOG", "BREAK", "ID", "INT", "STRING", "COMMENT", 
		"SPACE"
	};
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


	public ReducedMuLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ReducedMu.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\36\u00a6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\31\3\31\7\31\u0085\n\31\f\31\16\31\u0088\13"+
		"\31\3\32\6\32\u008b\n\32\r\32\16\32\u008c\3\33\3\33\3\33\3\33\7\33\u0093"+
		"\n\33\f\33\16\33\u0096\13\33\3\33\3\33\3\34\3\34\7\34\u009c\n\34\f\34"+
		"\16\34\u009f\13\34\3\34\3\34\3\35\3\35\3\35\3\35\2\2\36\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36\3\2\b\5\2C\\aac"+
		"|\6\2\62;C\\aac|\3\2\62;\5\2\f\f\17\17$$\4\2\f\f\17\17\5\2\13\f\17\17"+
		"\"\"\2\u00aa\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\3;\3\2\2\2\5>\3\2\2\2\7A\3\2\2\2\tC\3\2\2\2\13E\3\2\2\2\rH\3\2"+
		"\2\2\17K\3\2\2\2\21M\3\2\2\2\23O\3\2\2\2\25Q\3\2\2\2\27S\3\2\2\2\31U\3"+
		"\2\2\2\33W\3\2\2\2\35Y\3\2\2\2\37[\3\2\2\2!]\3\2\2\2#_\3\2\2\2%d\3\2\2"+
		"\2\'j\3\2\2\2)m\3\2\2\2+r\3\2\2\2-x\3\2\2\2/|\3\2\2\2\61\u0082\3\2\2\2"+
		"\63\u008a\3\2\2\2\65\u008e\3\2\2\2\67\u0099\3\2\2\29\u00a2\3\2\2\2;<\7"+
		"?\2\2<=\7?\2\2=\4\3\2\2\2>?\7#\2\2?@\7?\2\2@\6\3\2\2\2AB\7@\2\2B\b\3\2"+
		"\2\2CD\7>\2\2D\n\3\2\2\2EF\7@\2\2FG\7?\2\2G\f\3\2\2\2HI\7>\2\2IJ\7?\2"+
		"\2J\16\3\2\2\2KL\7-\2\2L\20\3\2\2\2MN\7/\2\2N\22\3\2\2\2OP\7,\2\2P\24"+
		"\3\2\2\2QR\7\61\2\2R\26\3\2\2\2ST\7=\2\2T\30\3\2\2\2UV\7?\2\2V\32\3\2"+
		"\2\2WX\7*\2\2X\34\3\2\2\2YZ\7+\2\2Z\36\3\2\2\2[\\\7}\2\2\\ \3\2\2\2]^"+
		"\7\177\2\2^\"\3\2\2\2_`\7v\2\2`a\7t\2\2ab\7w\2\2bc\7g\2\2c$\3\2\2\2de"+
		"\7h\2\2ef\7c\2\2fg\7n\2\2gh\7u\2\2hi\7g\2\2i&\3\2\2\2jk\7k\2\2kl\7h\2"+
		"\2l(\3\2\2\2mn\7g\2\2no\7n\2\2op\7u\2\2pq\7g\2\2q*\3\2\2\2rs\7y\2\2st"+
		"\7j\2\2tu\7k\2\2uv\7n\2\2vw\7g\2\2w,\3\2\2\2xy\7n\2\2yz\7q\2\2z{\7i\2"+
		"\2{.\3\2\2\2|}\7d\2\2}~\7t\2\2~\177\7g\2\2\177\u0080\7c\2\2\u0080\u0081"+
		"\7m\2\2\u0081\60\3\2\2\2\u0082\u0086\t\2\2\2\u0083\u0085\t\3\2\2\u0084"+
		"\u0083\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2"+
		"\2\2\u0087\62\3\2\2\2\u0088\u0086\3\2\2\2\u0089\u008b\t\4\2\2\u008a\u0089"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\64\3\2\2\2\u008e\u0094\7$\2\2\u008f\u0093\n\5\2\2\u0090\u0091\7$\2\2"+
		"\u0091\u0093\7$\2\2\u0092\u008f\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0096"+
		"\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0097\u0098\7$\2\2\u0098\66\3\2\2\2\u0099\u009d\7%\2\2"+
		"\u009a\u009c\n\6\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a1\b\34\2\2\u00a18\3\2\2\2\u00a2\u00a3\t\7\2\2\u00a3\u00a4\3\2\2\2"+
		"\u00a4\u00a5\b\35\2\2\u00a5:\3\2\2\2\b\2\u0086\u008c\u0092\u0094\u009d"+
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
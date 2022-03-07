// Generated from C:/Users/1/IdeaProjects/Language/src/main/antlr4\RedusedMu.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RedusedMuParser}.
 */
public interface RedusedMuListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(RedusedMuParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(RedusedMuParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(RedusedMuParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(RedusedMuParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(RedusedMuParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(RedusedMuParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(RedusedMuParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(RedusedMuParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(RedusedMuParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(RedusedMuParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void enterCondition_block(RedusedMuParser.Condition_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void exitCondition_block(RedusedMuParser.Condition_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void enterStat_block(RedusedMuParser.Stat_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void exitStat_block(RedusedMuParser.Stat_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(RedusedMuParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(RedusedMuParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link RedusedMuParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(RedusedMuParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link RedusedMuParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(RedusedMuParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(RedusedMuParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(RedusedMuParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(RedusedMuParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(RedusedMuParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpr(RedusedMuParser.MultiplicationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpr(RedusedMuParser.MultiplicationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(RedusedMuParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(RedusedMuParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(RedusedMuParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(RedusedMuParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(RedusedMuParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(RedusedMuParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPowExpr(RedusedMuParser.PowExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code powExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPowExpr(RedusedMuParser.PowExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(RedusedMuParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(RedusedMuParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(RedusedMuParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(RedusedMuParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(RedusedMuParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link RedusedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(RedusedMuParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(RedusedMuParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(RedusedMuParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberAtom(RedusedMuParser.NumberAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberAtom(RedusedMuParser.NumberAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(RedusedMuParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(RedusedMuParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(RedusedMuParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(RedusedMuParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringAtom(RedusedMuParser.StringAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link RedusedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringAtom(RedusedMuParser.StringAtomContext ctx);
}
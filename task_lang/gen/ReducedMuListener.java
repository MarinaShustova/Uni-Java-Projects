// Generated from C:/Users/1/IdeaProjects/task_lang/src/antlr\ReducedMu.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ReducedMuParser}.
 */
public interface ReducedMuListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(ReducedMuParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(ReducedMuParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ReducedMuParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ReducedMuParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(ReducedMuParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(ReducedMuParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ReducedMuParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ReducedMuParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void enterIf_stat(ReducedMuParser.If_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#if_stat}.
	 * @param ctx the parse tree
	 */
	void exitIf_stat(ReducedMuParser.If_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void enterCondition_block(ReducedMuParser.Condition_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#condition_block}.
	 * @param ctx the parse tree
	 */
	void exitCondition_block(ReducedMuParser.Condition_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void enterStat_block(ReducedMuParser.Stat_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#stat_block}.
	 * @param ctx the parse tree
	 */
	void exitStat_block(ReducedMuParser.Stat_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stat(ReducedMuParser.While_statContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#while_stat}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stat(ReducedMuParser.While_statContext ctx);
	/**
	 * Enter a parse tree produced by {@link ReducedMuParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(ReducedMuParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link ReducedMuParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(ReducedMuParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(ReducedMuParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(ReducedMuParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(ReducedMuParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(ReducedMuParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicationExpr(ReducedMuParser.MultiplicationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiplicationExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicationExpr(ReducedMuParser.MultiplicationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(ReducedMuParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(ReducedMuParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(ReducedMuParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(ReducedMuParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(ReducedMuParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code additiveExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(ReducedMuParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(ReducedMuParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationalExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(ReducedMuParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(ReducedMuParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(ReducedMuParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(ReducedMuParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpr}
	 * labeled alternative in {@link ReducedMuParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(ReducedMuParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumberAtom(ReducedMuParser.NumberAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumberAtom(ReducedMuParser.NumberAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBooleanAtom(ReducedMuParser.BooleanAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code booleanAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBooleanAtom(ReducedMuParser.BooleanAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(ReducedMuParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(ReducedMuParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterStringAtom(ReducedMuParser.StringAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAtom}
	 * labeled alternative in {@link ReducedMuParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitStringAtom(ReducedMuParser.StringAtomContext ctx);
}
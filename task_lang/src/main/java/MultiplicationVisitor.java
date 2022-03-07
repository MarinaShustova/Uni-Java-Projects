import domain.*;
import org.antlr.v4.runtime.misc.NotNull;

public class MultiplicationVisitor extends ReducedMuBaseVisitor<MultiplicationExpression> {
	private final ExpressionVisitor expressionVisitor;

	public MultiplicationVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public MultiplicationExpression visitMultiplicationExpr(@NotNull ReducedMuParser.MultiplicationExprContext ctx) {

		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
			case ReducedMuParser.MULT:
				return new MultExpression(left, right);
			case ReducedMuParser.DIV:
				return new DivisionExpression(left, right);
			default:
				throw new RuntimeException("unknown operator: " + ReducedMuParser.tokenNames[ctx.op.getType()]);
		}
	}
}

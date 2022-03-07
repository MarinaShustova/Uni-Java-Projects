import domain.*;
import org.antlr.v4.runtime.misc.NotNull;

public class AdditiveExpressionVisitor extends ReducedMuBaseVisitor<AdditiveExpression> {
	private final ExpressionVisitor expressionVisitor;

	public AdditiveExpressionVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public AdditiveExpression visitAdditiveExpr(@NotNull ReducedMuParser.AdditiveExprContext ctx) {

		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
			case ReducedMuParser.PLUS:
				return new AddExpression(left, right);
			case ReducedMuParser.MINUS:
				return new SubExpression(left, right);
			default:
				throw new RuntimeException("unknown operator: " + ReducedMuParser.tokenNames[ctx.op.getType()]);
		}
	}
}

import domain.Expression;

public class ParExprVisitor extends ReducedMuBaseVisitor<Expression> {
	private final ExpressionVisitor expressionVisitor;

	public ParExprVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}
	@Override
	public Expression visitParExpr(ReducedMuParser.ParExprContext ctx) {
		return expressionVisitor.visit(ctx.expr());
	}
}

import domain.AndExpression;
import domain.Expression;

public class AndVisitor extends ReducedMuBaseVisitor<AndExpression> {
	private final ExpressionVisitor expressionVisitor;

	public AndVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}
	@Override
	public AndExpression visitAndExpr(ReducedMuParser.AndExprContext ctx) {
		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));
		return new AndExpression(left, right);
	}
}

import domain.Expression;
import domain.OrExpression;

public class OrVisitor extends ReducedMuBaseVisitor<OrExpression> {
	private final ExpressionVisitor expressionVisitor;

	public OrVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}
	@Override
	public OrExpression visitOrExpr(ReducedMuParser.OrExprContext ctx) {
		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));
		return new OrExpression(left, right);
	}
}

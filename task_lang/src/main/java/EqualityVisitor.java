import domain.Expression;
import domain.RelationalExpression;
import type.CompareSign;

public class EqualityVisitor extends ReducedMuBaseVisitor<RelationalExpression> {
	private final ExpressionVisitor expressionVisitor;

	public EqualityVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public RelationalExpression visitEqualityExpr(ReducedMuParser.EqualityExprContext ctx) {

		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
			case ReducedMuParser.EQ:
				return new RelationalExpression(left, right, CompareSign.EQUAL);
			case ReducedMuParser.NEQ:
				return new RelationalExpression(left, right, CompareSign.NOT_EQUAL);
			default:
				throw new RuntimeException("unknown operator: " + ReducedMuParser.tokenNames[ctx.op.getType()]);
		}
	}
}

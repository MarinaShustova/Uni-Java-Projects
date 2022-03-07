import domain.Expression;
import domain.RelationalExpression;
import domain.Value;
import org.antlr.v4.runtime.misc.NotNull;
import type.CompareSign;

public class RelationalExpressionVisitor extends ReducedMuBaseVisitor<RelationalExpression> {
	private final ExpressionVisitor expressionVisitor;

	public RelationalExpressionVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public RelationalExpression visitRelationalExpr(ReducedMuParser.RelationalExprContext ctx) {

		Expression left = expressionVisitor.visit(ctx.expr(0));
		Expression right = expressionVisitor.visit(ctx.expr(1));

		switch (ctx.op.getType()) {
			case ReducedMuParser.LT:
				return new RelationalExpression(left, right, CompareSign.LESS);
			case ReducedMuParser.LTEQ:
				return new RelationalExpression(left, right, CompareSign.LESS_OR_EQUAL);
			case ReducedMuParser.GT:
				return new RelationalExpression(left, right, CompareSign.GREATER);
			case ReducedMuParser.GTEQ:
				return new RelationalExpression(left, right, CompareSign.GRATER_OR_EQAL);
			default:
				throw new RuntimeException("unknown operator: " + ReducedMuParser.tokenNames[ctx.op.getType()]);
		}
	}
}

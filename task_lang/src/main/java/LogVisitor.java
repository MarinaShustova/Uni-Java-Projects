import domain.Expression;
import domain.LogStatement;

public class LogVisitor extends ReducedMuBaseVisitor<LogStatement> {
	private final ExpressionVisitor expressionVisitor;

	public LogVisitor(ExpressionVisitor expressionVisitor) {
		this.expressionVisitor = expressionVisitor;
	}

	@Override
	public LogStatement visitLog(ReducedMuParser.LogContext ctx) {
		ReducedMuParser.ExprContext expressionCtx = ctx.expr();
		Expression expression = expressionCtx.accept(expressionVisitor);
		return new LogStatement(expression);
	}
}

import domain.*;

public class WhileVisitor extends ReducedMuBaseVisitor<WhileStatement> {
	private final Scope scope;
	private final ExpressionVisitor expressionVisitor;

	public WhileVisitor(Scope scope) {
		this.scope = scope;
		expressionVisitor = new ExpressionVisitor(this.scope);
	}

	@Override
	public WhileStatement visitWhile_stat(ReducedMuParser.While_statContext ctx) {
		ReducedMuParser.ExprContext context = ctx.expr();
		StatementVisitor statementVisitor = new StatementVisitor(scope);
		Expression condition = context.accept(expressionVisitor);
		Stat stat = ctx.block().accept(statementVisitor);
		return new WhileStatement(condition, stat);
	}
}

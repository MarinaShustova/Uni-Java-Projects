import domain.*;

public class AssignmentVisitor extends ReducedMuBaseVisitor<Assignment> {
	private final ExpressionVisitor expressionVisitor;
	private final Scope scope;

	public AssignmentVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
		this.expressionVisitor = expressionVisitor;
		this.scope = scope;
	}

	@Override
	public Assignment visitAssignment(ReducedMuParser.AssignmentContext ctx) {
		String id = ctx.ID().getText();
		Expression value = expressionVisitor.visit(ctx.expr());
		scope.addLocalVariable(new Variable(id, value.getType()));
		return new Assignment(id, value);
	}
}

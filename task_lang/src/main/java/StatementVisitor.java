import domain.BreakStatement;
import domain.Scope;
import domain.Stat;
import domain.Value;

public class StatementVisitor extends ReducedMuBaseVisitor<Stat> {
	private final ExpressionVisitor expressionVisitor;
	private final LogVisitor logVisitor;
	private final IfVisitor ifVisitor;
	private final WhileVisitor whileVisitor;
	private final AssignmentVisitor assignmentVisitor;
	private final BlockVisitor blockVisitor;
	private final BreakVisitor breakVisitor;

	public StatementVisitor(Scope scope) {
		expressionVisitor = new ExpressionVisitor(scope);
		logVisitor = new LogVisitor(expressionVisitor);
		ifVisitor = new IfVisitor(this,expressionVisitor);
		whileVisitor = new WhileVisitor(scope);
		assignmentVisitor = new AssignmentVisitor(expressionVisitor, scope);
		blockVisitor = new BlockVisitor(scope);
		breakVisitor = new BreakVisitor(this, expressionVisitor);
	}

	@Override
	public Stat visitLog(ReducedMuParser.LogContext ctx) {
		return logVisitor.visitLog(ctx);
	}

	@Override
	public Stat visitIf_stat(ReducedMuParser.If_statContext ctx) {
		return ifVisitor.visitIf_stat(ctx);
	}

	@Override
	public Stat visitWhile_stat(ReducedMuParser.While_statContext ctx) {
		return whileVisitor.visitWhile_stat(ctx);
	}

	@Override
	public Stat visitAssignment(ReducedMuParser.AssignmentContext ctx) {
		return assignmentVisitor.visitAssignment(ctx);
	}

	@Override
	public Stat visitBlock(ReducedMuParser.BlockContext ctx) {
		return blockVisitor.visitBlock(ctx);
	}

	@Override
	public Stat visitBreak2(ReducedMuParser.Break2Context ctx) {
		return breakVisitor.visitBreak2(ctx);
	}
}

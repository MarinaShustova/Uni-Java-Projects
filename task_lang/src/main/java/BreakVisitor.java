import domain.BreakStatement;
import domain.Expression;
import domain.IfStatement;
import domain.Stat;

public class BreakVisitor extends ReducedMuBaseVisitor<BreakStatement>{
	private final StatementVisitor statementVisitor;
	private final ExpressionVisitor expressionVisitor;

	public BreakVisitor(StatementVisitor statementVisitor, ExpressionVisitor expressionVisitor) {
		this.statementVisitor = statementVisitor;
		this.expressionVisitor = expressionVisitor;
	}
	@Override
	public BreakStatement visitBreak2(ReducedMuParser.Break2Context ctx) {
		return new BreakStatement();
	}
}

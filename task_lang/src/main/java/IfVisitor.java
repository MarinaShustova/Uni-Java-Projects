import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import domain.Expression;
import domain.IfStatement;
import domain.Stat;
import domain.Value;

import java.util.List;

public class IfVisitor extends ReducedMuBaseVisitor<IfStatement> {
	private final StatementVisitor statementVisitor;
	private final ExpressionVisitor expressionVisitor;

	public IfVisitor(StatementVisitor statementVisitor, ExpressionVisitor expressionVisitor) {
		this.statementVisitor = statementVisitor;
		this.expressionVisitor = expressionVisitor;
	}
	@Override
	public IfStatement visitIf_stat(ReducedMuParser.If_statContext ctx) {
		ReducedMuParser.ExprContext conditionalExpressionContext = ctx.expr();
		Expression condition = conditionalExpressionContext.accept(expressionVisitor);
		Stat trueStatement = ctx.trueStatement.accept(statementVisitor);
		if (ctx.falseStatement != null) {
			Stat falseStatement = ctx.falseStatement.accept(statementVisitor);
			return new IfStatement(condition, trueStatement, falseStatement);
		}
		return new IfStatement(condition, trueStatement);
	}
}

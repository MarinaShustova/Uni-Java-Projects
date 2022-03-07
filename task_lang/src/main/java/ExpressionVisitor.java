import domain.Expression;
import domain.Scope;
import domain.Stat;

public class ExpressionVisitor extends ReducedMuBaseVisitor<Expression> {
	private final AdditiveExpressionVisitor additiveExpressionVisitor;
	private final EqualityVisitor equalityVisitor;
	private final MultiplicationVisitor multiplicationVisitor;
	private final RelationalExpressionVisitor relationalExpressionVisitor;
	private final BooleanAtomVisitor booleanAtomVisitor;
	private final NumberAtomVisitor numberAtomVisitor;
	private final StringAtomVisitor stringAtomVisitor;
	private final ParExprVisitor parExprVisitor;
	private final VariableReferenceVisitor variableReferenceVisitor;

	public ExpressionVisitor(Scope scope) {
		additiveExpressionVisitor = new AdditiveExpressionVisitor(this);
		equalityVisitor = new EqualityVisitor(this);
		multiplicationVisitor = new MultiplicationVisitor(this);
		relationalExpressionVisitor = new RelationalExpressionVisitor(this);
		booleanAtomVisitor = new BooleanAtomVisitor();
		numberAtomVisitor = new NumberAtomVisitor();
		stringAtomVisitor = new StringAtomVisitor();
		parExprVisitor = new ParExprVisitor(this);
		variableReferenceVisitor = new VariableReferenceVisitor(scope);
	}

	@Override
	public Expression visitMultiplicationExpr(ReducedMuParser.MultiplicationExprContext ctx) {
		return multiplicationVisitor.visitMultiplicationExpr(ctx);
	}

	@Override
	public Expression visitAdditiveExpr(ReducedMuParser.AdditiveExprContext ctx) {
		return additiveExpressionVisitor.visitAdditiveExpr(ctx);
	}

	@Override
	public Expression visitRelationalExpr(ReducedMuParser.RelationalExprContext ctx) {
		return relationalExpressionVisitor.visitRelationalExpr(ctx);
	}

	@Override
	public Expression visitEqualityExpr(ReducedMuParser.EqualityExprContext ctx) {
		return equalityVisitor.visitEqualityExpr(ctx);
	}

	@Override
	public Expression visitNumberAtom(ReducedMuParser.NumberAtomContext ctx) {
		return numberAtomVisitor.visitNumberAtom(ctx);
	}

	@Override
	public Expression visitBooleanAtom(ReducedMuParser.BooleanAtomContext ctx) {
		return booleanAtomVisitor.visitBooleanAtom(ctx);
	}

	@Override
	public Expression visitStringAtom(ReducedMuParser.StringAtomContext ctx) {
		return stringAtomVisitor.visitStringAtom(ctx);
	}

	@Override
	public Expression visitParExpr(ReducedMuParser.ParExprContext ctx) {
		return parExprVisitor.visitParExpr(ctx);
	}

	@Override
	public Expression visitVarRef(ReducedMuParser.VarRefContext ctx) {
		return variableReferenceVisitor.visitVarRef(ctx);
	}
}

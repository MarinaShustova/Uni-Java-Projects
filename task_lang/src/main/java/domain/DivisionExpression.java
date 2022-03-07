package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;

public class DivisionExpression extends MultiplicationExpression {
	public DivisionExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	@Override
	public void accept(ExpressionGenerator generator) {
		generator.generate(this);
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

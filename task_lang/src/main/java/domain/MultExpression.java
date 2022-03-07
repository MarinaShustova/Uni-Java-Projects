package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;

public class MultExpression extends MultiplicationExpression {
	public MultExpression(Expression leftExpression, Expression rightExpression) {
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

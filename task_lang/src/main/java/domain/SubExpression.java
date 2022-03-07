package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;

public class SubExpression extends AdditiveExpression {
	public SubExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	@Override
	public void accept(ExpressionGenerator genrator) {
		genrator.generate(this);
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

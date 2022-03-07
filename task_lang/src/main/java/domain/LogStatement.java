package domain;

import generators.StatGenerator;

public class LogStatement implements Stat {
	private final Expression expression;

	public LogStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

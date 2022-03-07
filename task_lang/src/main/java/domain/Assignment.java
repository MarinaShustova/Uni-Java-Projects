package domain;

import generators.StatGenerator;

public class Assignment implements Stat {
	private final String varName;
	private final Expression expression;

	public Assignment(String varName, Expression expression) {
		this.varName = varName;
		this.expression = expression;
	}

	public String getVarName() {
		return varName;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;
import type.Type;

public class VariableReference implements Expression {

	private final Variable variable;

	public VariableReference(Variable variable) {
		this.variable = variable;
	}

	public String getName() {
		return variable.getName();
	}

	@Override
	public Type getType() {
		return variable.getType();
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

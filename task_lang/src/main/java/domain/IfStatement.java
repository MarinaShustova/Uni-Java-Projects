package domain;

import generators.StatGenerator;

import java.util.Optional;

public class IfStatement implements Stat {
	private final Expression condition;
	private final Stat trueStatement;
	private final Optional<Stat> falseStatement;

	public IfStatement(Expression condition, Stat trueStatement, Stat falseStatement) {
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = Optional.ofNullable(falseStatement);
	}

	public IfStatement(Expression condition, Stat trueStatement) {
		this.condition = condition;
		this.trueStatement = trueStatement;
		this.falseStatement = Optional.empty();
	}

	public Expression getCondition() {
		return condition;
	}

	public Stat getTrueStatement() {
		return trueStatement;
	}

	public Optional<Stat> getFalseStatement() {
		return falseStatement;
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

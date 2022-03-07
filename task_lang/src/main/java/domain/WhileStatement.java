package domain;

import generators.StatGenerator;

public class WhileStatement implements Stat {
	private final Expression expression;
	private final Stat block;
//	private final WhileBlock whileBlock;

	public WhileStatement(Expression expression, Stat block) {
		this.block = block;
		this.expression = expression;
	}

	public Stat getStatementBlock() {
		return block;
	}

	public Expression getExpression() {
		return expression;
	}
	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

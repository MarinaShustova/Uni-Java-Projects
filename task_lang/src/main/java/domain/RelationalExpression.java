package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;
import type.BuiltInType;
import type.CompareSign;
import type.Type;

public class RelationalExpression implements Expression {
	private final CompareSign compareSign;
	private final Expression leftExpression;
	private final Expression rightExpression;
	private final Type type;
	private final boolean isPrimitiveComparison;

	public RelationalExpression(Expression leftExpression, Expression rightExpression, CompareSign compareSign) {
		this.type = BuiltInType.BOOLEAN;
		this.compareSign = compareSign;
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
		boolean leftExpressionIsPrimitive = leftExpression.getType().getTypeClass().isPrimitive();
		boolean rightExpressionIsPrimitive = rightExpression.getType().getTypeClass().isPrimitive();
		isPrimitiveComparison = leftExpressionIsPrimitive && rightExpressionIsPrimitive;
		boolean isObjectsComparison = !leftExpressionIsPrimitive && !rightExpressionIsPrimitive;
		boolean isMixedComparison = !isPrimitiveComparison && !isObjectsComparison;
		if (isMixedComparison) {
			throw new RuntimeException("Mixed comparison is not allowed");
		}
	}

	public CompareSign getCompareSign() {
		return compareSign;
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public boolean isPrimitiveComparison() {
		return isPrimitiveComparison;
	}

	@Override
	public void accept(ExpressionGenerator generator) {
		generator.generate(this);
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}

	@Override
	public Type getType() {
		return type;
	}
}

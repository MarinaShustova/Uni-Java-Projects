//package domain;
//
//import generators.ExpressionGenerator;
//import generators.StatGenerator;
//import type.BuiltInType;
//import type.Type;
//
//public class AndExpression implements Expression {
//	private final Expression leftExpression;
//	private final Expression rightExpression;
//	private final Type type;
//
//	public AndExpression(Expression leftExpression, Expression rightExpression) {
//		this.type = getCommonType(leftExpression,rightExpression);
//		this.leftExpression = leftExpression;
//		this.rightExpression = rightExpression;
//	}
//
//	private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
//		if(rightExpression.getType() == BuiltInType.STRING) return BuiltInType.STRING;
//		return leftExpression.getType();
//	}
//	@Override
//	public Type getType() {
//		return type;
//	}
//
//	@Override
//	public void accept(ExpressionGenerator genrator) {
//
//	}
//
//	@Override
//	public void accept(StatGenerator generator) {
//
//	}
//	public Expression getLeftExpression() {
//		return leftExpression;
//	}
//
//	public Expression getRightExpression() {
//		return rightExpression;
//	}
//}

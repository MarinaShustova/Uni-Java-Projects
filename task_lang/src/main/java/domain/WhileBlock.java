//package domain;
//
//import generators.StatGenerator;
//import generators.WhileGenerator;
//
//import java.util.Collections;
//import java.util.List;
//
//public class WhileBlock {
//	private final Expression condition;
//	private final Stat trueStatement;
//	private final List<Stat> statements;
//	private final Scope scope;
//
//
//	public WhileBlock(Expression condition, Stat trueStatement,Scope scope, List<Stat> statements) {
//		this.condition = condition;
//		this.trueStatement = trueStatement;
//		this.scope = scope;
//		this.statements = statements;
//	}
//
//	public Expression getCondition() {
//		return condition;
//	}
//
//	public Stat getTrueStatement() {
//		return trueStatement;
//	}
//
//	public void accept(WhileGenerator generator) {
////		generator.generate(this);
//	}
//	public Scope getScope() {
//		return scope;
//	}
//
//	public List<Stat> getStatements() {
//		return Collections.unmodifiableList(statements);
//	}
//}

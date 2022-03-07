package generators;

import domain.*;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class StatGenerator {

	private final LogGenerator logGenerator;
	private final IfGenerator ifGenerator;
	private final BlockGenerator blockGenerator;
	private final WhileGenerator whileGenerator;
	private final AssignmentGenerator assignmentGenerator;
	private final ExpressionGenerator expressionGenerator;
	private final BreakGenerator breakGenerator;
	private final MethodVisitor methodVisitor;

	public StatGenerator(MethodVisitor methodVisitor, Scope scope) {
		expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
		logGenerator = new LogGenerator(expressionGenerator,methodVisitor);
		whileGenerator = new WhileGenerator(methodVisitor, expressionGenerator,this);
		blockGenerator = new BlockGenerator(methodVisitor);
		ifGenerator = new IfGenerator(this, expressionGenerator, methodVisitor);
		assignmentGenerator = new AssignmentGenerator(methodVisitor, expressionGenerator,scope);
		breakGenerator = new BreakGenerator(methodVisitor, scope);
		this.methodVisitor = methodVisitor;
	}

	public void generate(LogStatement printStatement) {
		logGenerator.generate(printStatement);
	}

	public void generate(IfStatement ifStatement) {
		ifGenerator.generate(ifStatement);
	}

	public void generate(Block block) {
		blockGenerator.generate(block);
	}

	public void generate(WhileStatement whileStatement) {
		((Block)whileStatement.getStatementBlock()).getScope().endLabel = new Label();
		whileGenerator.generate(whileStatement);
		methodVisitor.visitLabel(((Block)whileStatement.getStatementBlock()).getScope().endLabel);
	}

	public void generate(Assignment assignment) {
		assignmentGenerator.generate(assignment);
	}

	public void generate(Value value) {
		expressionGenerator.generate(value);
	}
	public void generate(AdditiveExpression expression) {
		expressionGenerator.generate(expression);
	}

	public void generate(MultiplicationExpression expression) {
		expressionGenerator.generate(expression);
	}

	public void generate(RelationalExpression expression) {
		expressionGenerator.generate(expression);
	}

	public void generate(VariableReference expression) {
		expressionGenerator.generate(expression);
	}

	public void generate(BreakStatement breakStatement) {
		breakGenerator.generate(breakStatement);
	}
}
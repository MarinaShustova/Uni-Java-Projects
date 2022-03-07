package generators;

import domain.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MultiplicationGenerator {
	private final MethodVisitor methodVisitor;
	private final ExpressionGenerator expressionGenerator;

	public MultiplicationGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator) {
		this.methodVisitor = methodVisitor;
		this.expressionGenerator = expressionGenerator;
	}

	public void generate(MultiplicationExpression multiplicationExpression) {
		Expression leftExpression = multiplicationExpression.getLeftExpression();
		Expression rightExpression = multiplicationExpression.getRightExpression();
		leftExpression.accept(expressionGenerator);
		rightExpression.accept(expressionGenerator);
		if (multiplicationExpression instanceof MultExpression) {
			methodVisitor.visitInsn(Opcodes.IMUL);
		}
		else if (multiplicationExpression instanceof DivisionExpression) {
			methodVisitor.visitInsn(Opcodes.IDIV);
		}
	}
}

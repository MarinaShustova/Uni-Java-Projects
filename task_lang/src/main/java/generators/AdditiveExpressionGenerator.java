package generators;

import domain.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.Type;

public class AdditiveExpressionGenerator {
	private final MethodVisitor methodVisitor;
	private final ExpressionGenerator expressionGenerator;

	public AdditiveExpressionGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator) {
		this.methodVisitor = methodVisitor;
		this.expressionGenerator = expressionGenerator;
	}

	public void generate(AdditiveExpression additiveExpression) {
		Expression leftExpression = additiveExpression.getLeftExpression();
		Expression rightExpression = additiveExpression.getRightExpression();
		leftExpression.accept(expressionGenerator);
		rightExpression.accept(expressionGenerator);
		if (additiveExpression instanceof AddExpression) {
			methodVisitor.visitInsn(Opcodes.IADD);
		}
		else if (additiveExpression instanceof SubExpression) {
			methodVisitor.visitInsn(Opcodes.ISUB);
		}
	}
}

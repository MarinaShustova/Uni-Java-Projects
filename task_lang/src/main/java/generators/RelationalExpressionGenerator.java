package generators;

import domain.Expression;
import domain.RelationalExpression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.CompareSign;

public class RelationalExpressionGenerator {
	private final ExpressionGenerator expressionGenerator;
	private final MethodVisitor methodVisitor;

	public RelationalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
		this.expressionGenerator = expressionGenerator;
		this.methodVisitor = methodVisitor;
	}

	public void generate(RelationalExpression conditionalExpression) {
		Expression leftExpression = conditionalExpression.getLeftExpression();
		Expression rightExpression = conditionalExpression.getRightExpression();
		CompareSign compareSign = conditionalExpression.getCompareSign();
		leftExpression.accept(expressionGenerator);
		rightExpression.accept(expressionGenerator);
		methodVisitor.visitInsn(Opcodes.ISUB);
		Label endLabel = new Label();
		Label trueLabel = new Label();
		methodVisitor.visitJumpInsn(compareSign.getOpcode(), trueLabel);
		methodVisitor.visitInsn(Opcodes.ICONST_0);
		methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
		methodVisitor.visitLabel(trueLabel);
		methodVisitor.visitInsn(Opcodes.ICONST_1);
		methodVisitor.visitLabel(endLabel);
	}
}

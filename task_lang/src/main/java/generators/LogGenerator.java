package generators;

import domain.Expression;
import domain.LogStatement;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.Type;


public class LogGenerator {
	private final MethodVisitor methodVisitor;
	private final ExpressionGenerator expressionGenerator;

	public LogGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
		this.methodVisitor = methodVisitor;
		this.expressionGenerator = expressionGenerator;
	}

	public void generate(LogStatement logStatement) {
		methodVisitor.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
		Expression expression = logStatement.getExpression();
		expression.accept(expressionGenerator);
		Type type = expression.getType();
		String descriptor = "(" + type.getDescriptor() + ")V";
		methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
	}
}

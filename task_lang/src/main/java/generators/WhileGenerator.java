package generators;

import domain.Block;
import domain.Expression;
import domain.Stat;
import domain.WhileStatement;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;


public class WhileGenerator {
	private final MethodVisitor methodVisitor;
	private final ExpressionGenerator expressionGenerator;
	private final StatGenerator statGenerator;

	public WhileGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator,
						  StatGenerator statGenerator) {
		this.expressionGenerator = expressionGenerator;
		this.methodVisitor = methodVisitor;
		this.statGenerator = statGenerator;
	}

	public void generate(WhileStatement whileStatement) {
		Label conditionLabel = new Label();
		Label nextLabel = new Label();
		methodVisitor.visitLabel(conditionLabel);
		Expression expression = whileStatement.getExpression();
		expression.accept(expressionGenerator);
		methodVisitor.visitInsn(ICONST_0);
		methodVisitor.visitJumpInsn(IF_ICMPEQ, nextLabel);
		Stat stat = whileStatement.getStatementBlock();
		stat.accept(statGenerator);
		methodVisitor.visitJumpInsn(GOTO, conditionLabel);
		methodVisitor.visitLabel(nextLabel);
	}
}

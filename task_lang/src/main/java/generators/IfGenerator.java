package generators;

import domain.Expression;
import domain.IfStatement;
import domain.Stat;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;

public class IfGenerator {
	private final StatGenerator statementGenerator;
	private final ExpressionGenerator expressionGenerator;
	private final MethodVisitor methodVisitor;

	public IfGenerator(StatGenerator statementGenerator, ExpressionGenerator expressionGenerator,
					   MethodVisitor methodVisitor) {
		this.statementGenerator = statementGenerator;
		this.expressionGenerator = expressionGenerator;
		this.methodVisitor = methodVisitor;
	}

	public void generate(IfStatement ifStatement) {
		Expression condition = ifStatement.getCondition();
		condition.accept(expressionGenerator);
		Label trueLabel = new Label();
		Label endLabel = new Label();
		methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel);
		Optional<Stat> falseStatement = ifStatement.getFalseStatement();
		falseStatement.ifPresent(stat -> stat.accept(statementGenerator));
		methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
		methodVisitor.visitLabel(trueLabel);
		ifStatement.getTrueStatement().accept(statementGenerator);
		methodVisitor.visitLabel(endLabel);
	}
}

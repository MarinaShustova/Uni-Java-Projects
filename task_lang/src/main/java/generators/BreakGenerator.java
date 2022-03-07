package generators;

import domain.Block;
import domain.BreakStatement;
import domain.Scope;
import domain.Stat;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class BreakGenerator {
	private final MethodVisitor methodVisitor;
	private final Scope scope;

	public BreakGenerator(MethodVisitor methodVisitor, Scope scope) {
		this.methodVisitor = methodVisitor;
		this.scope = scope;
	}
	public void generate(BreakStatement breakStatement) {
		methodVisitor.visitJumpInsn(Opcodes.GOTO, scope.endLabel);
	}
}

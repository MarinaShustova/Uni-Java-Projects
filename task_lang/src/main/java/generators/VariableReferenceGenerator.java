package generators;

import domain.Scope;
import domain.VariableReference;
import org.objectweb.asm.MethodVisitor;
import type.Type;

public class VariableReferenceGenerator {
	private final MethodVisitor methodVisitor;
	private final Scope scope;

	public VariableReferenceGenerator(MethodVisitor methodVisitor, Scope scope) {
		this.methodVisitor = methodVisitor;
		this.scope = scope;
	}

	public void generate(VariableReference localVariableReference) {
		String varName = localVariableReference.getName();
		int index = scope.getLocalVariableIndex(varName);
		Type type = localVariableReference.getType();
		methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
	}
}

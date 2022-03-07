package generators;

import domain.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import type.Type;

public class AssignmentGenerator {
	private final MethodVisitor methodVisitor;
	private final ExpressionGenerator expressionGenerator;
	private final Scope scope;

	public AssignmentGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator, Scope scope) {
		this.methodVisitor = methodVisitor;
		this.expressionGenerator = expressionGenerator;
		this.scope = scope;
	}

	public void generate(Assignment assignment) {
		String varName = assignment.getVarName();
		Expression expression = assignment.getExpression();
		Type type = expression.getType();
		if(scope.isLocalVariableExists(varName)) {
			expression.accept(expressionGenerator);
			int index = scope.getLocalVariableIndex(varName);
//			Variable variable = scope.getLocalVariable(varName);
//			Type localVariableType = variable.getType();
			methodVisitor.visitVarInsn(type.getStoreVariableOpcode(), index);
		}
	}
}

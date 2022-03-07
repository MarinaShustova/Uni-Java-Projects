package generators;

import com.sun.org.apache.xerces.internal.impl.xs.models.XSCMBinOp;
import domain.*;
import org.objectweb.asm.MethodVisitor;

public class ExpressionGenerator {

	private final AdditiveExpressionGenerator additiveExpressionGenerator;
	private final MultiplicationGenerator multiplicationGenerator;
	private final RelationalExpressionGenerator relationalExpressionGenerator;
	private final ValueGenerator valueGenerator;
	private final VariableReferenceGenerator variableReferenceGenerator;

	public ExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
		additiveExpressionGenerator = new AdditiveExpressionGenerator(methodVisitor, this);
		multiplicationGenerator = new MultiplicationGenerator(methodVisitor, this);
		relationalExpressionGenerator = new RelationalExpressionGenerator(this, methodVisitor);
		valueGenerator = new ValueGenerator(methodVisitor);
		variableReferenceGenerator = new VariableReferenceGenerator(methodVisitor, scope);
	}


	public void generate(Value value) {
		valueGenerator.generate(value);
	}

	public void generate(AdditiveExpression expression) {
		additiveExpressionGenerator.generate(expression);
	}

	public void generate(MultiplicationExpression expression) {
		multiplicationGenerator.generate(expression);
	}

	public void generate(RelationalExpression expression) {
		relationalExpressionGenerator.generate(expression);
	}
	public void generate(VariableReference expression) {
		variableReferenceGenerator.generate(expression);
	}
}

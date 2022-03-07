package generators;

import domain.Value;
import org.objectweb.asm.MethodVisitor;
import type.BuiltInType;
import type.Type;

public class ValueGenerator {
	private final MethodVisitor methodVisitor;

	public ValueGenerator(MethodVisitor methodVisitor) {
		this.methodVisitor = methodVisitor;
	}

	public void generate(Value value) {
		Type type = value.getType();
		String stringValue = value.getValue();
		Object transformedValue = getValueFromString(stringValue, type);
		methodVisitor.visitLdcInsn(transformedValue);
	}

	public Object getValueFromString(String stringValue, Type type) {
		if (type == BuiltInType.INT) {
			return Integer.valueOf(stringValue);
		}
		if (type == BuiltInType.BOOLEAN) {
			return Boolean.valueOf(stringValue);
		}
		if (type == BuiltInType.STRING) {
			return stringValue.substring(1,stringValue.length()-1);
		}
		throw new AssertionError("Objects not yet implemented!");
	}
}

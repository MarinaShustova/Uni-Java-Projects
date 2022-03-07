import domain.Value;
import type.BuiltInType;
import type.Type;

public class NumberAtomVisitor extends ReducedMuBaseVisitor<Value> {
	@Override
	public Value visitNumberAtom(ReducedMuParser.NumberAtomContext ctx) {
		String value = ctx.getText();
		Type type = BuiltInType.INT;
		return new Value(value, type);
	}
}

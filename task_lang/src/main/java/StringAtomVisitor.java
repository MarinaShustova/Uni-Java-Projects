import domain.Value;
import type.BuiltInType;

public class StringAtomVisitor extends ReducedMuBaseVisitor<Value> {
	@Override
	public Value visitStringAtom(ReducedMuParser.StringAtomContext ctx) {
		String str = ctx.getText();
		return new Value(str, BuiltInType.STRING);
	}
}

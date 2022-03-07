import domain.Scope;
import domain.Value;
import org.antlr.v4.runtime.misc.NotNull;
import type.BuiltInType;
import type.Type;

public class BooleanAtomVisitor extends ReducedMuBaseVisitor<Value> {
	@Override
	public Value visitBooleanAtom(ReducedMuParser.BooleanAtomContext ctx) {
		String value = ctx.getText();
		Type type = BuiltInType.BOOLEAN;
		return new Value(value, type);
//		return new Value(Boolean.valueOf(ctx.getText()));
	}
}

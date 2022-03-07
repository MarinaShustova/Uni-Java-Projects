import domain.Scope;
import domain.Variable;
import domain.VariableReference;
import org.antlr.v4.runtime.misc.NotNull;

public class VariableReferenceVisitor extends ReducedMuBaseVisitor<VariableReference> {
	private final Scope scope;

	public VariableReferenceVisitor(Scope scope) {
		this.scope = scope;
	}

	@Override
	public VariableReference visitVarRef(@NotNull ReducedMuParser.VarRefContext ctx) {
		String varName = ctx.getText();
		Variable variable = scope.getLocalVariable(varName);
		return new VariableReference(variable);
	}
}

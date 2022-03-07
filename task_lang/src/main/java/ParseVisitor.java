import domain.Block;
import domain.Parse;
import domain.Scope;
import org.antlr.v4.runtime.misc.NotNull;

public class ParseVisitor extends ReducedMuBaseVisitor<Parse> {
	@Override
	public Parse visitParse(@NotNull ReducedMuParser.ParseContext ctx) {
		Scope scope = new Scope();
		BlockVisitor blockVisitor = new BlockVisitor(scope);
		ReducedMuParser.BlockContext blockContext = ctx.block();
		Block block = blockContext.accept(blockVisitor);
		return new Parse(block);
	}
}

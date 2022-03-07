import domain.Block;
import domain.Scope;
import domain.Stat;

import java.util.List;
import java.util.stream.Collectors;

public class BlockVisitor extends ReducedMuBaseVisitor<Block> {
	private Scope scope;

	public BlockVisitor(Scope scope) {
		this.scope = scope;
	}

	@Override
	public Block visitBlock(ReducedMuParser.BlockContext ctx) {
		List<ReducedMuParser.StatContext> blockStatementsCtx = ctx.stat();
		Scope newScope = new Scope(scope);
		StatementVisitor statementVisitor = new StatementVisitor(newScope);
		List<Stat> statements = blockStatementsCtx.stream()
				.map(smtt -> smtt.accept(statementVisitor)).collect(Collectors.toList());
		return new Block(newScope, statements);
	}
}

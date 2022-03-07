package generators;

import domain.Block;
import domain.Scope;
import domain.Stat;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.List;

public class BlockGenerator {
	private final MethodVisitor methodVisitor;
//	private final Scope scope;

	public BlockGenerator(MethodVisitor methodVisitor) {
		this.methodVisitor = methodVisitor;
//		this.scope = scope;
	}

	public void generate(Block block) {
		Scope newScope = block.getScope();
		List<Stat> statements = block.getStatements();
		StatGenerator statementGenerator = new StatGenerator(methodVisitor, newScope);
//		newScope.endLabel = new Label();
		statements.stream().forEach(stmt -> stmt.accept(statementGenerator));
	}
}

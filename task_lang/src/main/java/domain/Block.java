package domain;

import generators.StatGenerator;

import java.util.Collections;
import java.util.List;

public class Block implements Stat {
	private final List<Stat> statements;
	private final Scope scope;

	public Block(Scope scope, List<Stat> statements) {
		this.scope = scope;
		this.statements = statements;
	}

	public static Block empty(Scope scope) {
		return new Block(scope, Collections.emptyList());
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}

	public Scope getScope() {
		return scope;
	}

	public List<Stat> getStatements() {
		return Collections.unmodifiableList(statements);
	}
}

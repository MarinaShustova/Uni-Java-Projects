package domain;

import type.Type;

public class Variable {
	private final String name;
	private final Type type;

	public Variable(String name, Type type) {
		this.type = type;
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}

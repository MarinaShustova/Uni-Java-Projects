package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;
import type.Type;

public class Value implements Expression {
	private String value;
	private Type type;

	public Value(String value, Type type) {
		this.type = type;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	@Override
	public void accept(ExpressionGenerator genrator) {
		genrator.generate(this);
	}

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {

		if (value == null) {
			return 0;
		}

		return this.value.hashCode();
	}

	@Override
	public boolean equals(Object o) {

		if (value == o) {
			return true;
		}

		if (value == null || o == null || o.getClass() != value.getClass()) {
			return false;
		}

		Value that = (Value) o;

		return this.value.equals(that.value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
package domain;

import generators.ExpressionGenerator;
import generators.StatGenerator;
import type.Type;

public interface Expression{
	Type getType();
	void accept(ExpressionGenerator genrator);
	void accept(StatGenerator generator);
}
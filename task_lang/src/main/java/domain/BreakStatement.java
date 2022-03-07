package domain;

import generators.StatGenerator;

public class BreakStatement implements Stat{

	@Override
	public void accept(StatGenerator generator) {
		generator.generate(this);
	}
}

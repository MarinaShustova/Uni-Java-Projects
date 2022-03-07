package domain;

import generators.StatGenerator;

@FunctionalInterface
public interface Stat {
	void accept(StatGenerator generator);
}

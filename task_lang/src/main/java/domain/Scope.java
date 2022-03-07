package domain;

import java.awt.*;
import java.util.*;
import org.apache.commons.collections4.map.LinkedMap;
import org.objectweb.asm.Label;

public class Scope {
	private LinkedMap<String, Variable> localVariables = new LinkedMap<>();
	public Label endLabel;

	public Scope() {

	}

	public Scope(Scope scope) {
		localVariables = new LinkedMap<>(scope.localVariables);
		endLabel = scope.endLabel;
	}

	public void addLocalVariable(Variable variable) {
		localVariables.put(variable.getName(),variable);
	}

	public Variable getLocalVariable(String varName) {
		return Optional.ofNullable(localVariables.get(varName))
				.orElseThrow(() -> new RuntimeException("no such variable:"));
	}

	public int getLocalVariableIndex(String varName) {
		return localVariables.indexOf(varName);
	}

	public boolean isLocalVariableExists(String varName) {
		return localVariables.containsKey(varName);
	}
}
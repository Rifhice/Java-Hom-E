package ExpressionInterpreter;

import java.util.Observable;

public abstract class EnvironmentVariable extends Observable{
	
	private String name;
	private String unity;
	private String description;
	
	public EnvironmentVariable(String name, String description, String unity) {
		this.name = name;
		this.unity = unity;
		this.description = description;
	}
	
	public abstract boolean isEqual(Object value);
	public abstract boolean isNotEqual(Object value);
	public abstract boolean isSuperior(Object value);
	public abstract boolean isInferior(Object value);
	public abstract boolean isSuperiorOrEqual(Object value);
	public abstract boolean isInferiorOrEqual(Object value);
	public abstract void setCurrentValue(Object value);
	public abstract Object getCurrentValue();
	
	public String toString() {
		return name;
	}
	
}

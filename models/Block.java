package models;

public class Block implements Evaluable{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private EnvironmentVariable variable;
	private Object value;
	private String operator;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Block(EnvironmentVariable variable, Object value, String operator) {
		this.variable = variable;
		this.value = value;
		this.operator = operator;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //
	public boolean evaluate() {
		switch (operator) {
		case "==":
			return variable.isEqual(value);
		case "!=":
			return variable.isNotEqual(value);
		case "<=":
			return variable.isInferiorOrEqual(value);
		case ">=":
			return variable.isSuperiorOrEqual(value);
		case "<":
			return variable.isSuperior(value);
		case ">":
			return variable.isInferior(value);
		}
		return false;
	}
	
	public String toString() {
		return variable.toString() + " " + operator + " " + value;
	}
	
	public EnvironmentVariable getVariable() {
		return variable;
	}

}

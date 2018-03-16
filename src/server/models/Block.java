package server.models;

/**
 * A block is something evaluable composed by an environment variable compares thanks
 * to an operator to a environment value.
 * Example : Temperature > 3, Presence == True
 * @author Clm-Roig
 */
public class Block implements Evaluable{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private EnvironmentVariable variable;
	private Object environmentValue;
	private String operator;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Block(EnvironmentVariable variable, Object environmentValue, String operator) {
		this.variable = variable;
		this.environmentValue = environmentValue;
		this.operator = operator;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //
	public boolean evaluate() {
		switch (operator) {
		case "==":
			return variable.isEqual(environmentValue);
		case "!=":
			return variable.isNotEqual(environmentValue);
		case "<=":
			return variable.isInferiorOrEqual(environmentValue);
		case ">=":
			return variable.isSuperiorOrEqual(environmentValue);
		case "<":
			return variable.isSuperior(environmentValue);
		case ">":
			return variable.isInferior(environmentValue);
		}
		return false;
	}
	
	public String toString() {
		return variable.toString() + " " + operator + " " + environmentValue;
	}
	
	public EnvironmentVariable getVariable() {
		return variable;
	}

}

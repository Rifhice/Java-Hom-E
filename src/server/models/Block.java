package server.models;

/**
 * A block is something evaluable composed by an environment variable compares thanks
 * to an operator to a environment value.
 * Example : Temperature > 3, Presence == True
 * @author Clm-Roig
 */
public class Block implements Evaluable {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String operator;
    
    // Attributes from other table
    private EnvironmentValue environmentValue; 
    private EnvironmentVariable environmentVariable;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Block() {}

    public Block(EnvironmentVariable environmentVariable, EnvironmentValue environmentValue, String operator) {
        this.environmentVariable = environmentVariable;
        this.environmentValue = environmentValue;
        this.operator = operator;
    }

    public Block(int id, EnvironmentVariable environmentVariable, EnvironmentValue environmentValue, String operator) {
        this.id = id;
        this.environmentValue = environmentValue;
        this.environmentVariable = environmentVariable;
        this.operator = operator;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public Object getEnvironmentValue() {
        return environmentValue;
    }

    public void setEnvironmentValue(EnvironmentValue environmentValue) {
        this.environmentValue = environmentValue;
    }

    public EnvironmentVariable getEnvironmentVariable() {
        return environmentVariable;
    }
    
    public void setEnvironmentVariable(EnvironmentVariable environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    // =========================================================

    public boolean evaluate() {
        switch (operator) {
        case "==":
            return environmentVariable.isEqual(environmentValue);
        case "!=":
            return environmentVariable.isNotEqual(environmentValue);
        case "<=":
            return environmentVariable.isInferiorOrEqual(environmentValue);
        case ">=":
            return environmentVariable.isSuperiorOrEqual(environmentValue);
        case "<":
            return environmentVariable.isSuperior(environmentValue);
        case ">":
            return environmentVariable.isInferior(environmentValue);
        }
        return false;
    }

    public String toString() {
        return environmentVariable.toString() + " " + operator + " " + environmentValue;
    }

}

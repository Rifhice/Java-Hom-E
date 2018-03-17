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
    private Value value; 
    private EnvironmentVariable environmentVariable;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Block() {}

    public Block(EnvironmentVariable environmentVariable, Value value, String operator) {
        this.environmentVariable = environmentVariable;
        this.value = value;
        this.operator = operator;
    }

    public Block(int id, EnvironmentVariable environmentVariable, Value value, String operator) {
        this.id = id;
        this.value = value;
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
    
    public Object getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
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
            return environmentVariable.isEqual(value);
        case "!=":
            return environmentVariable.isNotEqual(value);
        case "<=":
            return environmentVariable.isInferiorOrEqual(value);
        case ">=":
            return environmentVariable.isSuperiorOrEqual(value);
        case "<":
            return environmentVariable.isSuperior(value);
        case ">":
            return environmentVariable.isInferior(value);
        }
        return false;
    }

    public String toString() {
        return environmentVariable.toString() + " " + operator + " " + value;
    }

}

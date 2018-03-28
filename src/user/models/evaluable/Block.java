package user.models.evaluable;

import org.json.JSONObject;

import user.models.environmentVariable.EnvironmentVariable;

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
    private String operator;
    private String value;
    // Attributes from other table
    private EnvironmentVariable environmentVariable;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Block() {}
    
    public Block(EnvironmentVariable environmentVariable, String value, String operator) {
        this.value = value;
        this.environmentVariable = environmentVariable;
        this.operator = operator;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    public EnvironmentVariable getEnvironmentVariable() {
        return environmentVariable;
    }
    
    public void setEnvironmentVariable(EnvironmentVariable environmentVariable) {
        this.environmentVariable = environmentVariable;
    }

    // =========================================================

    public String toString() {
        return environmentVariable.getName() + " " + operator + " " + value;
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("type", "block");
        result.put("operator",operator);
        result.put("variable", environmentVariable.getId());
        result.put("value", value);
        return result;
    }
}

package server.models.evaluable;

import org.json.JSONObject;

import server.models.commandValue.CommandValue;
import server.models.environmentVariable.EnvironmentVariable;

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
    private CommandValue commandValue; 
    private EnvironmentVariable environmentVariable;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Block() {}

    public Block(EnvironmentVariable environmentVariable, CommandValue commandValue, String operator) {
        this.environmentVariable = environmentVariable;
        this.commandValue = commandValue;
        this.operator = operator;
    }

    public Block(int id, EnvironmentVariable environmentVariable, CommandValue commandValue, String operator) {
        this.id = id;
        this.commandValue = commandValue;
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
    
    public Object getCommandValue() {
        return commandValue;
    }

    public void setCommandValue(CommandValue commandValue) {
        this.commandValue = commandValue;
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
            return environmentVariable.isEqual(commandValue);
        case "!=":
            return environmentVariable.isNotEqual(commandValue);
        case "<=":
            return environmentVariable.isInferiorOrEqual(commandValue);
        case ">=":
            return environmentVariable.isSuperiorOrEqual(commandValue);
        case "<":
            return environmentVariable.isSuperior(commandValue);
        case ">":
            return environmentVariable.isInferior(commandValue);
        }
        return false;
    }

    public String toString() {
        return environmentVariable.toString() + " " + operator + " " + commandValue;
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("type", "block");
        result.put("id", id);
        result.put("operator",operator);
        result.put("command", commandValue.toJson());
        result.put("variable", environmentVariable.toJson());
        return result;
    }
    
}

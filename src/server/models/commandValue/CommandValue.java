package server.models.commandValue;

import java.util.ArrayList;

import org.json.JSONObject;

/**
 * A value is an information the actuator needs to perform an action or modify its state
 * It allows the server to send the right action to the actuators.
 * @author Jade
 */
public abstract class CommandValue {
 // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;   

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public CommandValue() {}
        
    public CommandValue(String name) {
        this.name = name;
    }
    
    public CommandValue(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    // ===========================================
    
    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("name",name);
        return result;
    }
}

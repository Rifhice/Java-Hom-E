package server.models;

import org.json.JSONObject;

/**
 * An action is a message sent by the server to the actuators which needs to react according to it.
 * @author Clm-Roig
 */
public class AtomicAction {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String executable;
    private Actuator actuator;
    
    // Attributes from other tables

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public AtomicAction() {}

    public AtomicAction(int id, String executable) {
        this.id = id;
        this.executable = executable;
    }
    
    public AtomicAction(String executable,Actuator actuator) {
        this.executable = executable;
        this.actuator = actuator;
    }
    
    public AtomicAction(int id, String executable,Actuator actuator) {
        this.id = id;
        this.executable = executable;
        this.actuator = actuator;
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

    public Actuator getActuator() {
		return actuator;
	}

	public void setActuator(Actuator actuator) {
		this.actuator = actuator;
	}

	public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }
    
    // ================================
    public String toString() {
        String res = "AT.ACTION #"+id ;
        res += "  " + executable;      
        return res;
    }
    
    public void execute() {
        // TODO
    }
    
    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
    	result.put("id", id);
    	result.put("executable", executable);
    	result.put("actuator", actuator.getId());
    	return result;
    }    

}

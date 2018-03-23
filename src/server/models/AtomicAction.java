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
    private String name;
    private String executable;
    private Actuator actuator;
    
    // Attributes from other tables

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public AtomicAction() {}

    public AtomicAction(int id, String name, String executable) {
        this.id = id;
        this.name = name;
        this.executable = executable;
    }
    
    public AtomicAction(int id, String name, String executable,Actuator actuator) {
        this.id = id;
        this.name = name;
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        String res = "AT.ACTION #"+id+" "+name ;
        res += "  " + executable;      
        return res;
    }
    
    public void execute() {
        // TODO
    }
    
    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
    	result.put("id", id);
    	result.put("name", name);
    	result.put("executable", executable);
    	result.put("actuator", actuator);
    	return result;
    }    

}

package server.models;

/**
 * A command is the information about the possible values (continuous or discrete) an actuator can receive to perform one action
 * @author Clm-Roig
 */
public class Command {
	
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;
    
    // Attributes from other tables
    private int actuatorId;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Command() {}
    
    public Command(String name) {
        this.name = name;
    }
    
    public Command(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Command(int id, String name, int actuatorId) {
        this.id = id;
        this.name = name;
        this.actuatorId = actuatorId;
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

    public int getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(int actuatorId) {
        this.actuatorId = actuatorId;
    }
    
    // =================================================    
	
	
}

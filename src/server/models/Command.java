package server.models;

import java.util.ArrayList;

import server.models.argument.Argument;

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
    private String description;
    private String key;
    private ArrayList<Argument> arguments;
    
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
    
    public Command(String name,String description, String key, ArrayList<Argument> arguments) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.arguments = arguments;
    }
    
    public Command(int id, String name, int actuatorId,ArrayList<Argument> arguments) {
        this.id = id;
        this.name = name;
        this.actuatorId = actuatorId;
        this.arguments = arguments;
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

    public String toString() {
    	String res = "#"+id +" "+ name + "\n" + description + "\nKey : " + key + "\nARGUMENTS\n";
    	if(arguments != null) {
    	    for (Argument argument : arguments) {
                res += argument + "\n";
            }
    	}
    	return res;
    }
    
	
	
}

package models;


import java.util.ArrayList;

/**
 * An actuator is a connected object managed by the server and performing physical actions according to it. 
 * @author Clm-Roig
 */
public class Actuator extends ExternalActor{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<Command> commands;
	
	// Attributes from others tables
	private int category_actuator_id;
	private String category_actuator_name;
	private String category_actuator_description;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Actuator() {
	    
	}
	
	public Actuator(String name, String description,ArrayList<Command> commands) {
		super(name,description);
		this.commands = commands;
	}
	
	public Actuator(String name, String description,ArrayList<Command> commands, int category_actuator_id, String category_actuator_name, String category_actuator_description) {
        super(name,description);
        this.commands = commands;
        this.category_actuator_id = category_actuator_id;
        this.category_actuator_name = category_actuator_name;
        this.category_actuator_description = category_actuator_description;
    }
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //
	public static Actuator registerToTheSystem(Object jsonToParse) {
		return null;
	}
	
}

package models;


import java.util.ArrayList;

public class Actuator extends ExternalActor{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<Command> commands;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Actuator(String name, String description,ArrayList<Command> commands) {
		super(name,description);
		this.commands = commands;
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //
	public static Actuator registerToTheSystem(Object jsonToParse) {
		return null;
	}
	
}

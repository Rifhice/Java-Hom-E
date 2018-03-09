package models;


import java.util.ArrayList;

public class Actuator extends ExternalActor{

	private ArrayList<Command> commands;
	
	public Actuator(String name, String description,ArrayList<Command> commands) {
		super(name,description);
		this.commands = commands;
	}

	public static Actuator registerToTheSystem(Object jsonToParse) {
		return null;
	}
	
}

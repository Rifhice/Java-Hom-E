package server.models;

/**
 * A command is the information about the possible values (continuous or discrete) an actuator can receive to perform one action
 * @author Clm-Roig
 */
public abstract class Command {
	
	public abstract void launch();
	
}

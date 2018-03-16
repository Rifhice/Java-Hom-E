package server.models;

/**
 * A command is a message sent by the server to the actuators which needs to react according to it.
 * @author Clm-Roig
 */
public abstract class Command {
	
	public abstract void launch();
	
}

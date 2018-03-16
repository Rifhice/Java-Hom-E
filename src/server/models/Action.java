package server.models;

/**
 * An actuator is a message sent by the server to the actuators which needs to react according to it.
 * @author Jade
 */

public abstract class Action {
	
	public abstract void launch();

}

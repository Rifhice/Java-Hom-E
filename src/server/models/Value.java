package server.models;

import java.util.Observable;

	
/**
 * A value is an information the actuator needs to perform an action or modify its state
 * It allows the server to send the right action to the actuators.
 * @author Jade
 */
public abstract class Value {
	    
	// ==================== //
	// ==== ATTRIBUTES ==== //
	// ==================== //
	private int id;
	private String name;   

	// ====================== //
	// ==== CONSTRUCTORS ==== //
	// ====================== //
	public Value() {}
	    
	public Value(String name) {
		this.name = name;
	}
		

}

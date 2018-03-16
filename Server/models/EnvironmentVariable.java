package models;


import java.util.Observable;

/**
 * An environment variable is an interesting value  for the server, related to the environment. 
 * It allows the server to send the right command to the actuators.
 * @author Clm-Roig
 */
public abstract class EnvironmentVariable extends Observable{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
	private String name;
	private String unit;
	private String description;
	private static int ID_COUNT = 0;    

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public EnvironmentVariable() {}
    
	public EnvironmentVariable(String name, String description, String unit) {
		this.name = name;
		this.unit = unit;
		this.description = description;
		id = ID_COUNT;
		ID_COUNT++;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //	
	public int getId() {
        return id;
    }
	
	public String toString() {
        return id + ": " + name;
    }
	
	public abstract boolean isEqual(Object value);
	public abstract boolean isNotEqual(Object value);
	public abstract boolean isSuperior(Object value);
	public abstract boolean isInferior(Object value);
	public abstract boolean isSuperiorOrEqual(Object value);
	public abstract boolean isInferiorOrEqual(Object value);
}

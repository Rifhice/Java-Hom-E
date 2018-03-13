package models;


import java.util.Observable;

public abstract class EnvironmentVariable extends Observable{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private String name;
	private String unity;
	private String description;
	private static int ID_COUNT = 0;
    private int id;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public EnvironmentVariable(String name, String description, String unity) {
		this.name = name;
		this.unity = unity;
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
	public abstract void setCurrentValue(Object value);
	public abstract Object getCurrentValue();	
}

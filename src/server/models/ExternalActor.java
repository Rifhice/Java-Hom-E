package server.models;


public class ExternalActor {
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //	
	protected int id;
	protected String name;
	protected String description;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public ExternalActor() {}
	
	public ExternalActor(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// ==================================
	
	public void enable() {
		
	}
	
	public void disable() {
		
	}
	
	public boolean isEnabled() {
		return true;
	}
}

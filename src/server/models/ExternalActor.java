package server.models;

import org.json.JSONObject;

public class ExternalActor {
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //	
	protected int id;
	protected String name;
	protected String description;
	protected boolean isActivated = true;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public ExternalActor() {}
	
	public ExternalActor(int id) {
        this.id = id;
    }
	
	public ExternalActor(String name, String description) {
        this.name = name;
        this.description = description;
    }
	
	public ExternalActor(int id, String name, String description) {
	    this.id = id;
		this.name = name;
		this.description = description;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
		isActivated = true;
	}
	
	public void disable() {
		isActivated = false;
	}
	
	public boolean isActivated() {
		return isActivated;
	}
	
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put("id"	, id);
		result.put("name", name);
		result.put("description", description);
		result.put("activated", isActivated);
		return result;
	}
}

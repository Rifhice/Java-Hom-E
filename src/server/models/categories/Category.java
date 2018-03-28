package server.models.categories;

import org.json.JSONObject;

public abstract class Category {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	protected int id;
	protected String name;
	protected String description;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Category() {
		
	}
	
	public Category(int id, String name,String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Category(String name, String description) {
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
	
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put("id",id);
		result.put("name",name);
		result.put("description",description);
		return result;
	}
	
}

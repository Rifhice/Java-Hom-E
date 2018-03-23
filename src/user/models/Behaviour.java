package user.models;

import server.models.evaluable.Expression;

public class Behaviour {
	// ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
	private String name;
	private boolean isActivated;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Behaviour(int id, String name, Boolean isActivated) {
        this.id = id;
        this.name = name;
        this.isActivated = isActivated;
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

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }	
}

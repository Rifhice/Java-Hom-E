package server.models;

import java.util.Date;

import org.json.JSONObject;

/**
 *
 * @author Clm-Roig
 */
public abstract class History {
	    
	// ==================== //
	// ==== ATTRIBUTES ==== //
	// ==================== //
	private int id;
	private Date date;
	private String type;
	private String action;
	private String user;

	// ====================== //
	// ==== CONSTRUCTORS ==== //
	// ====================== //
	public History() {}
	    
	public History(Date date, String type, String action, String user) {
		this.date = date;
		this.type = type;
		this.action = action;
		this.user = user;
	}
	
	public History(int id, Date date, String type, String action, String user) {
	    this.id = id;
        this.date = date;
        this.type = type;
        this.action = action;
        this.user = user;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    // ===========================================
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put("id",id);
		result.put("type", type);
		result.put("action", action);
		result.put("user",user);
		result.put("date", date);
		return result;
	}

}

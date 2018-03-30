package server.models;

import java.util.ArrayList;

import org.json.JSONObject;

public class Role {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name; 

    // Attributes from others tables   
    private ArrayList<Right> rights;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Role() {}

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Role(int id, String name, ArrayList<Right> rights) {
        this.id = id;
        this.name = name;
        this.rights = rights;
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

    public ArrayList<Right> getRights() {
        return rights;
    }

    public void setRights(ArrayList<Right> rights) {
        this.rights = rights;
    }  

    // ==================================

    public String toString() {
        String role = "\n ROLE #"+id+" "+name;
        role += "\nRights:";
        if(rights != null) {
            for (Right right : rights) {
                role += "\n"+right;
            }
        }
        return role;
    }  
    
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("name", name);
        if (rights != null) {
        	for (int i = 0; i < rights.size(); i++) {
    			result.append("rights", rights.get(i).toJson());
    		}
        	
        }
        
        return result;
    }

}

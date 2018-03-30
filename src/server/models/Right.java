package server.models;

import java.util.ArrayList;

import org.json.JSONObject;

public class Right {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String denomination; 
    private String description;
    
    // Attributes from other tables
    private ArrayList<Command> commands;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Right() {}

    public Right(String denomination, String description) {
        this.denomination = denomination;
        this.description = description;
    }
    
    public Right(int id, String denomination, String description) {
        this.id = id;
        this.denomination = denomination;
        this.description = description;
    }
    
    public Right(int id, String denomination, String description, ArrayList<Command> commands) {
        this.id = id;
        this.denomination = denomination;
        this.description = description;
        this.commands = commands;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }
    
    // ==================================

    public String toString() {
        String right = "Right #"+ id +" "+ denomination + "\n"+ description;          
        return right;
    }
    
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("denomination",denomination);
        result.put("description", description);
        if (commands != null) {
	        for (int i = 0; i < commands.size(); i++) {
				result.append("commands", commands.get(i).toJson());
			}
        }
        return result;
    }
    
    public static void main(String[] args) {
        Right r = new Right(1,"test","blabla");
        System.out.println(r);
    }

}

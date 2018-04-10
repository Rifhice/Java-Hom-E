package server.models;

import java.util.ArrayList;

import org.json.JSONObject;

import server.models.commandValue.CommandValue;

/**
 * A command is the information about the possible values (continuous or discrete) an actuator can receive to perform one action
 * @author Clm-Roig
 */
public class Command {
	
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;
    private String description;
    private String key;
    
    // Attributes from other tables
    private Actuator actuator;
    private ArrayList<CommandValue> commandValues;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Command() {}
    
    public Command(String name) {
        this.name = name;
    }
    
    public Command(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Command(int id, String name, Actuator actuator) {
        this.id = id;
        this.name = name;
        this.actuator = actuator;
    }
    
    public Command(String name,String description, String key, ArrayList<CommandValue> commandValues) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.commandValues = commandValues;
    }
    
    public Command(int id, String name, Actuator actuator,ArrayList<CommandValue> commandValues) {
        this.id = id;
        this.name = name;
        this.actuator = actuator;
        this.commandValues = commandValues;
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

    public String getKey() {
        return key;
    }

    public ArrayList<CommandValue> getCommandValues() {
        return commandValues;
    }

    public void setCommandValues(ArrayList<CommandValue> commandValues) {
        this.commandValues = commandValues;
    }

    public Actuator getActuator() {
        return actuator;
    }

    public void setActuator(Actuator actuator) {
        this.actuator = actuator;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    // =================================================    

    public String toString() {
    	String res = "#"+id +" "+ name + "\n" + description + "\nKey : " + key + "\nARGUMENTS\n";
    	if(commandValues != null) {
    	    for (CommandValue commandValue : commandValues) {
                res += commandValue + "\n";
            }
    	}
    	return res;
    }
    
    public JSONObject toJson() {
    	JSONObject curentCommand = new JSONObject();
		curentCommand.put("id", getId());
		curentCommand.put("name", getName());
		curentCommand.put("description", getDescription());
		curentCommand.put("key", getKey());
		if(actuator != null) {
			curentCommand.put("actuator",actuator.getId());
		}
		ArrayList<CommandValue> commandValue = getCommandValues();
		if(commandValue != null) {
			for (int j = 0; j < commandValue.size(); j++) {
				curentCommand.append("commandValue", commandValue.get(j).toJson());
			}
		}
    	return curentCommand;
    }
	
}

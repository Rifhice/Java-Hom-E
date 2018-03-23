package server.models.categories;

import java.util.ArrayList;

import org.json.JSONObject;

import server.models.Actuator;

public class ActuatorCategory extends Category{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //

    // Attributes from other tables
    ArrayList<Actuator> actuators;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ActuatorCategory() {}
    
    public ActuatorCategory(String name, String description) {
        super(name, description);
    }
    
    public ActuatorCategory(int id, String name, String description) {
        super(id, name, description);
    }
    
    public ActuatorCategory(int id, String name, String description, ArrayList<Actuator> actuators) {
        super(id, name, description);
        this.actuators = actuators;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public ArrayList<Actuator> getActuators() {
        return actuators;
    }

    public void setActuators(ArrayList<Actuator> actuators) {
        this.actuators = actuators;
    }
    
    // ==========================================
    
    public String toString() {
        String res = "ACTUATOR CAT. #" + id + " " + name + "\n" + description;
        return res;
    }

    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	for (int i = 0; i < actuators.size(); i++) {
			result.append("actuators", actuators.get(i).toJson());
		}
    	return result;
    }
    
}

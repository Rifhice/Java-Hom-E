package server.models.categories;

import java.util.ArrayList;

import org.json.JSONObject;

import server.models.Sensor;

public class SensorCategory extends Category{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    
    // Attributes from other tables
    ArrayList<Sensor> sensors;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SensorCategory() {}

    public SensorCategory(String name, String description) {
        super(name, description);
    }

    public SensorCategory(int id, String name, String description) {
        super(id, name, description);
    }
    
    public SensorCategory(int id, String name, String description, ArrayList<Sensor> sensors) {
        super(id, name, description);
        this.sensors = sensors;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }
    
    // ==========================================   
    
    public String toString() {
        return "Sensor category : " + id + "\nName : " + name + "\nDescription : " + description;
    }
    
    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	for (int i = 0; i < sensors.size(); i++) {
			result.append("sensors", sensors.get(i).toJson());
		}
    	return result;
    }
}

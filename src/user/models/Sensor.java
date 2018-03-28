package user.models;


import java.util.ArrayList;

import org.json.JSONObject;


/**
 * A sensor is a connected object in charge of sending useful changes about environment variables to the server.
 * @author Clm-Roig
 *
 */
public class Sensor extends ExternalActor{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<EnvironmentVariable> environmentVariables;
	
	// Attributes from others tables
    private SensorCategory sensorCategory;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Sensor() {}
    
    public Sensor(String name,String description) {
    	super(name,description);
    }
    
    public Sensor(String name, String description, ArrayList<EnvironmentVariable> environmentVariables) {
        super(name, description);
        this.environmentVariables = environmentVariables;
        for (int i = 0; i < environmentVariables.size(); i++) {
            environmentVariables.get(i).setSensor(this);
			
		}
    }
    
	public Sensor(int id, String name, String description, ArrayList<EnvironmentVariable> environmentVariables) {
		super(id, name, description);
		this.environmentVariables = environmentVariables;
	}
	
	public Sensor(int id, String name, String description, ArrayList<EnvironmentVariable> environmentVariables, 
	        SensorCategory sensorCategory) {
        super(id, name, description);
        this.environmentVariables = environmentVariables;
        this.sensorCategory = sensorCategory;
    }

	// ================= //
    // ==== METHODS ==== //
    // ================= //
	public void setId(int id) {
		this.id = id;
	}
	
	public void changeValue(int idVariable, String value) {
		for (int i = 0; i < environmentVariables.size(); i++) {
			if(environmentVariables.get(i).getId() == idVariable) {
				if(environmentVariables.get(i).getValue() instanceof ContinuousValue) {
					double valueDouble = Double.parseDouble(value);
					((ContinuousValue)environmentVariables.get(i).getValue()).setCurrentValue(valueDouble);
				}
				else {
					((DiscreteValue)environmentVariables.get(i).getValue()).setCurrentValue(value);
				}
			}
		}
	}
	
	public ArrayList<EnvironmentVariable> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariable(ArrayList<EnvironmentVariable> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }
    
    public SensorCategory getSensorCategory() {
        return sensorCategory;
    }

    public void setSensorCategory(SensorCategory sensorCategory) {
        this.sensorCategory = sensorCategory;
    }   
    
    // ===================================================

    public String toString() {
		String res = "SENSOR #" + id + " " + this.name + "\n" + this.sensorCategory + "\n"+ this.description + "\nVARIABLES";
		if(environmentVariables != null) {
			for (int i = 0; i < environmentVariables.size(); i++) {
				res += "\n" + environmentVariables.get(i);
			}
		}
		return res;
	}
    
    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
        for (int i = 0; i < environmentVariables.size(); i++) {
			result.append("environmentVariable", environmentVariables.get(i).toJson());
		}
        result.put("category", sensorCategory);
        return result;
    }

}

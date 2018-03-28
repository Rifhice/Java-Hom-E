package server.models;


import org.json.JSONObject;

import server.models.categories.SensorCategory;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;

/**
 * A sensor is a connected object in charge of sending useful changes about environment variables to the server.
 * @author Clm-Roig
 *
 */
public class Sensor extends ExternalActor{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private EnvironmentVariable environmentVariables = null;
	
	// Attributes from others tables
    private SensorCategory sensorCategory;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Sensor() {}
    
    public Sensor(String name,String description) {
    	super(name,description);
    }
    
    public Sensor(String name, String description, EnvironmentVariable environmentVariables) {
        super(name, description);
        this.environmentVariables = environmentVariables;
        environmentVariables.setSensor(this);
    }
    
	public Sensor(int id, String name, String description, EnvironmentVariable environmentVariables) {
		super(id, name, description);
		this.environmentVariables = environmentVariables;
	}
	
	public Sensor(int id, String name, String description, EnvironmentVariable environmentVariables, 
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
	
	public void changeValue(String value) {
		if(environmentVariables.getValue() instanceof ContinuousValue) {
			double valueDouble = Double.parseDouble(value);
			((ContinuousValue)environmentVariables.getValue()).setCurrentValue(valueDouble);
		}
		else {
			((DiscreteValue)environmentVariables.getValue()).setCurrentValue(value);
		}
	}
	
	public EnvironmentVariable getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariable(EnvironmentVariable environmentVariables) {
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
		String res = "SENSOR #" + id + " " + this.name + "\n" +/* this.sensorCategory +*/ "\n"+ this.description + "\nVARIABLES";
		if(environmentVariables != null) {
			res += "\n" + environmentVariables;
		}
		return res;
	}
    
    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	if(environmentVariables != null) {
			result.put("environmentVariable", environmentVariables.toJson());
    	}
        //result.put("category", sensorCategory);
        return result;
    }

}

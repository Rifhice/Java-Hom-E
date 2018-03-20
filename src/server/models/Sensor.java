package server.models;


import java.util.ArrayList;

import server.models.categories.SensorCategory;
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
	private ArrayList<EnvironmentVariable> environmentVariable;
	
	// Attributes from others tables
    private SensorCategory sensorCategory;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Sensor() {}
    
    public Sensor(String name,String description) {
    	super(name,description);
    }
    
    public Sensor(String name, String description, ArrayList<EnvironmentVariable> environmentVariable) {
        super(name, description);
        this.environmentVariable = environmentVariable;
    }
    
	public Sensor(int id, String name, String description, ArrayList<EnvironmentVariable> environmentVariable) {
		super(id, name, description);
		this.environmentVariable = environmentVariable;
	}
	
	public Sensor(int id, String name, String description, ArrayList<EnvironmentVariable> environmentVariables, 
	        SensorCategory sensorCategory) {
        super(id, name, description);
        this.environmentVariable = environmentVariables;
        this.sensorCategory = sensorCategory;
    }

	// ================= //
    // ==== METHODS ==== //
    // ================= //
	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<EnvironmentVariable> getEnvironmentVariable() {
        return environmentVariable;
    }

    public void setEnvironmentVariable(ArrayList<EnvironmentVariable> environmentVariable) {
        this.environmentVariable = environmentVariable;
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
		if(environmentVariable != null) {
			for (int i = 0; i < environmentVariable.size(); i++) {
				res += "\n" + environmentVariable.get(i);
			}
		}
		return res;
	}

}

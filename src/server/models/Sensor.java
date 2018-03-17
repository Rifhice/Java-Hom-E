package server.models;


import java.util.ArrayList;

import org.json.*;

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
    private int sensorCategoryId;
    private String sensorCategoryName;
    private String sensorCategoryDescription;
	
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
	        int sensorCategoryId, String sensorCategoryName, String sensorCategoryDescription) {
        super(id, name, description);
        this.environmentVariable = environmentVariables;
        this.sensorCategoryId = sensorCategoryId;
        this.sensorCategoryName = sensorCategoryName;
        this.sensorCategoryDescription = sensorCategoryDescription;
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

    public int getSensorCategoryId() {
        return sensorCategoryId;
    }

    public void setSensorCategoryId(int sensorCategoryId) {
        this.sensorCategoryId = sensorCategoryId;
    }

    public String getSensorCategoryName() {
        return sensorCategoryName;
    }

    public void setSensorCategoryName(String sensorCategoryName) {
        this.sensorCategoryName = sensorCategoryName;
    }

    public String getSensorCategoryDescription() {
        return sensorCategoryDescription;
    }

    public void setSensorCategoryDescription(String sensorCategoryDescription) {
        this.sensorCategoryDescription = sensorCategoryDescription;
    }
    
    // ===================================================$
    
	public String toString() {
		String res = "ID : " + id + "\nName : " + this.name + "\n" + "Description : " + this.description + "\n\nVariables : \n";
		if(environmentVariable != null) {
			for (int i = 0; i < environmentVariable.size(); i++) {
				res += environmentVariable.get(i) + "\n";
			}
		}
		return res;
	}

}

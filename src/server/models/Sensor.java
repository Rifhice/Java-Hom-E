package server.models;


import java.util.ArrayList;

import org.json.*;

public class Sensor extends ExternalActor{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<EnvironmentVariable> environmentVariable;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Sensor(String name, String description, ArrayList<EnvironmentVariable> environmentVariable) {
		super(name, description);
		this.environmentVariable = environmentVariable;
	}

	// ====================== //
    // ==== METHODS ==== //
    // ====================== //
	public ArrayList<EnvironmentVariable> getEnvironmentVariable() {
		return environmentVariable;
	}

	public String toString() {
		String res = "ID : " + id + "\nName : " + this.name + "\n" + "Description : " + this.description + "\n\nVariables : \n";
		for (int i = 0; i < environmentVariable.size(); i++) {
			res += environmentVariable.get(i) + "\n";
		}
		return res;
	}
	
	public static Sensor registerToTheSystem(JSONObject jsonToParse) {
		String name = jsonToParse.getString("name");
		String description = jsonToParse.getString("description");
		ArrayList<EnvironmentVariable> variables = new ArrayList<EnvironmentVariable>();
		JSONArray arr = jsonToParse.getJSONArray("variables");
		for (int i = 0; i < arr.length(); i++){
			JSONObject object = arr.getJSONObject(i);
			if(object.getString("type").equals("continuous")) {
				variables.add(new ContinuousEnvironmentVariable(object.getString("name"), object.getString("description"), object.getString("unity"), object.getDouble("valuemin"), object.getDouble("valuemax"), object.getDouble("precision"), object.getDouble("currentvalue")));
			}
			else if(arr.getJSONObject(i).getString("type").equals("discrete")){
				ArrayList<String> values = new ArrayList<String>();
				JSONArray valuesArray = object.getJSONArray("values");
				for (int j = 0; j < valuesArray.length(); j++) {
					values.add(valuesArray.getString(i));
				}
				variables.add(new DiscreteEnvironmentVariable(object.getString("name"), object.getString("description"), object.getString("unity"), values, object.getString("currentvalue")));
			}
		}
		return new Sensor(name, description, variables);
	}

}

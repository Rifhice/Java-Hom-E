package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import server.models.Sensor;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;
import server.models.environmentVariable.Value;
import ocsf.server.ConnectionToClient;

public class SensorManager extends Manager{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<Sensor> sensors;
	
	private static SensorManager manager = null;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	private SensorManager() {
		sensors = new ArrayList<Sensor>();
	}
	
	public static SensorManager getManager() {
		if(manager == null) 
			manager = new SensorManager();
		return manager;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= // 
	
	/**
	 * Create a sensor from a JSON. 
	 * @param jsonToParse
	 * @return
	 */
	public static Sensor getSensorFromJson(JSONObject jsonToParse) {
		try {
			String name = jsonToParse.getString("name");
			String description = jsonToParse.getString("description");
			
			ArrayList<EnvironmentVariable> variables = new ArrayList<EnvironmentVariable>();
			JSONArray arr = jsonToParse.getJSONArray("variables");
			
			for (int i = 0; i < arr.length(); i++){
			    
			    // Set the environment variable
				JSONObject object = arr.getJSONObject(i);
				EnvironmentVariable ev = new EnvironmentVariable();
				ev.setName(object.getString("name"));
                ev.setName(object.getString("description"));
                ev.setName(object.getString("unit"));	
                
                Value value;
                // Value Continuous
                if(object.getString("valueType").equals("continuous")) {
                    value = new ContinuousValue();
                    ((ContinuousValue) value).setValueMin(object.getDouble("valueMin"));
                    ((ContinuousValue) value).setValueMax(object.getDouble("valueMax"));
                    ((ContinuousValue) value).setCurrentValue(object.getDouble("currentValue"));
                    ((ContinuousValue) value).setPrecision(object.getDouble("precision"));
                    
                    ev.setValue(value);
                }
                
                // Value Discrete
                else if(object.getString("valueType").equals("discrete")) {
                    value = new DiscreteValue();
                    ((DiscreteValue) value).setCurrentValue(object.getString("currentValue"));
                    
                    // Get the possible values
                    ArrayList<String> possibleValuesArray = new ArrayList<String>();
                    JSONArray valuesArray = object.getJSONArray("possibleValues");
                    for (int j = 0; j < valuesArray.length(); j++) {
                        possibleValuesArray.add(valuesArray.getString(j));
                    }
                    ((DiscreteValue) value).setPossibleValues(possibleValuesArray);        
                    ev.setValue(value);
                }
                
                variables.add(ev);			
			}
			return new Sensor(name, description, variables);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void registerSensorToTheSystem(JSONObject json,ConnectionToClient client) {
		Sensor sensor = getSensorFromJson(json); //Create the new Sensor object
		Sensor sensorCreated = null;
		try {
			sensorCreated = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO().create(sensor);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		if(sensorCreated != null) {
			sensors.add(sensor);
			result.put("result", "success");
			result.put("id", sensorCreated.getId());
		}
		else {
			result.put("result", "failure");
		}
		try {
			client.sendToClient(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sensor + "\nAdded to the system !");
	}
	
	public ArrayList<Sensor> getSensors(){
		return sensors;
	}
	
	public ArrayList<EnvironmentVariable> getEnvironmentVariables(){
		ArrayList<EnvironmentVariable> variables = new ArrayList<EnvironmentVariable>();
		for (int i = 0; i < sensors.size(); i++) {
			variables.addAll(sensors.get(i).getEnvironmentVariable());
		}
		return variables;
	}
	
	public void valueChange(JSONObject json) {
		
	}
	
	/**
	 * 
	 * @param id, the id of sensors searched
	 * @return Sensor, the sensor corresponding the id given or null if there is none.
	 */
	public Sensor getSensorById(String id) {
		for (int i = 0; i < sensors.size(); i++) {
			if(Integer.toString(sensors.get(i).getId()).equals(id)) {
				return sensors.get(i);
			}
		}
		return null;
	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String verb = json.getString("verb");
		switch (verb) {
		case "post":
			registerSensorToTheSystem(json,client);
			break;
		default:
			break;
		}
	}
}

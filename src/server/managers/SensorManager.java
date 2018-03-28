package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.SensorDAO;
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
	private SensorDAO sensorDAO = AbstractDAOFactory.getFactory(SystemManager.db).getSensorDAO();
	
	private static SensorManager manager = null;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	/**
	 *  Singleton pattern
	 */
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
                if(object.getString("type").equals("continuous")) {
                    value = new ContinuousValue();
                    ((ContinuousValue) value).setValueMin(object.getDouble("valueMin"));
                    ((ContinuousValue) value).setValueMax(object.getDouble("valueMax"));
                    ((ContinuousValue) value).setCurrentValue(object.getDouble("currentvalue"));
                    ((ContinuousValue) value).setPrecision(object.getDouble("precision"));
                    
                    ev.setValue(value);
                }
                
                // Value Discrete
                else if(object.getString("type").equals("discrete")) {
                    value = new DiscreteValue();
                    ((DiscreteValue) value).setCurrentValue(object.getString("currentvalue"));
                    
                    // Get the possible values
                    ArrayList<String> possibleValuesArray = new ArrayList<String>();
                    JSONArray valuesArray = object.getJSONArray("possiblevalues");
                    for (int j = 0; j < valuesArray.length(); j++) {
                        possibleValuesArray.add(valuesArray.getString(j));
                    }
                    ((DiscreteValue) value).setPossibleValues(possibleValuesArray);        
                    ev.setValue(value);
                }
                
                variables.add(ev);			
			}
			return new Sensor(name, description, variables.get(0));
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
			sensorCreated = sensorDAO.create(sensor);
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		if(sensorCreated != null) {
			sensors.add(sensor);
			result.put("result", "success");
			result.put("verb", "post");
			result.put("id", sensorCreated.getId());
			// TODO : temporarily, we just return the 1rst environmentVariable.
			result.put("idEnv",sensorCreated.getEnvironmentVariables().getId());
	        result.put("idValue",sensorCreated.getEnvironmentVariables().getValue().getId());
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
		variables = AbstractDAOFactory.getFactory(SystemManager.db).getEnvironmentVariableDAO().getAll();		
		return variables;
	}
	
	public void changeEnvironmentVariableValue(JSONObject json,ConnectionToClient client) {
		Sensor sensor = getSensorById(json.getInt("id"));
		if(sensor != null) {
			sensor.changeValue(json.getInt("idVariable"), json.getString("value"));
		}
		else {
			System.out.println("SENSOR UNKNOWN OR NOT CONNECTED");
		}
	}
	
	/**
	 * 
	 * @param id, the id of sensors searched
	 * @return Sensor, the sensor corresponding the id given or null if there is none.
	 */
	public Sensor getSensorById(int id) {
		for (int i = 0; i < sensors.size(); i++) {
			if(sensors.get(i).getId() == (id)) {
				return sensors.get(i);
			}
		}
		return null;
	}
	
	public void returnGetEnvironmentVariables(JSONObject json, ConnectionToClient client) {
	    ArrayList<EnvironmentVariable> environmentVariables = getEnvironmentVariables();

        JSONObject result = new JSONObject();
        if(environmentVariables != null) {
            result.put("result", "success");
            result.put("recipient", "sensor");
            result.put("action", "getEnvironmentVariables");
            for (EnvironmentVariable environmentVariable : environmentVariables) {
                result.append("environmentVariables", environmentVariable.toJson());
            }
        }
        else {
            result.put("result", "failure");
        }
        try {
            client.sendToClient(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("RESULT: "+result.toString());
	}

	public void getAll(JSONObject json,ConnectionToClient client) {
		ArrayList<Sensor> sensors = sensorDAO.getAll();
		JSONObject result = new JSONObject();
        result.put("result", "success");
        result.put("recipient", "sensor");
        result.put("action", "getAll");
		for (int i = 0; i < sensors.size(); i++) {
			result.append("sensors", sensors.get(i).toJson());
		}
        try {
            client.sendToClient(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
		switch (action) {
		case "post":
			registerSensorToTheSystem(json,client);
			break;
		case "changeValue":
			changeEnvironmentVariableValue(json, client);
			break;
		case "getEnvironmentVariables": 
		    returnGetEnvironmentVariables(json, client);
		    break;
		case "getAll":
			getAll(json,client);
		default:
			break;
		}
	}
}

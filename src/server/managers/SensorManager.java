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
	private static ArrayList<Sensor> sensors;
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
			JSONObject object = jsonToParse.getJSONObject("variables");

			EnvironmentVariable ev = new EnvironmentVariable();
			ev.setName(object.getString("name"));
            ev.setDescription(object.getString("description"));
            ev.setUnit(object.getString("unit"));	
            
            Value value;
            JSONObject valueJ = object.getJSONObject("value");
            // Value Continuous
            if(valueJ.getString("type").equals("continuous")) {
                value = new ContinuousValue();
                ((ContinuousValue) value).setValueMin(valueJ.getDouble("valueMin"));
                ((ContinuousValue) value).setValueMax(valueJ.getDouble("valueMax"));
                ((ContinuousValue) value).setCurrentValue(valueJ.getDouble("currentValue"));
                ((ContinuousValue) value).setPrecision(valueJ.getDouble("precision"));
                
                ev.setValue(value);
            }
            
            // Value Discrete
            else if(valueJ.getString("type").equals("discrete")) {
                value = new DiscreteValue();
                ((DiscreteValue) value).setCurrentValue(valueJ.getString("currentValue"));
                
                // Get the possible values
                ArrayList<String> possibleValuesArray = new ArrayList<String>();
                JSONArray valuesArray = valueJ.getJSONArray("possibleValues");
                for (int j = 0; j < valuesArray.length(); j++) {
                    possibleValuesArray.add(valuesArray.getString(j));
                }
                ((DiscreteValue) value).setPossibleValues(possibleValuesArray);        
                ev.setValue(value);
            }
            	
			return new Sensor(name, description, ev);
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
			result.put("idEnv",sensorCreated.getEnvironmentVariables().getId());
	        result.put("idValue",sensorCreated.getEnvironmentVariables().getValue().getId());
	        SystemManager.sendToAllClient(result.toString());
		}
		else {
			result.put("result", "failure");
			try {
				client.sendToClient(result.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(sensor + "\nAdded to the system !");
	}
	
	public static ArrayList<Sensor> getSensors(){
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
			sensor.changeValue(json.getString("value"));
			JSONObject result = new JSONObject();
			result.put("recipient", "sensor");
			result.put("action", "changeValue");
			result.put("idSensor", json.getInt("id"));
			result.put("value", json.getString("value"));
			try {
				SystemManager.sendToAllClient(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		ArrayList<EnvironmentVariable> environmentVariables = null;
		try {
			environmentVariables = getEnvironmentVariables();
	    }
	    catch (Exception e) {
			e.printStackTrace();
		}

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
        	result.put("recipient", "sensor");
            result.put("result", "failure");
            result.put("action", "getEnvironmentVariables");
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
			try {
			result.append("sensors", sensors.get(i).toJson());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(result.toString());
        try {
            client.sendToClient(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
    /**
     * Possible values for key "action":
     * <ul>
     * <li>post</li>
     * <li>changeValue</li>
     * <li>getEnvironmentVariables</li>
     * <li>getAll</li>
     * </ul>
     */
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

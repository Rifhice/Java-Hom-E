package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import server.models.ContinuousEnvironmentVariable;
import server.models.DiscreteEnvironmentVariable;
import server.models.EnvironmentVariable;
import server.models.Sensor;
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
	
	public static Sensor getSensorFromJson(JSONObject jsonToParse) {
		try {
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
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void registerSensorToTheSystem(JSONObject json,ConnectionToClient client) {
		Sensor sensor = getSensorFromJson(json); //Create the new Sensor object
		Sensor sensorCreated = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO().create(sensor);
		JSONObject result = new JSONObject();
		if(sensorCreated != null) {
			sensors.add(sensor);
			json.put("result", "success");
			json.put("id", sensorCreated.getId());
		}
		else {
			json.put("result", "failure");
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

package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.models.EnvironmentVariable;
import server.models.Sensor;
import ocsf.server.ConnectionToClient;

public class SensorManager extends Manager{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	ArrayList<Sensor> sensors;
	
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
	public void registerSensorToTheSystem(JSONObject json) {
		sensors.add(Sensor.registerToTheSystem(json));
		System.out.println(sensors.get(sensors.size()-1) + "\nAdded to the system !");
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
		// TODO Auto-generated method stub
		
	}
}

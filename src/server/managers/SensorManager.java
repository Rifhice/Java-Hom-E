package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
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
	public void registerSensorToTheSystem(JSONObject json) {
		Sensor sensor = Sensor.getSensorFromJson(json); //Create the new Sensor object
		AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO().create(sensor);
		// TODO add to database
		sensors.add(sensor);
		
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
		System.out.println("Hey" + json);
		String verb = json.getString("verb");
		switch (verb) {
		case "post":
			registerSensorToTheSystem(json);
			break;
		default:
			break;
		}
	}
}

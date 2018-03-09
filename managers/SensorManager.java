package managers;


import java.util.ArrayList;

import org.json.JSONObject;

import models.EnvironmentVariable;
import models.Sensor;

public class SensorManager {
	
	ArrayList<Sensor> sensors;
	
	private static SensorManager manager = null;
	
	private SensorManager() {
		sensors = new ArrayList<Sensor>();
	}
	
	public static SensorManager getManager() {
		if(manager == null) 
			manager = new SensorManager();
		return manager;
	}
	
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
	
	public Sensor getSensorById(String id) {
		for (int i = 0; i < sensors.size(); i++) {
			if(sensors.get(i).getId().equals(id)) {
				return sensors.get(i);
			}
		}
		return null;
	}
}

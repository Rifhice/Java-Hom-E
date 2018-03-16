package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.models.Actuator;
import ocsf.server.ConnectionToClient;

public class ActuatorManager extends Manager {
	
	private ArrayList<Actuator> actuators;
	
	private static ActuatorManager manager = null;
	
	private ActuatorManager() {
		actuators = new ArrayList<Actuator>();
	}
	
	public static ActuatorManager getManager() {
		if(manager == null) 
			manager = new ActuatorManager();
		return manager;
	}
	
	public void registerActuatorToTheSystem(JSONObject json) {
		// TODO
	}
	
	public ArrayList<Actuator> getActuators(){
		return actuators;
	}
	
	public Actuator getActuatorById(String id) {
		for (int i = 0; i < actuators.size(); i++) {
			if(Integer.toString(actuators.get(i).getId()).equals(id)) {
				return actuators.get(i);
			}
		}
		return null;
	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		// TODO
	}
}

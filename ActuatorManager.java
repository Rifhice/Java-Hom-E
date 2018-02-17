

import java.util.ArrayList;

import org.json.JSONObject;

public class ActuatorManager {
	
	ArrayList<Actuator> actuators;
	
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
		actuators.add(Actuator.registerToTheSystem(json));
		System.out.println(actuators.get(actuators.size()-1) + "\nAdded to the system !");
	}
	
	public ArrayList<Actuator> getActuators(){
		return actuators;
	}
	
	public Actuator getActuatorById(String id) {
		for (int i = 0; i < actuators.size(); i++) {
			if(actuators.get(i).getId().equals(id)) {
				return actuators.get(i);
			}
		}
		return null;
	}
}

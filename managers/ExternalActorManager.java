package managers;


import java.util.ArrayList;

import org.json.JSONObject;

import models.Actuator;
import models.EnvironmentVariable;
import models.ExternalActor;
import models.Sensor;

public class ExternalActorManager {
	
	private SensorManager sensorManager;
	private ActuatorManager actuatorManager;
	
	private static ExternalActorManager manager = null;
	
	private ExternalActorManager() {
		sensorManager = SensorManager.getManager();
		actuatorManager = ActuatorManager.getManager();
	}
	
	public static ExternalActorManager getManager() {
		if(manager == null) 
			manager = new ExternalActorManager();
		return manager;
	}
	
	public ArrayList<Actuator> getActuators(){
		return actuatorManager.getActuators();
	}
	
	public ArrayList<Sensor> getSensors(){
		return sensorManager.getSensors();
	}
	
	public void handleMessage(JSONObject json) {
		String type = json.getString("type");
		String action = json.getString("action");
		
		boolean wasGeneral = false;
		switch (action) {
		case "setDescription":
			setDescription(json);
			wasGeneral = true;
			break;
		case "setName":
			setName(json);
			wasGeneral = true;
			break;
		case "disable":
			disableActor(json);
			wasGeneral = true;
			break;
		case "enable":
			enableActor(json);
			wasGeneral = true;
			break;
		default:
			break;
		}
		
		 if(!wasGeneral) {
			switch (type) {
			case "actuator":
				switch (action) {
				case "register":
					actuatorManager.registerActuatorToTheSystem(json);
					break;
	
				default:
					break;
				}
				break;
			case "sensor":
				switch (action) {
				case "register":
					sensorManager.registerSensorToTheSystem(json);
					break;
				case "valueChange":
					sensorManager.valueChange(json);
				default:
					break;
				}
				break;
			default:
				break;
			}
		 }
	}
	
	public ArrayList<EnvironmentVariable> getEnvironmentVariables(){
		return sensorManager.getEnvironmentVariables();
	}
	
	public void setDescription(JSONObject json) {
		ExternalActor actor = SensorManager.getManager().getSensorById(json.getString("id"));
		if(actor == null) {
			actor = ActuatorManager.getManager().getActuatorById(json.getString("id"));
		}
		if(actor != null) {
			actor.setDescription(json.getString("description"));
		}
	}
	
	public void setName(JSONObject json) {
		ExternalActor actor = SensorManager.getManager().getSensorById(json.getString("id"));
		if(actor == null) {
			actor = ActuatorManager.getManager().getActuatorById(json.getString("id"));
		}
		if(actor != null) {
			actor.setName(json.getString("name"));
			System.out.println(actor);
		}
	}
	
	public void disableActor(JSONObject json) {
		ExternalActor actor = SensorManager.getManager().getSensorById(json.getString("id"));
		if(actor == null) {
			actor = ActuatorManager.getManager().getActuatorById(json.getString("id"));
		}
		if(actor != null) {
			actor.disable();
		}
	}
	
	public void enableActor(JSONObject json) {
		ExternalActor actor = SensorManager.getManager().getSensorById(json.getString("id"));
		if(actor == null) {
			actor = ActuatorManager.getManager().getActuatorById(json.getString("id"));
		}
		if(actor != null) {
			actor.enable();
		}
	}
}

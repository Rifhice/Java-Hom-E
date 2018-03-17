package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.models.Behaviour;
import ocsf.server.ConnectionToClient;

public class SensorCategorieManager extends Manager{

	private static SensorCategorieManager manager = null;
	
	private SensorCategorieManager() {
		
	}
	
	public static SensorCategorieManager getManager() {
		if(manager == null) 
			manager = new SensorCategorieManager();
		return manager;
	}
	
	public void createSensorCategorie(JSONObject json) {
		
	}
	
	public void deleteSensorCategorie(Behaviour behaviour) {

	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {

	}
}

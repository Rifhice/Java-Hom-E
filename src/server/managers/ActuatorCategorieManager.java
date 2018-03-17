package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.models.Behaviour;
import ocsf.server.ConnectionToClient;

public class ActuatorCategorieManager extends Manager{

	private static ActuatorCategorieManager manager = null;
	
	private ActuatorCategorieManager() {
		
	}
	
	public static ActuatorCategorieManager getManager() {
		if(manager == null) 
			manager = new ActuatorCategorieManager();
		return manager;
	}
	
	public void createActuatorCategorie(JSONObject json) {
		
	}
	
	public void deleteActuatorCategorie(Behaviour behaviour) {

	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {

	}
}

package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import server.models.Ambience;
import server.models.Behaviour;
import ocsf.server.ConnectionToClient;

public class AmbienceManager extends Manager{

	private static AmbienceManager manager = null;
	
	private AmbienceManager() {
	}
	
	public static AmbienceManager getManager() {
		if(manager == null) 
			manager = new AmbienceManager();
		return manager;
	}
	
	
	public void getAllAmbience(JSONObject json, ConnectionToClient client){
		ArrayList<Ambience> ambiences = null;

		try {
			ambiences = AbstractDAOFactory.getFactory(SystemManager.db).getAmbienceDAO().getAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		result.put("recipient", "ambience");
		result.put("action", "getAll");
		for (int i = 0; i < ambiences.size(); i++) {
			JSONObject ambience = new JSONObject();
			ambience.put("id", ambiences.get(i).getId());
			ambience.put("name", ambiences.get(i).getName());
			result.append("ambiences", ambience);
		}
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void createAmbience(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void deleteAmbience(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void activateAmbience(JSONObject json, ConnectionToClient client) {

	}
	
	public void deactivateAmbience(JSONObject json, ConnectionToClient client) {

	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		// TODO Auto-generated method stub
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllAmbience(json,client);
	            break;
	        case "create":
	        	createAmbience(json,client);
	            break;
	        case "delete":
	        	deleteAmbience(json,client);
	            break;
	        case "update":
	            break;
        }
	}
}

package server.managers;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import ocsf.server.ConnectionToClient;
import server.factories.AbstractDAOFactory;
import server.models.Ambience;
import server.models.Behaviour;

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
			Ambience currentAmbience = ambiences.get(i);
			JSONObject ambience = new JSONObject();
			ambience.put("id", currentAmbience.getId());
			ambience.put("name", currentAmbience.getName());
			List<Behaviour> behaviours = currentAmbience.getBehaviours();
			try {
				for(int j = 0; j < behaviours.size(); j++) {
					Behaviour behaviour = behaviours.get(j);
					System.out.println(behaviour.getId());
					JSONObject behaviourJSON = new JSONObject();
					behaviourJSON.put("id", behaviour.getId());
					ambience.append("behaviours", behaviourJSON);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
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

package server.managers;


import java.util.ArrayList;

import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import server.models.Behaviour;
import server.models.categories.SensorCategory;
import ocsf.server.ConnectionToClient;

public class BehaviourManager extends Manager{

	private static BehaviourManager manager = null;
	private ArrayList<Behaviour> behaviours;
	
	private BehaviourManager() {
		behaviours = new ArrayList<Behaviour>();
	}
	
	public static BehaviourManager getManager() {
		if(manager == null) 
			manager = new BehaviourManager();
		return manager;
	}
	
	public ArrayList<Behaviour> getAllBehaviours(){
		return behaviours;	
	}
	
	public void createBehaviour(JSONObject json) {
		behaviours.add(Behaviour.createBehaviour(json));
		System.out.println(behaviours.get(behaviours.size()-1));
	}
	
	public void getAllBehaviours(JSONObject json, ConnectionToClient client) {
		ArrayList<Behaviour> behaviours = AbstractDAOFactory.getFactory(SystemManager.db).getBehaviourDAO().getAll();
		JSONObject result = new JSONObject();
		result.put("recipient", "behaviours");
		result.put("action", "getAll");
		for (int i = 0; i < behaviours.size(); i++) {
			JSONObject behaviour = new JSONObject();
			behaviour.put("id", behaviours.get(i).getId());
			behaviour.put("name", behaviours.get(i).getName());
			result.append("behaviours", behaviour);
		}
		try {
			client.sendToClient(result);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteBehaviours(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void createBehaviours(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void updateBehaviours(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void deleteBehaviour(Behaviour behaviour) {
		behaviours.remove(behaviour);
	}
	
	public void activateBehaviour(Behaviour behaviour) {
		behaviour.setActivated(true);
	}
	
	public void deactivateBehaviour(Behaviour behaviour) {
		behaviour.setActivated(false);
	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllBehaviours(json,client);
	            break;
	        case "create":
	        	createBehaviours(json,client);
	            break;
	        case "delete":
	        	deleteBehaviours(json,client);
	            break;
	        case "update":
	        	updateBehaviours(json,client);
	            break;
        }
	}
}

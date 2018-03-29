package server.managers;


import java.net.StandardSocketOptions;
import java.util.ArrayList;

import org.json.JSONObject;

import server.dao.abstractDAO.BehaviourDAO;
import server.factories.AbstractDAOFactory;
import server.models.Behaviour;
import ocsf.server.ConnectionToClient;

public class BehaviourManager extends Manager{

    private BehaviourDAO behaviourDAO = AbstractDAOFactory.getFactory(SystemManager.db).getBehaviourDAO();
	private static BehaviourManager manager = null;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    /**
     *  Singleton pattern
     */
	private BehaviourManager() {
	}
	
	public static BehaviourManager getManager() {
		if(manager == null) 
			manager = new BehaviourManager();
		return manager;
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //
	
	/**
     * Get all the behaviours in DB. Send to the client a JSONObject.    
     * @param client
     */
	public void getAllBehaviours(ConnectionToClient client) {
		ArrayList<Behaviour> behaviours = null;

		try {
			behaviours = behaviourDAO.getAll();
			System.out.println(behaviours);
		} catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		result.put("recipient", "behaviour");
		result.put("action", "getAll");
		JSONObject behaviour;
		for (int i = 0; i < behaviours.size(); i++) {
		    behaviour = new JSONObject();
			behaviour.put("id", behaviours.get(i).getId());
			behaviour.put("name", behaviours.get(i).getName());
			behaviour.put("description", behaviours.get(i).getDescription());
			behaviour.put("isActivated", behaviours.get(i).isActivated());
			
			try {
		         behaviour.put("expression", behaviours.get(i).getExpression().toJson());
			} catch (Exception e) {
			    e.printStackTrace();
			}
			
			result.append("behaviours", behaviour);
		}
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteBehaviours(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void createBehaviours(JSONObject json, ConnectionToClient client) {
		Behaviour behaviour = null;
		try {
			behaviour = Behaviour.createBehaviour(json); 
			AbstractDAOFactory.getFactory(SystemManager.db).getBehaviourDAO().create(behaviour);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		try {
			if(behaviour == null) {
				result.put("recipient", "behaviour");
				result.put("action", "create");
				result.put("result", "failure");
				System.out.println(result.toString());
				client.sendToClient(result.toString());
			}
			else {
				result.put("recipient", "behaviour");
				result.put("action", "create");
				result.put("result", "success");
				System.out.println(result.toString());
				SystemManager.sendToAllClient(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateBehaviours(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void deleteBehaviour(Behaviour behaviour) {
	}
	
	public void activateBehaviour(Behaviour behaviour) {
		behaviour.setActivated(true);
	}
	
	public void deactivateBehaviour(Behaviour behaviour) {
		behaviour.setActivated(false);
	}

	/**
     * Possible values for key "action":
     * <ul>
     * <li>getAll</li>
     * <li>create</li>
     * <li>delete</li>
     * <li>update</li>
     * </ul>
     */
	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllBehaviours(client);
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

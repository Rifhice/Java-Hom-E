package server.managers;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ocsf.server.ConnectionToClient;
import server.dao.abstractDAO.AmbienceDAO;
import server.dao.abstractDAO.BehaviourDAO;
import server.factories.AbstractDAOFactory;
import server.models.Ambience;
import server.models.Behaviour;

public class AmbienceManager extends Manager{

    private AmbienceDAO ambienceDAO = AbstractDAOFactory.getFactory(SystemManager.db).getAmbienceDAO();
	private static AmbienceManager manager = null;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    /**
     *  Singleton pattern
     */
	private AmbienceManager() {
	}
	
	public static AmbienceManager getManager() {
		if(manager == null) 
			manager = new AmbienceManager();
		return manager;
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= // 
	/**
	 * Get all the ambiences in DB. Send to the client a JSONObject.	
	 * @param json
	 * @param client
	 */
	public void getAllAmbiences(JSONObject json, ConnectionToClient client){
		ArrayList<Ambience> ambiences = null;

		try {
			ambiences = ambienceDAO.getAll();
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
	
	/**
	 * Create an Ambience in DB. Send to the client a JSONObject : 
	 * {
	 *     recipient: ambience,
	 *     action: create,     
	 *     result: success
	 *     ambience: ambience converted toJSON
	 * }
	 * In case of fail, result is set to failure and there is no key/value "ambience"	 * 
	 * @param ambience
	 * @param client
	 * @see AmbienceDAO#create(Ambience)
	 */
	public void createAmbience(Ambience ambience, ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "ambience");
		result.put("action", "create");
		try {
			if(ambienceDAO.create(ambience) == null) {
				result.put("result", "failure");
				client.sendToClient(result.toString());
			}
			else {
				result.put("ambience", ambience.toJSON());
				result.put("result", "success");
				SystemManager.sendToAllClient(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteAmbience(JSONObject json, ConnectionToClient client) {
		
	}
	
	public void activateAmbience(JSONObject json, ConnectionToClient client) {

	}
	
	public void deactivateAmbience(JSONObject json, ConnectionToClient client) {

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
		// TODO Auto-generated method stub
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllAmbiences(json,client);
	            break;
	        case "create":
	        	BehaviourDAO behaviourDAO = AbstractDAOFactory.getFactory(SystemManager.db).getBehaviourDAO();
	        	try {
	        		JSONArray behavioursJSON = json.getJSONArray("behaviours");
		        	List<Behaviour> behaviours = new ArrayList<Behaviour>();
		        	for(int i = 0; i < behavioursJSON.length(); i++) {
		        		behaviours.add(behaviourDAO.getById(behavioursJSON.getInt(i)));
		        	}
		        	Ambience ambience = new Ambience();
		        	ambience.setName(json.getString("name"));
		        	ambience.setBehaviours(behaviours);
		        	createAmbience(ambience ,client);
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        	
	            break;
	        case "delete":
	        	deleteAmbience(json,client);
	            break;
	        case "update":
	            break;
        }
	}
}

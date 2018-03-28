package server.managers;

import java.util.ArrayList;
import org.json.JSONObject;

import ocsf.server.ConnectionToClient;
import server.dao.abstractDAO.RightDAO;
import server.factories.AbstractDAOFactory;
import server.models.Right;
import server.models.User;


public class RightManager extends Manager {
	
	// ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<Right> rights;
    private RightDAO rightDAO = AbstractDAOFactory.getFactory(SystemManager.db).getRightDAO();
    private static RightManager manager = null;
    
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    /**
     *  Singleton pattern
     */
    private RightManager() {
        
    }
    
    public static RightManager getManager() {
        if(manager == null) 
            manager = new RightManager();
        return manager;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    /**
     * Get an user in DB by his id.
     * @param json
     * @param client
     */
    public void getByUser(JSONObject json, ConnectionToClient client) {
    	ArrayList<Right> rights = null;
    	int id = json.getInt("id");
    	User user = new User();
		try {
			user = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO().getById(id);
			rights = rightDAO.getByUser(user);
		} catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		result.put("recipient", "right");
		result.put("action", "getByUser");
		result.put("rights", rights);
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public void getAll(JSONObject json, ConnectionToClient client) {
    	ArrayList<Right> rights = null;
		try {
			rights = rightDAO.getAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		result.put("recipient", "right");
		result.put("action", "getAll");
		result.put("rights", rights);
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
		
    /**
     * Possible values for key "action":
     * <ul>
     * <li>getByUser</li>
     * <li>getAll</li>
     * </ul>
     */
	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        switch(action) {
	        case "getByUser":
	            getByUser(json,client);
	            break;
	        case "getAll":  
	        	getAll(json,client);
	        	break;
        }
    }

}

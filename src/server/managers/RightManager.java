package server.managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import javafx.util.Pair;
import ocsf.server.ConnectionToClient;
import server.dao.abstractDAO.RightDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Right;
import server.models.User;
import server.tools.Security;


public class RightManager extends Manager {
	
	// ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<Right> rights;
    private RightDAO rightDAO = AbstractDAOFactory.getFactory(SystemManager.db).getRightDAO();
    private static RightManager manager = null;
    
    // ================= //
    // ==== METHODS ==== //
    // ================= // 
    
  
    
    public void getByUser(JSONObject json, ConnectionToClient client) {
    	ArrayList<Right> rights = null;
    	int id = json.getInt("id");
    	User user = new User();
		try {
			user = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO().getById(id);
			rights = AbstractDAOFactory.getFactory(SystemManager.db).getRightDAO().getByUser(user);
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
			rights = AbstractDAOFactory.getFactory(SystemManager.db).getRightDAO().getAll();
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
		
		

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        System.out.println(action);
        String user;
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
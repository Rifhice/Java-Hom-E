package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import javafx.util.Pair;
import server.dao.abstractDAO.ActuatorCategoriesDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Behaviour;
import server.models.User;
import server.models.categories.ActuatorCategory;
import server.models.categories.SensorCategory;
import server.tools.Security;
import ocsf.server.ConnectionToClient;

public class ActuatorCategorieManager extends Manager{

	private static ActuatorCategorieManager manager = null;
	private ActuatorCategoriesDAO actuatorCategoriesDAO = AbstractDAOFactory.getFactory(SystemManager.db).getActuatorCategoriesDAO();
	
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
	
	public void getAllActuatorCategorie(ConnectionToClient client) {
		ArrayList<ActuatorCategory> actuatorCategories = actuatorCategoriesDAO.getAll();
		JSONObject result = new JSONObject();
		result.put("recipient", "actuatorCategories");
		result.put("action", "getAll");
		for (int i = 0; i < actuatorCategories.size(); i++) {
			JSONObject currentCat = new JSONObject();
			currentCat.put("id", actuatorCategories.get(i).getId());
			currentCat.put("name", actuatorCategories.get(i).getName());
			currentCat.put("description", actuatorCategories.get(i).getDescription());
			result.append("categories", currentCat);
		}
		System.out.println(result.toString());
		try {
			client.sendToClient(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllActuatorCategorie(client);
	            break;
        }
	}
}

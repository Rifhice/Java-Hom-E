package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import server.dao.abstractDAO.ActuatorCategoriesDAO;
import server.factories.AbstractDAOFactory;
import server.models.categories.ActuatorCategory;
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
		try {
			client.sendToClient(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createActuatorCategorie(ActuatorCategory obj, ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "actuatorCategories");
		result.put("action", "create");
		if(actuatorCategoriesDAO.create(obj) == null) {
			result.put("result", "failure");
		}
		else {
			result.put("result", "success");
		}
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteActuatorCategorie(int id,ConnectionToClient client) {
		System.out.println("delete");
		JSONObject result = new JSONObject();
		result.put("recipient", "actuatorCategories");
		result.put("action", "delete");
		if(actuatorCategoriesDAO.delete(id) == 0) {
			result.put("result", "failure");
		}
		else {
			result.put("result", "success");
		}
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateActuatorCategorie(ActuatorCategory obj,ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "actuatorCategories");
		result.put("action", "update");
		if(!actuatorCategoriesDAO.update(obj)) {
			System.out.println("hey");
			result.put("result", "failure");
		}
		else {
			result.put("result", "success");
		}
		try {
			client.sendToClient(result.toString());
		} catch (Exception e) {
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
	        case "create":
	        	createActuatorCategorie(new ActuatorCategory(json.getString("name"),json.getString("description")),client);
	            break;
	        case "delete":
	        	deleteActuatorCategorie(json.getInt("id"),client);
	            break;
	        case "update":
	        	updateActuatorCategorie(new ActuatorCategory(json.getInt("id"),json.getString("name"),json.getString("description")),client);
	            break;
        }
	}
}

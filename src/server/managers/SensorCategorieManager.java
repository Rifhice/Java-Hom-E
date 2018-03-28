package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import server.dao.abstractDAO.SensorCategoriesDAO;
import server.factories.AbstractDAOFactory;
import server.models.categories.SensorCategory;
import ocsf.server.ConnectionToClient;

public class SensorCategorieManager extends Manager{
    
    private SensorCategoriesDAO sensorCategoriesDAO = AbstractDAOFactory.getFactory(SystemManager.db).getSensorCategoriesDAO();
	private static SensorCategorieManager manager = null;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //    
    /**
     *  Singleton pattern
     */
	private SensorCategorieManager() {
		
	}
	
	public static SensorCategorieManager getManager() {
		if(manager == null) 
			manager = new SensorCategorieManager();
		return manager;
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= // 
	public void createSensorCategorie(SensorCategory obj, ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "sensorCategories");
		result.put("action", "create");
		try {
			if(sensorCategoriesDAO.create(obj) == null) {
				result.put("result", "failure");
				client.sendToClient(result.toString());
			}
			else {
				result.put("result", "success");
				SystemManager.sendToAllClient(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteSensorCategorie(int id,ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "sensorCategories");
		result.put("action", "delete");
		try {
			if(sensorCategoriesDAO.delete(id) == 0) {
				result.put("result", "failure");
				client.sendToClient(result.toString());
			}
			else {
				result.put("result", "success");
				SystemManager.sendToAllClient(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void updateSensorCategorie(SensorCategory obj, ConnectionToClient client) {
		JSONObject result = new JSONObject();
		result.put("recipient", "sensorCategories");
		result.put("action", "update");
		try {
			if(sensorCategoriesDAO.update(obj) < 1) {
				result.put("result", "failure");
				client.sendToClient(result.toString());
			}
			else {
				result.put("result", "success");
				SystemManager.sendToAllClient(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			

	}
	
	public void getAllSensorCategorie(ConnectionToClient client) {
		ArrayList<SensorCategory> sensorCategories = sensorCategoriesDAO.getAll();
		JSONObject result = new JSONObject();
		result.put("recipient", "sensorCategories");
		result.put("action", "getAll");
		for (int i = 0; i < sensorCategories.size(); i++) {
			JSONObject currentCat = new JSONObject();
			currentCat.put("id", sensorCategories.get(i).getId());
			currentCat.put("name", sensorCategories.get(i).getName());
			currentCat.put("description", sensorCategories.get(i).getDescription());
			result.append("categories", currentCat);
		}
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
	        	getAllSensorCategorie(client);
	            break;
	        case "create":
	        	createSensorCategorie(new SensorCategory(json.getString("name"),json.getString("description")),client);
	            break;
	        case "delete":
	        	deleteSensorCategorie(json.getInt("id"),client);
	            break;
	        case "update":
	        	updateSensorCategorie(new SensorCategory(json.getInt("id"),json.getString("name"),json.getString("description")),client);
	            break;
        }
	}
}

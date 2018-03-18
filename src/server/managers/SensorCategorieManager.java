package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import server.dao.abstractDAO.ActuatorCategoriesDAO;
import server.dao.abstractDAO.SensorCategoriesDAO;
import server.factories.AbstractDAOFactory;
import server.models.Behaviour;
import server.models.categories.SensorCategory;
import ocsf.server.ConnectionToClient;

public class SensorCategorieManager extends Manager{

	private static SensorCategorieManager manager = null;
	private SensorCategoriesDAO sensorCategoriesDAO = AbstractDAOFactory.getFactory(SystemManager.db).getSensorCategoriesDAO();
	
	private SensorCategorieManager() {
		
	}
	
	public static SensorCategorieManager getManager() {
		if(manager == null) 
			manager = new SensorCategorieManager();
		return manager;
	}
	
	public void createSensorCategorie(JSONObject json) {
		
	}
	
	public void deleteSensorCategorie(Behaviour behaviour) {

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
	        	getAllSensorCategorie(client);
	            break;
        }
	}
}

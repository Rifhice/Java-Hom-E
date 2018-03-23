package server.managers;


import org.json.JSONObject;

import server.models.Behaviour;
import server.models.categories.SensorCategory;
import ocsf.server.ConnectionToClient;

public class CommandManager extends Manager{

	private static CommandManager manager = null;
	
	private CommandManager() {
		
	}
	
	public static CommandManager getManager() {
		if(manager == null) 
			manager = new CommandManager();
		return manager;
	}
	
	public void createComplexCommand(JSONObject json,ConnectionToClient client) {
		
	}
	
	public void deleteComplexCommand(JSONObject json,ConnectionToClient client) {

	}
	
	public void updateComplexCommand(JSONObject json,ConnectionToClient client) {
		
	}
	
	public void getAllAtomicCommand(JSONObject json,ConnectionToClient client) {

	}
	
	public void getAllComplexCommand(JSONObject json,ConnectionToClient client) {

	}
	
	public void getAllCommand(JSONObject json,ConnectionToClient client) {

	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
        switch(action) {
	        case "getAll":
	        	getAllCommand(json,client);
	            break;
	        case "getAllComplex":
	        	getAllComplexCommand(json,client);
	            break;
	        case "getAllAtomic":
	        	getAllAtomicCommand(json,client);
	            break;
	        case "create":
	        	createComplexCommand(json,client);
	            break;
	        case "delete":
	        	deleteComplexCommand(json,client);
	            break;
	        case "update":
	        	updateComplexCommand(json,client);
	            break;
        }
	}
}

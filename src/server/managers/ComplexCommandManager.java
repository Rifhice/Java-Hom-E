package server.managers;


import org.json.JSONObject;

import server.models.Behaviour;
import ocsf.server.ConnectionToClient;

public class ComplexCommandManager extends Manager{

	private static ComplexCommandManager manager = null;
	
	private ComplexCommandManager() {
		
	}
	
	public static ComplexCommandManager getManager() {
		if(manager == null) 
			manager = new ComplexCommandManager();
		return manager;
	}
	
	public void createComplexCommand(JSONObject json) {
		
	}
	
	public void deleteComplexCommand(Behaviour behaviour) {

	}

	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {

	}
}

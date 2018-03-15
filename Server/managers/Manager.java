package managers;

import org.json.JSONObject;

import ocsf.serverConnection.ConnectionToClient;

public abstract class Manager {
	
	public abstract void handleMessage(JSONObject json,ConnectionToClient client);
	
}

package managers;

import org.json.JSONObject;

import ocsf.server.ConnectionToClient;

public abstract class Manager {
	
	public abstract void handleMessage(JSONObject json,ConnectionToClient client);
	
}

package server.managers;

import org.json.JSONObject;

import ocsf.server.ConnectionToClient;

/**
 * A manager deals with the entitys he manages. There is only one Manager by entity (singleton pattern).
 * @author Clm-Roig
 *
 */
public abstract class Manager {
	
	public abstract void handleMessage(JSONObject json,ConnectionToClient client);
	
}

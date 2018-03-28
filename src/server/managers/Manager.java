package server.managers;

import org.json.JSONObject;

import ocsf.server.ConnectionToClient;

/**
 * A manager deals with the entities he manages. There is only one Manager by entity (singleton pattern).
 * @author Clm-Roig
 *
 */
public abstract class Manager {
	
    /**
     * Parses the JSON object received by a client and deals with it if the "action" called is 
     * recognized by the manager.
     * @param json
     * @param client
     */
	public abstract void handleMessage(JSONObject json,ConnectionToClient client);
	
}

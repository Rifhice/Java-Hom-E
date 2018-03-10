package managers;

import org.json.JSONObject;

public abstract class Manager {
	
	public abstract void handleMessage(JSONObject json);
	
}

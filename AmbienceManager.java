

import java.util.ArrayList;

import org.json.JSONObject;

public class AmbienceManager {

	private static AmbienceManager manager = null;
	private ArrayList<Ambience> ambiences;
	
	private AmbienceManager() {
		ambiences = new ArrayList<Ambience>();
	}
	
	public static AmbienceManager getManager() {
		if(manager == null) 
			manager = new AmbienceManager();
		return manager;
	}
	
	
	public ArrayList<Ambience> getAllAmbience(){
		return ambiences;	
	}
	
	public void createAmbience(JSONObject json) {
		
	}
	
	public void deleteAmbience(JSONObject json) {
		
	}
	
	public void activateAmbience(JSONObject json) {

	}
	
	public void deactivateAmbience(JSONObject json) {

	}
}

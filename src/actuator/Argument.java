package actuator;

import org.json.JSONObject;

public abstract class Argument {


	protected String name;
	
	public Argument(String name) {
		this.name = name;
	}

	public abstract JSONObject getJson(); 
}

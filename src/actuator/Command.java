package actuator;

import java.util.ArrayList;

import org.json.JSONObject;

public class Command {

	private String name;
	private String description;
	private String key;
	private ArrayList<Argument> arguments;
	
	public Command(String name,String description, String key, ArrayList<Argument> arguments) {
		this.name = name;
		this.description = description;
		this.key = key;
		this.arguments = arguments;
	}
	
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("name", name);
		result.put("description", description);
		result.put("key", key);
		for (int i = 0; i < arguments.size(); i++) {
			result.append("args", arguments.get(i).getJson());
		}
		return result;
	}
	
}

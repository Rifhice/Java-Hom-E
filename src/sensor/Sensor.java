package sensor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

public class Sensor {

	private static int id;
	private String name;
	private String description;
	private EnvironmentVariable variables;
	private static SensorClient client;	
	
	public Sensor(SensorClient client, String name, String description, EnvironmentVariable variables) {
		this.name = name;
		this.description = description;
		this.variables = variables;
		this.client = client;
		registerToSystem();
	}
	
	public void registerToSystem() {
		JSONObject registration = new JSONObject();
		registration.put("recipient", "sensor");
		registration.put("action", "post");
		registration.put("id", "null");
		registration.put("name", name);
		registration.put("description", description);
		registration.put("variables", variables.toJson());
		System.out.println(registration.toString());
		try {
			client.sendToServer(registration.toString());
		} catch (IOException e) {
			System.out.println(registration.toString());
			e.printStackTrace();
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public EnvironmentVariable getEnvironmentVariables(){
		return variables;
	}
	
	public static void updateVariable(EnvironmentVariable variable) {
		JSONObject json = new JSONObject();
		json.put("recipient", "sensor");
		json.put("action", "changeValue");
		json.put("id", id);
		String value = "";
		if(variable.getValue() instanceof ContinuousValue) {
			value = ((ContinuousValue) variable.getValue()).getCurrentValue() + "";
		}
		else {
			value = ((DiscreteValue) variable.getValue()).getCurrentValue() + "";
		}
		json.put("idVariable", variable.getId());
		json.put("value", value);
		try {
			client.sendToServer(json.toString());
		} catch (IOException e) {
			System.out.println(json.toString());
			e.printStackTrace();
		}
	}
	
}

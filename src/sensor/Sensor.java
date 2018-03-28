package sensor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.message.ClientFinalMessage;

import server.models.environmentVariable.EnvironmentVariable;

public class Sensor {

	private static int id;
	private String name;
	private String description;
	private ArrayList<Variable> variables;
	private static SensorClient client;	
	
	public Sensor(SensorClient client, String name, String description, ArrayList<Variable> variables) {
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
		for (int i = 0; i < variables.size(); i++) {
			registration.append("variables", variables.get(i).getJson());
		}
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
	
	public ArrayList<Variable> getEnvironmentVariables(){
		return variables;
	}
	
	public static void updateVariable(Variable variable) {
		JSONObject json = new JSONObject();
		json.put("recipient", "sensor");
		json.put("action", "changeValue");
		json.put("id", id);
		String value = "";
		if(variable instanceof ContinuousValue) {
			value = ((ContinuousValue) variable).getCurrentValue() + "";
		}
		else {
			value = ((DiscreteValue) variable).getCurrentValue() + "";
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

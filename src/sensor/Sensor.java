package sensor;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.message.ClientFinalMessage;

public class Sensor {

	private static String name;
	private static String description;
	private static ArrayList<Variable> variables;
	private static SensorClient client;	
	
	public Sensor(SensorClient client, String name, String description, ArrayList<Variable> variables) {
		this.name = name;
		this.description = description;
		this.variables = variables;
		this.client = client;
		registerToSystem();
	}
	
	public static void registerToSystem() {
		JSONObject registration = new JSONObject();
		registration.put("recipient", "sensor");
		registration.put("verb", "post");
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
	
	public static void updateVariable(Variable variable) {
		
	}
	
}

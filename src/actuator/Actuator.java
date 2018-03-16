package actuator;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.message.ClientFinalMessage;

public class Actuator {

	private static String name;
	private static String description;
	private static ArrayList<Command> commands;
	private static ActuatorClient client;	
	
	public Actuator(ActuatorClient client, String name, String description, ArrayList<Command> commands) {
		this.name = name;
		this.description = description;
		this.commands = commands;
		this.client = client;
		registerToSystem();
	}
	
	public static void registerToSystem() {
		JSONObject registration = new JSONObject();
		registration.put("recipient", "actuator");
		registration.put("verb", "post");
		registration.put("id", "null");
		registration.put("name", name);
		registration.put("description", description);
		for (int i = 0; i < commands.size(); i++) {
			registration.append("commands", commands.get(i).getJson());
		}
		try {
			client.sendToServer(registration.toString());
		} catch (IOException e) {
			System.out.println(registration.toString());
			e.printStackTrace();
		}
	}
	
}

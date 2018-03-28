package server.managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.ActuatorDAO;
import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.Command;
import server.models.commandValue.CommandValue;
import server.models.commandValue.ContinuousCommandValue;
import server.models.commandValue.DiscreteCommandValue;
import ocsf.server.ConnectionToClient;

public class ActuatorManager extends Manager {
	
    private ActuatorDAO actuatorDAO = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getActuatorDAO();
	private ArrayList<Actuator> actuators;
	
	private static ActuatorManager manager = null;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	/**
	 *  Singleton pattern
	 */
	private ActuatorManager() {
		actuators = new ArrayList<Actuator>();
	}
	
	public static ActuatorManager getManager() {
		if(manager == null) 
			manager = new ActuatorManager();
		return manager;
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //	
	public void registerActuatorToTheSystem(JSONObject json) {
		// TODO
	}
	
	public ArrayList<Actuator> getActuators(){
		return actuators;
	}
	
	public Actuator getActuatorById(String id) {
		for (int i = 0; i < actuators.size(); i++) {
			if(Integer.toString(actuators.get(i).getId()).equals(id)) {
				return actuators.get(i);
			}
		}
		return null;
	}

	public static Actuator getActuatorFromJson(JSONObject jsonToParse) {
		try {
			String name = jsonToParse.getString("name");
			String description = jsonToParse.getString("description");
			ArrayList<Command> commands = new ArrayList<Command>();
			JSONArray arr = jsonToParse.getJSONArray("commands");
			
			for (int i = 0; i < arr.length(); i++){
				
				JSONObject object = arr.getJSONObject(i);
				String nameCommand = object.getString("name");
				String descriptionCommand = object.getString("description");
				String keyCommand = object.getString("key");
				
				JSONArray arrArg = object.getJSONArray("args");
				ArrayList<CommandValue> arguments = new ArrayList<CommandValue>();
				for (int j = 0; j < arrArg.length(); j++){
					
					JSONObject current = arrArg.getJSONObject(j);
					System.out.println(current.toString());
					if(current.getString("type").equals("continuous")) {
						arguments.add(new ContinuousCommandValue(current.getString("name"), current.getDouble("valuemin"), current.getDouble("valuemax"), current.getDouble("precision")));
					}
					else if(current.getString("type").equals("discrete")){
						ArrayList<String> values = new ArrayList<String>();
						JSONArray valuesArray = current.getJSONArray("values");
						for (int z = 0; z < valuesArray.length(); z++) {
							values.add(valuesArray.getString(z));
						}
						arguments.add(new DiscreteCommandValue(current.getString("name"), values));
					}
				}
				commands.add(new Command(nameCommand, descriptionCommand, keyCommand, arguments));
			}
			return new Actuator(name, description, commands);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void registerActuatorToTheSystem(JSONObject json,ConnectionToClient client) {
		Actuator actuator = getActuatorFromJson(json);
		Actuator create = null;
		try {
		 create = actuatorDAO.create(actuator);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject result = new JSONObject();
		System.out.println(result);
		if(create != null) {
			create.setConnectionToClient(client);
			actuators.add(actuator);
			result.put("result", "success");
			result.put("verb", "post");
			result.put("id", create.getId());
		}
		else {
			result.put("result", "failure");
		}
		try {
			System.out.println(result.toString());
			client.sendToClient(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(actuator + "\nAdded to the system !");
	}
	
	public void execute(JSONObject json,ConnectionToClient client) {
		System.out.println(json.getString("executable"));
		for (int i = 0; i < actuators.size(); i++) {
			if(actuators.get(i).getId() == json.getInt("id")) {
				actuators.get(i).execute(json.getString("executable"));
			}
		}
	}
	
	/**
     * Possible values for key "action":
     * <ul>
     * <li>post</li>
     * <li>execute</li>
     * </ul>
     */
	@Override
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String verb = json.getString("verb");
		switch (verb) {
		case "post":
			registerActuatorToTheSystem(json,client);
			break;
		case "execute":
			execute(json,client);
			break;
		default:
			break;
		}
	}
}

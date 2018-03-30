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
	private static ArrayList<Actuator> actuators = new ArrayList<Actuator>();
	
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
	
	/**
	 * 
	 * @return The list of the different actuators in the system
	 */
	public static ArrayList<Actuator> getActuators(){
		return actuators;
	}
	
	/**
	 * 
	 * @param id Actuator id to get
	 * @return Actuator
	 */
	public Actuator getActuatorById(String id) {
		for (int i = 0; i < actuators.size(); i++) {
			if(Integer.toString(actuators.get(i).getId()).equals(id)) {
				return actuators.get(i);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param jsonToParse
	 * @return Actuator from the JSON
	 */
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
	
	/**
	 * 
	 * @param json Json to parse to register the actuator
	 * @param client Connection to the client
	 */
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
		if(create != null) {
			create.setConnectionToClient(client);
			actuators.add(actuator);
			result.put("result", "success");
			result.put("verb", "post");
			result.put("id", create.getId());
			SystemManager.sendToAllClient(result.toString());
			System.out.println("Actuator #" + actuator.getId() + " registered");
		}
		else {
			result.put("result", "failure");
			try {
				client.sendToClient(result.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(actuator + "\nAdded to the system !");
	}
	
	/**
	 * 
	 * @param json Execute the executable in this json
	 * @param client Connection to the client
	 */
	public void execute(JSONObject json,ConnectionToClient client) {
		for (int i = 0; i < actuators.size(); i++) {
			if(actuators.get(i).getId() == json.getInt("id")) {
				actuators.get(i).execute(json.getString("executable"));
				System.out.println("Executing : '" + json.getString("executable") + "' on actuator #" + actuators.get(i).getId());
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

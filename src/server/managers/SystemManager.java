package server.managers;

import java.io.IOException;

import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import ocsf.server.ConnectionToClient;
import server.server.EchoServer;

public class SystemManager extends Manager{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private final int USER_SERVER_PORT = 1111;
	private final int SENSOR_SERVER_PORT = 1112;
	private final int ACTUATOR_SERVER_PORT = 1113;
	
	private static SystemManager manager = null;
	
	private AmbienceManager ambienceManager;
	private BehaviourManager behaviourManager;
	private UserManager userManager;
	private SensorManager sensorManager;
	private ActuatorManager actuatorManager;
	private ActuatorCategorieManager actuatorCategorieManager;
	private SensorCategorieManager sensorCategorieManager;
	private CommandManager commandManager;
	private RightManager rightManager;
	
	private static EchoServer userServer;
	private static EchoServer sensorServer;
	private static EchoServer actuatorServer;
	
	// Currently used DB = SQLite
	public static final int db = AbstractDAOFactory.SQLITE_DAO_FACTORY;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    /**
     *  Singleton pattern
     */
	private SystemManager() {
		ambienceManager = AmbienceManager.getManager();
		behaviourManager = BehaviourManager.getManager();
		sensorManager = SensorManager.getManager();
		actuatorManager = ActuatorManager.getManager();
		userManager = UserManager.getManager();
		actuatorCategorieManager = ActuatorCategorieManager.getManager();
		sensorCategorieManager = SensorCategorieManager.getManager();
		commandManager = CommandManager.getManager();
		rightManager = RightManager.getManager();
		
		userServer = new EchoServer(USER_SERVER_PORT,this);
		sensorServer = new EchoServer(SENSOR_SERVER_PORT,sensorManager);
		actuatorServer = new EchoServer(ACTUATOR_SERVER_PORT,actuatorManager);
		try {
			userServer.listen();
			sensorServer.listen();
			actuatorServer.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ================= //
	// ==== METHODS ==== //
	// ================= //	
	/**
	 * Return the SystemManager (singleton pattern)
	 * @return SystemManager
	 */
	public static SystemManager getManager() {
		if(manager == null) 
			manager = new SystemManager();
		return manager;
	}
	
	/**
     * Dispatch the JSON received to the specified manager in key "recipient".
     * Possible values for key "recipient":
     * <ul>
     * <li>ambience</li>
     * <li>behaviour</li>
     * <li>sensor</li>
     * <li>actuator</li>
     * <li>user</li>
     * <li>sensorCategories</li>
     * <li>actuatorCategories</li>
     * <li>command</li>
     * <li>right</li>
     * </ul>
     */
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String recipient = json.getString("recipient");
		switch (recipient) {
		case "actuator":
            actuatorManager.handleMessage(json,client);
            break;
		case "actuatorCategories":
            actuatorCategorieManager.handleMessage(json,client);
            break;
		case "ambience":
			ambienceManager.handleMessage(json,client);
			break;
		case "behaviour":
			behaviourManager.handleMessage(json,client);
			break;
	     case "command":
            commandManager.handleMessage(json,client);
            break;
        case "right":
            rightManager.handleMessage(json,client);
            break;
		case "sensor":
			sensorManager.handleMessage(json,client);
			break;
		case "sensorCategories":
            sensorCategorieManager.handleMessage(json,client);
            break;
		case "user":
			userManager.handleMessage(json,client);
			break;		
		default:
			System.out.println("System manager: Manager NOT FOUND.");
			break;
		}
	}
	
	public static void sendToAllClient(String message) {
		userServer.sendToAllClients(message);
	}
	
	// ============== //
	// ==== MAIN ==== //
	// ============== //
	public static void main(String[] args) {
		new SystemManager();
	}
}

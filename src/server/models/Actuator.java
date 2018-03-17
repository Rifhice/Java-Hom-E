package server.models;

import java.util.ArrayList;

/**
 * An actuator is a connected object managed by the server and performing physical actions according to it. 
 * @author Clm-Roig
 */
public class Actuator extends ExternalActor{

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private ArrayList<Command> commands;

    // Attributes from others tables
    private int actuatorCategoryId;
    private String actuatorCategoryName;
    private String actuatorCategoryDescription;
    
    private ArrayList<AtomicAction> atomicActions;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Actuator() {}
    
    public Actuator(String name, String description) {
        super(name,description);
    }

    public Actuator(int id, String name, String description) {
        super(id,name,description);
    }

    public Actuator(String name, String description,ArrayList<Command> commands) {
        super(name,description);
        this.commands = commands;
    }

    
    public Actuator(int id, String name, String description,ArrayList<Command> commands) {
        super(id,name,description);
        this.commands = commands;
    }

    public Actuator(
        int id, String name, String description, ArrayList<Command> commands, 
        int actuatorCategoryId, String actuatorCategoryName, String actuatorCategoryDescription
    ) {
        super(id,name,description);
        this.commands = commands;
        this.actuatorCategoryId = actuatorCategoryId;
        this.actuatorCategoryName = actuatorCategoryName;
        this.actuatorCategoryDescription = actuatorCategoryDescription;
    }
    
    public Actuator(
        int id, String name, String description,ArrayList<Command> commands, 
        int actuatorCategoryId, String actuatorCategoryName, String actuatorCategoryDescription, 
        ArrayList<AtomicAction> atomicActions
    ) {
        super(id,name,description);
        this.commands = commands;
        this.actuatorCategoryId = actuatorCategoryId;
        this.actuatorCategoryName = actuatorCategoryName;
        this.actuatorCategoryDescription = actuatorCategoryDescription;
        this.atomicActions = atomicActions;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public int getActuatorCategoryId() {
        return actuatorCategoryId;
    }

    public void setActuatorCategoryId(int actuatorCategoryId) {
        this.actuatorCategoryId = actuatorCategoryId;
    }

    public String getActuatorCategoryName() {
        return actuatorCategoryName;
    }

    public void setActuatorCategoryName(String actuatorCategoryName) {
        this.actuatorCategoryName = actuatorCategoryName;
    }

    public String getActuatorCategoryDescription() {
        return actuatorCategoryDescription;
    }

    public void setActuatorCategoryDescription(String actuatorCategoryDescription) {
        this.actuatorCategoryDescription = actuatorCategoryDescription;
    }

    public ArrayList<AtomicAction> getAtomicActions() {
        return atomicActions;
    }

    public void setAtomicActions(ArrayList<AtomicAction> atomicActions) {
        this.atomicActions = atomicActions;
    }
    
    public String toString() {
    	String res = "ACTUATOR\nName " + name + "\nDescription : " + description + "\n\nCOMMANDS\n";
    	for (int i = 0; i < commands.size(); i++) {
			res += commands.get(i).toString() + "\n";
		}
    	return res;
    }
    
    // ==================================

}

package server.models;

import java.util.ArrayList;

import server.models.categories.ActuatorCategory;

/**
 * An actuator is a connected object managed by the server and performing physical actions according to it. 
 * @author Clm-Roig
 */
public class Actuator extends ExternalActor{

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    
    // Attributes from others tables
    private ArrayList<Command> commands;    
    private ActuatorCategory actuatorCategory;
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
        ActuatorCategory actuatorCategory
    ) {
        super(id,name,description);
        this.commands = commands;
        this.actuatorCategory = actuatorCategory;
    }
    
    public Actuator(
        int id, String name, String description,ArrayList<Command> commands, 
        ActuatorCategory actuatorCategory, ArrayList<AtomicAction> atomicActions
    ) {
        super(id,name,description);
        this.commands = commands;
        this.actuatorCategory = actuatorCategory;
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

    public ActuatorCategory getActuatorCategory() {
        return actuatorCategory;
    }

    public void setActuatorCategory(ActuatorCategory actuatorCategory) {
        this.actuatorCategory = actuatorCategory;
    }

    public ArrayList<AtomicAction> getAtomicActions() {
        return atomicActions;
    }

    public void setAtomicActions(ArrayList<AtomicAction> atomicActions) {
        this.atomicActions = atomicActions;
    }
    
    public String toString() {
    	String res = "ACTUATOR #" + id + " "+ name;
    	if(actuatorCategory != null) {
    	    res += "\nCat: "+ actuatorCategory.getName();
    	}       
    	res += "\n" + description + "\nCOMMANDS\n";
    	if(commands != null) {
        	for (Command command : commands) {
                res += command + "\n";
            }
    	}
    	return res;
    }
    
    // ==================================

}

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
    private int actuator_category_id;
    private String actuator_category_name;
    private String actuator_category_description;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Actuator() {

    }

    public Actuator(String name, String description) {
        super(name,description);
    }

    public Actuator(String name, String description,ArrayList<Command> commands) {
        super(name,description);
        this.commands = commands;
    }

    public Actuator(String name, String description,ArrayList<Command> commands, int actuator_category_id, String actuator_category_name, String actuator_category_description) {
        super(name,description);
        this.commands = commands;
        this.actuator_category_id = actuator_category_id;
        this.actuator_category_name = actuator_category_name;
        this.actuator_category_description = actuator_category_description;
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

    public int getActuator_category_id() {
        return actuator_category_id;
    }

    public void setActuator_category_id(int actuator_category_id) {
        this.actuator_category_id = actuator_category_id;
    }

    public String getActuator_category_name() {
        return actuator_category_name;
    }

    public void setActuator_category_name(String actuator_category_name) {
        this.actuator_category_name = actuator_category_name;
    }

    public String getActuator_category_description() {
        return actuator_category_description;
    }

    public void setActuator_category_description(String actuator_category_description) {
        this.actuator_category_description = actuator_category_description;
    }
    
    // ==================================

    public static Actuator registerToTheSystem(Object jsonToParse) {
        return null;
    }

}

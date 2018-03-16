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

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Actuator() {}

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
        this.actuatorCategoryId = actuator_category_id;
        this.actuatorCategoryName = actuator_category_name;
        this.actuatorCategoryDescription = actuator_category_description;
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
    
    // ==================================

    public static Actuator registerToTheSystem(Object jsonToParse) {
        return null;
    }

}

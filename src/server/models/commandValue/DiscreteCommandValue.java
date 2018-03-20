package server.models.argument;

import java.util.ArrayList;

import org.json.JSONObject;

public class DiscreteArgument extends Argument {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DiscreteArgument(String name, String description, ArrayList<String> values) {
        super(name,description);
        this.possibleValues = values;
    }
	
    public String toString() {
    	return "DISCRETE ARGUMENT \nName : " + name + "\nDescription : " + description + "\n values " + possibleValues; 
    }
    
}

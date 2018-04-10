package server.models.commandValue;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A discrete value is a variable which the current value is a String 
 * among possible values.
 * @author Clm-Roig
 */
public class DiscreteCommandValue extends CommandValue {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    
    public DiscreteCommandValue() {}
    
    public DiscreteCommandValue(ArrayList<String> values) {
        super();
        this.possibleValues = values;   
    }
    
    public DiscreteCommandValue(String name, ArrayList<String> values) {
        super(name);
        this.possibleValues = values;   
    }
    
    public DiscreteCommandValue(int id, String name, ArrayList<String> values) {
        super(id, name);
        this.possibleValues = values;   
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //     
    public ArrayList<String> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(ArrayList<String> values) {
        this.possibleValues = values;
    }
    
    // ==================================
    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	result.put("type", "discrete");
    	JSONArray array = new JSONArray(possibleValues);
    	result.put("possibleValues", array);
    	return result;
    }
}

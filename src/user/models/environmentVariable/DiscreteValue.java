package user.models.environmentVariable;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A discrete value is a string which is an information the actuator needs 
 * to perform an action or modify its state.
 * @author Jade Hennebert
 */
public class DiscreteValue extends Value {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DiscreteValue() {
        super();
    }
    
    public DiscreteValue(int id, ArrayList<String> values) {
        super(id);
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

    public String toString() {
        String res = super.toString();
        if(possibleValues != null) {
            res += "\nPossibles values: ";
            for (String possibleValue : possibleValues) {
                res += possibleValue;
            }
        }
        return res;
    }

}

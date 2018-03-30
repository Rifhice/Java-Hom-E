package server.models.environmentVariable;

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
    private String currentValue;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DiscreteValue() {
        super();
    }
    
    public DiscreteValue(int id, ArrayList<String> values, String currentValue) {
        super(id);
        this.possibleValues = values;
        this.currentValue = currentValue;
    }
    
    public DiscreteValue(String currentValue) {
        this.currentValue = currentValue;
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
    
    public String getCurrentValue() {
        return currentValue;
    }
    
    public void setCurrentValue(String value) {
        currentValue = value;
    }
    
    // ==================================

    public JSONObject toJson() {
        JSONObject result = super.toJson();
        result.put("type", "discrete");
        result.put("currentValue", currentValue);
    	JSONArray array = new JSONArray(possibleValues);
    	result.put("possibleValues", array);
    	return result;
    }
    
    public boolean isEqual(Value value) {
        if(value.getValue() instanceof String) {
            return ((String)value.getValue()).equals(currentValue);
        }
        return false;
    }

    public String toString() {
        String res = super.toString() + " " + currentValue;
        if(possibleValues != null) {
            boolean firstValue = true;
            res += "\nPossibles values: [";
            for (String possibleValue : possibleValues) {
                if(firstValue) {
                    res += possibleValue;
                    firstValue = false;
                }
                else {
                    res += ", " + possibleValue;
                }
            }
            res += "]";
        }
        return res;
    }

    public boolean isNotEqual(Value value) {
        if(value.getValue() instanceof String) {
            return !((String)value.getValue()).equals(currentValue);
        }
        return false;
    }

    public boolean isSuperior(Value value) {
        return false;
    }

    public boolean isInferior(Value value) {
        return false;
    }

    public boolean isSuperiorOrEqual(Value value) {
        return false;
    }
    public boolean isInferiorOrEqual(Value value) {
        return false;
    }  

	public Object getValue() {
		return currentValue;
	}
    
}

package sensor;

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
    
    public DiscreteValue(ArrayList<String> values, String currentValue) {
        super();
        this.possibleValues = values;
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
        setChanged();
        notifyObservers();
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
    
    public boolean isEqual(Object value) {
        if(value instanceof String) {
            return ((String)value).equals(currentValue);
        }
        return false;
    }

    public String toString() {
        String res = super.toString() + ":" + currentValue;
        if(possibleValues != null) {
            res += "\nPossibles values: ";
            for (String possibleValue : possibleValues) {
                res += possibleValue;
            }
        }
        return res;
    }

    public boolean isNotEqual(Object value) {
        if(value instanceof String) {
            return !((String)value).equals(currentValue);
        }
        return false;
    }

    public boolean isSuperior(Object value) {
        return false;
    }

    public boolean isInferior(Object value) {
        return false;
    }

    public boolean isSuperiorOrEqual(Object value) {
        return false;
    }
    public boolean isInferiorOrEqual(Object value) {
        return false;
    }  

}

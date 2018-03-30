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
    private String currentValue;

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
    
    public DiscreteValue(ArrayList<String> possibleValues, String currentValue) {
        super();
        this.currentValue = currentValue;
        this.possibleValues = possibleValues;
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

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
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

    @Override
    public boolean isEqual(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isNotEqual(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSuperior(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInferior(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSuperiorOrEqual(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInferiorOrEqual(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

}

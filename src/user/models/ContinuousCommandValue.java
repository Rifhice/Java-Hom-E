package user.models;

import org.json.JSONObject;

/**
 * A continous value is a float which is an information the actuator needs 
 * to perform an action or modify its state
 * @author Jade Hennebert
 */
public class ContinuousCommandValue extends CommandValue{
	
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private double valueMin;
    private double valueMax;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousCommandValue() {
    	
    }
    
    public ContinuousCommandValue(String name, double valueMin,double valueMax, double precision) {
        super(name);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
    }
    
    public ContinuousCommandValue(int id, String name, double valueMin,double valueMax, double precision) {
        super(id, name);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
    }
    
    public ContinuousCommandValue(double valueMin,double valueMax, double precision) {
        super();
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public double getValueMin() {
        return valueMin;
    }

    public void setValueMin(double valueMin) {
        this.valueMin = valueMin;
    }

    public double getValueMax() {
        return valueMax;
    }

    public void setValueMax(double valueMax) {
        this.valueMax = valueMax;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    // ==================================
    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	result.put("type", "continuous");
    	result.put("valueMin", valueMin);
    	result.put("valueMax", valueMax);
    	result.put("precision", precision);
    	return result;
    }
    
}

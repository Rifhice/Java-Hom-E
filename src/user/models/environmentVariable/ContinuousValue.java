package user.models.environmentVariable;

import org.json.JSONObject;

/**
 * A continuous value is a value which is a decimal number.
 * @author Clm-Roig
 */
public class ContinuousValue extends Value {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private double valueMin;
    private double valueMax;
    private double currentValue;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousValue(double valueMin,double valueMax, double precision) {
        super();
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
    };
    
    public ContinuousValue(int id, double valueMin,double valueMax, double precision, double currentValue) {
        super(id);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
        this.currentValue = currentValue;
    }
    
    public ContinuousValue(double valueMin,double valueMax, double precision, double currentValue) {
        super();
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
        this.currentValue = currentValue;
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

    public Object getCurrentValue() {
    	return currentValue;
    }
    
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    // ==================================

    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	result.put("type", "continuous");
    	result.put("valueMin", valueMin);
    	result.put("valueMax", valueMax);
    	result.put("precision", precision);
    	result.put("currentValue", currentValue);
    	return result;
    }


    public String toString() {
        String res = super.toString() + ":" + currentValue;
        res += "\nMin: " + valueMin;         
        res += "\nMax: " + valueMax;
        res += "\nPrecision: " + precision;
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

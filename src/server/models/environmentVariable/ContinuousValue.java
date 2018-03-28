package server.models.environmentVariable;

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
    public ContinuousValue() {
        super();
    };
    
    public ContinuousValue(int id, double valueMin,double valueMax, double precision, double currentValue) {
        super(id);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
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

    public double getCurrentValue() {
    	return currentValue;
    }
    
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
        setChanged();
        notifyObservers();
    }

    public JSONObject toJson() {
    	JSONObject result = super.toJson();
    	result.put("type", "continuous");
    	result.put("valueMin", valueMin);
    	result.put("valueMax", valueMax);
    	result.put("precision", precision);
    	result.put("currentValue", currentValue);
    	return result;
    }

    // ==================================

    public boolean isEqual(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() == currentValue;
        }
        return false;
    }

    public String toString() {
        String res = super.toString() + ":" + currentValue;
        res += "\nMin: " + valueMin;         
        res += "\nMax: " + valueMax;
        res += "\nPrecision: " + precision;
        return res;
    }

    public boolean isNotEqual(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() != currentValue;
        }
        return false;
    }

    public boolean isSuperior(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() > currentValue;
        }
        return false;
    }

    public boolean isInferior(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() < currentValue;
        }
        return false;
    }

    public boolean isSuperiorOrEqual(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() >= currentValue;
        }
        return false;
    }

    public boolean isInferiorOrEqual(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue() <= currentValue;
        }
        return false;
    }
}

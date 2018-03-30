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
    
    public ContinuousValue(double currentValue) {
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

    public boolean isEqual(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() == currentValue;
        }
        return false;
    }

    public String toString() {
        String res = super.toString() + " " + currentValue;
        res += "\nMin: " + valueMin;         
        res += "\nMax: " + valueMax;
        res += "\nPrecision: " + precision;
        return res;
    }

    public boolean isNotEqual(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() != currentValue;
        }
        return false;
    }

    public boolean isSuperior(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() > currentValue;
        }
        return false;
    }

    public boolean isInferior(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() < currentValue;
        }
        return false;
    }

    public boolean isSuperiorOrEqual(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() >= currentValue;
        }
        return false;
    }

    public boolean isInferiorOrEqual(Value value) {
        if (value.getValue() instanceof Number) {
            return ((Number) value.getValue()).doubleValue() <= currentValue;
        }
        return false;
    }

	public Object getValue() {
		return currentValue;
	}
}

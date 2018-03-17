package server.models;

import java.util.ArrayList;

/**
 * A discrete environment is a string which is an information the actuator needs 
 * to perform an action or modify its state
 * @author Jade Hennebert
 */
public class DiscreteEnvironmentVariable extends EnvironmentVariable {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;
    private String currentValue;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DiscreteEnvironmentVariable(String name, String description, String unit, ArrayList<String> values, String currentValue) {
        super(name,description,unit);
        this.possibleValues = values;
        this.currentValue = currentValue;
    }
    
    public DiscreteEnvironmentVariable(int id, String name, String description, String unit, ArrayList<String> values, String currentValue) {
        super(id, name,description,unit);
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

    public boolean isEqual(Object value) {
        if(value instanceof String) {
            return ((String)value).equals(currentValue);
        }
        return false;
    }

    public String toString() {
        return super.toString() + ":" + currentValue;
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

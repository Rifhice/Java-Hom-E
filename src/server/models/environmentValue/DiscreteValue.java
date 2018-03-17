package server.models.environmentValue;

import java.util.ArrayList;

/**
 * A discrete value is a variable which the current value is a String 
 * among possible values.
 * @author Clm-Roig
 */
public class DiscreteValue extends EnvironmentValue {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    
    public DiscreteValue() {}
    
    public DiscreteValue(String name, ArrayList<String> values) {
        super(name);
        this.possibleValues = values;   
    }
    
    public DiscreteValue(int id, String name, ArrayList<String> values) {
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


}
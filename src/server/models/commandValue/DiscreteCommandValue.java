package server.models.commandValue;

import java.util.ArrayList;

/**
 * A discrete value is a variable which the current value is a String 
 * among possible values.
 * @author Clm-Roig
 */
public class DiscreteCommandValue extends CommandValue {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    
    public DiscreteCommandValue() {}
    
    public DiscreteCommandValue(String name, ArrayList<String> values) {
        super(name);
        this.possibleValues = values;   
    }
    
    public DiscreteCommandValue(int id, String name, ArrayList<String> values) {
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

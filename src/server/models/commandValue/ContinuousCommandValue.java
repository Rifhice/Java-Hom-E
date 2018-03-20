package server.models.argument;

import org.json.JSONObject;

public class ContinuousArgument extends Argument{
	
	private double valueMin;
    private double valueMax;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousArgument(String name, String description, double valueMin,double valueMax, double precision) {
        super(name, description);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
    }
    
    public String toString() {
    	return "CONTINUOURS ARGUMENT \nName : " + name + "\nDescription : " + description + "\n From " + valueMin + " to " + valueMax + " every " + precision; 
    }
    
}

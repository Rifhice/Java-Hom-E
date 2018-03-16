package actuator;

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
    }
	
	@Override
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("type", "continuous");
		result.put("name", name);
		result.put("description", description);
		result.put("valueMin", valueMin);
		result.put("valueMax", valueMax);
		result.put("precision", precision);
		return result;
	}
    
}

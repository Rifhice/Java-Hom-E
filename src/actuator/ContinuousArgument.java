package actuator;

import org.json.JSONObject;

public class ContinuousArgument extends Argument{
	
	private double valueMin;
    private double valueMax;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousArgument(String name, double valueMin,double valueMax, double precision) {
        super(name);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.precision = precision;
    }
	
	@Override
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("type", "continuous");
		result.put("name", name);
		result.put("valuemin", valueMin);
		result.put("valuemax", valueMax);
		result.put("precision", precision);
		return result;
	}
    
}

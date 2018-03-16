package sensor;

import org.json.JSONObject;

public class ContinuousVariable extends Variable{
	
	private double valueMin;
    private double valueMax;
    private double currentValue;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousVariable(String name, String description, String unity, double valueMin,double valueMax, double precision, double currentValue) {
        super(name, description, unity);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.currentValue = currentValue;
    }
    
	public void setValue(Object newValue) {
		if(newValue instanceof Double) {
			currentValue = (Double) newValue;
		}
		Sensor.updateVariable(this);
	}
	
	@Override
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("id", id);
		result.put("type", "continuous");
		result.put("name", name);
		result.put("description", description);
		result.put("unity", unit);
		result.put("valueMin", valueMin);
		result.put("valueMax", valueMax);
		result.put("precision", precision);
		result.put("currentValue",currentValue);
		return result;
	}
    
}

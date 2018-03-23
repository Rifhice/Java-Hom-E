package actuator;

import java.util.ArrayList;

import org.json.JSONObject;

public class DiscreteArgument extends Argument {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<String> possibleValues;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public DiscreteArgument(String name, ArrayList<String> values) {
        super(name);
        this.possibleValues = values;
    }

	@Override
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("type", "discrete");
		result.put("name", name);
		for (int i = 0; i < possibleValues.size(); i++) {
			result.append("values", possibleValues.get(i));
		}
		return result;
	}
	
}

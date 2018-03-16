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
    public DiscreteArgument(String name, String description, ArrayList<String> values) {
        super(name,description);
        this.possibleValues = values;
    }

	@Override
	public JSONObject getJson() {
		JSONObject result = new JSONObject();
		result.put("type", "discrete");
		result.put("name", name);
		result.put("description", description);
		for (int i = 0; i < possibleValues.size(); i++) {
			result.append("values", possibleValues.get(i));
		}
		return result;
	}
	
}

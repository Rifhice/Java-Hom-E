package server.models.evaluable;

import org.json.JSONObject;

public interface Evaluable {

	public boolean evaluate();
	public JSONObject toJson();
}

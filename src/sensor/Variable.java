package sensor;

import org.json.JSONObject;

public abstract class Variable {

	protected String name;
	protected String unit;
	protected String description;
	protected int id;
	
	public Variable(String name, String description, String unit) {
		this.name = name;
		this.unit = unit;
		this.description = description;
	}

	public abstract void setValue(Object newValue);
	public abstract JSONObject getJson();
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
}

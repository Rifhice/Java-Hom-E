package sensor;

import org.json.JSONObject;

public abstract class Variable {

	protected String name;
	protected String unit;
	protected String description;
	protected static int id;
	protected static int ID_COUNT = 0;
	
	public Variable(String name, String description, String unit) {
		this.name = name;
		this.unit = unit;
		this.description = description;
		id = ID_COUNT;
		ID_COUNT++;
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

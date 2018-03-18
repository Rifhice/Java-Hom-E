package server.models.categories;

public class SensorCategory extends Category{

	public SensorCategory() {}
	
	public SensorCategory(int id, String name, String description) {
		super(id, name, description);
	}
	public SensorCategory(String name, String description) {
		super(name, description);
	}
	
	public String toString() {
		return "Sensor category : " + id + "\nName : " + name + "\nDescription : " + description;
	}
}

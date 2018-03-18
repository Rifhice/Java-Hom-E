package server.models.categories;

public class ActuatorCategory extends Category{
	
	public ActuatorCategory() {}
	
	public ActuatorCategory(int id, String name, String description) {
		super(id, name, description);
	}
	public ActuatorCategory(String name, String description) {
		super(name, description);
	}
	
	public String toString() {
		return "Actuator category : " + id + "\nName : " + name + "\nDescription : " + description;
	}
}

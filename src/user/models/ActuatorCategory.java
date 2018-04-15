package user.models;

public class ActuatorCategory extends Category{
	
	public ActuatorCategory(int id, String name, String description) {
		super(name, description);
		this.id = id;
	}
	
	public ActuatorCategory(int id, String name) {
		super(name);
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}

package user.models;

public class SensorCategory extends Category{

	int id;
	
	public SensorCategory(int id,String name, String description) {
		super(name, description);
		this.id = id;
	}
	
}

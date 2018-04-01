package user.models;

public abstract class Category {

	protected String name;
	protected String description;
	protected int id;
	public Category(String name) {
		this.name = name;
	}
	
	public Category(String name,String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
}

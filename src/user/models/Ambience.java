package user.models;

import java.util.List;

public class Ambience {

	private int id;
	private String name;
	private List<Integer> behaviours;
	
	public Ambience(int id, String name, List<Integer> behaviours) {
		this.id = id;
		this.name = name;
		this.behaviours = behaviours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getBehaviours() {
		return behaviours;
	}

	public void setBehaviours(List<Integer> behaviours) {
		this.behaviours = behaviours;
	}
	
}

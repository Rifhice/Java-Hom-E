package user.models;

import java.util.List;

public class Ambience {

	private int id;
	private List<Integer> behaviours;
	
	public Ambience(int id, List<Integer> behaviours) {
		this.id = id;
		this.behaviours = behaviours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getBehaviours() {
		return behaviours;
	}

	public void setBehaviours(List<Integer> behaviours) {
		this.behaviours = behaviours;
	}
	
}



import java.util.ArrayList;

public class Ambience {

	private ArrayList<Behaviour> behaviours;
	private String name;
	
	public Ambience(String name, ArrayList<Behaviour> behaviours) {
		this.name = name;
		this.behaviours = behaviours;
	}
	
	public void activateAmbience() {
		for (int i = 0; i < behaviours.size(); i++) {
			behaviours.get(i).activate();
		}
	}
	
	public void deactivateAmbience() {
		for (int i = 0; i < behaviours.size(); i++) {
			behaviours.get(i).deactivate();
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addBehaviour(Behaviour behaviour) {
		behaviours.add(behaviour);
	}
	
	public void deleteBehaviour(Behaviour behaviour) {
		behaviours.remove(behaviour);
	}
	
	public ArrayList<Behaviour> getBehaviours(){
		return behaviours;
	}
}

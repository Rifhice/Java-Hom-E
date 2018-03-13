package models;


import java.util.ArrayList;

public class Ambience {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<Behaviour> behaviours;
	private String name;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Ambience(String name, ArrayList<Behaviour> behaviours) {
		this.name = name;
		this.behaviours = behaviours;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //	
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

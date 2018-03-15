package models;


import java.util.ArrayList;

public class Ambience {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private String name;

    // Attributes from other tables
    private ArrayList<Behaviour> behaviours;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Ambience() {}
    
    public Ambience(String name) {
        this.name = name;
    }

    public Ambience(String name, ArrayList<Behaviour> behaviours) {
        this.name = name;
        this.behaviours = behaviours;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //		
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setBehaviours(ArrayList<Behaviour> behaviours) {
        this.behaviours = behaviours;
    }

    public ArrayList<Behaviour> getBehaviours(){
        return behaviours;
    }
    
    public void addBehaviour(Behaviour behaviour) {
        behaviours.add(behaviour);
    }

    public void deleteBehaviour(Behaviour behaviour) {
        behaviours.remove(behaviour);
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
}

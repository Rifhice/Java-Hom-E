package server.models;


import java.util.ArrayList;

public class Ambience {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;

    // Attributes from other tables
    private ArrayList<Behaviour> behaviours;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Ambience() {}
    
    public Ambience(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ambience(int id, String name, ArrayList<Behaviour> behaviours) {
        this.id = id;
        this.name = name;
        this.behaviours = behaviours;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //		
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
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
    
    // ==================================
    
    public void addBehaviour(Behaviour behaviour) {
        behaviours.add(behaviour);
    }

    public void deleteBehaviour(Behaviour behaviour) {
        behaviours.remove(behaviour);
    }

    public void activateAmbience() {
        for (int i = 0; i < behaviours.size(); i++) {
            behaviours.get(i).setActivated(true);
        }
    }

    public void deactivateAmbience() {
        for (int i = 0; i < behaviours.size(); i++) {
            behaviours.get(i).setActivated(false);
        }
    }
}

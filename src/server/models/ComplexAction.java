package server.models;

import java.util.ArrayList;

/**
 * A complex action is a collection of atomic actions.
 * @author Clm-Roig
 */
public class ComplexAction {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;
    
    // Attributes from other tables
    private ArrayList<AtomicAction> atomicActions;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ComplexAction() {}
    
    public ComplexAction(String name) {
        this.name = name;
    }
    
    public ComplexAction(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public ComplexAction(String name, ArrayList<AtomicAction> atomicActions) {
        this.name = name;
        this.atomicActions = atomicActions;
    }
    
    public ComplexAction(int id, String name, ArrayList<AtomicAction> atomicActions) {
        this.id = id;
        this.name = name;
        this.atomicActions = atomicActions;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AtomicAction> getAtomicActions() {
        return atomicActions;
    }

    public void setAtomicActions(ArrayList<AtomicAction> atomicActions) {
        this.atomicActions = atomicActions;
    }
    
    // ================================
    
    public void execute() {
        // TODO
    }

}

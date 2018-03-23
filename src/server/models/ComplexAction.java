package server.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
    
    public String toString() {
    	String res = "Id : " + id + "\nName : " + name+"\n";
    	for (int i = 0; i < atomicActions.size(); i++) {
			res += atomicActions.get(i).toString()+"\n";
		}
    	return res;
    }
    
    // ================================
    
    public void execute() {
        // TODO
    }
    
    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
    	result.put("id", id);
    	result.put("name",name);
    	for (int i = 0; i < atomicActions.size(); i++) {
			result.append("atomicAction", atomicActions.get(i).toJson());
		}
    	return result;
    }

}

package server.models.environmentVariable;

import java.util.Observable;

import org.json.JSONObject;

public abstract class Value extends Observable {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
<<<<<<< refs/remotes/origin/Values
    public Value() {
        
    }
    
=======
>>>>>>> Values changes
    public Value(int id) {
        this.id = id;
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
    
    // =================================
    // TODO 
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        return result;
    }
<<<<<<< refs/remotes/origin/Values
    
    public abstract boolean isEqual(Object value);
    public abstract boolean isNotEqual(Object value);
    public abstract boolean isSuperior(Object value);
    public abstract boolean isInferior(Object value);
    public abstract boolean isSuperiorOrEqual(Object value);
    public abstract boolean isInferiorOrEqual(Object value);
=======
>>>>>>> Values changes


}


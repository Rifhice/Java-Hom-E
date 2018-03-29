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
    public Value() {}
    
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
    
    public abstract Object getValue();
    
    // =================================
    // TODO 
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        result.put("id", id);
        return result;
    }
    public abstract boolean isEqual(Value value);
    public abstract boolean isNotEqual(Value value);
    public abstract boolean isSuperior(Value value);
    public abstract boolean isInferior(Value value);
    public abstract boolean isSuperiorOrEqual(Value value);
    public abstract boolean isInferiorOrEqual(Value value);
    
    public String toString() {
        String res = "#" + getId();
        return res;
    }
}


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


}


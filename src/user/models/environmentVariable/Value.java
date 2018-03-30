package user.models.environmentVariable;

import java.util.Observable;

import org.json.JSONObject;

public abstract class Value {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    
    public static enum VALUE_TYPE {
    	CONTINUOUS,
    	DISCRETE;
    	
    	public String toString() {
    		switch(this) {
    		case CONTINUOUS: return "continuous";
    		case DISCRETE: return "discrete";
    		}
    		return "";
    	}
    }

    
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
    
    // =================================
    // TODO 
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        return result;
    }
    
    public String toString() {
        String res = "VALUE #" + getId();
        return res;
    }
    
    public abstract boolean isEqual(Object value);
    public abstract boolean isNotEqual(Object value);
    public abstract boolean isSuperior(Object value);
    public abstract boolean isInferior(Object value);
    public abstract boolean isSuperiorOrEqual(Object value);
    public abstract boolean isInferiorOrEqual(Object value);
    public abstract Object getCurrentValue();
}


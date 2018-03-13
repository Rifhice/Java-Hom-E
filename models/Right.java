package models;

public class Right {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String denomination; 
    private String description;  
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Right() {}

    public Right(String denomination, String description) {
        this.denomination = denomination;
        this.description = description;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
        
    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        String right = "ID: "+ id +"\nDenomination: "+ denomination + "\nDescription: "+ description;          
        return right;
    }

}

package server.models;

public class Role {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name; 
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Role() {}
    
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
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

    // ==================================
  

}

package user.models;

import java.util.ArrayList;

public class Role {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name; 

    // Attributes from others tables   
    private ArrayList<Right> rights;

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

    public ArrayList<Right> getRights() {
        return rights;
    }

    public void setRights(ArrayList<Right> rights) {
        this.rights = rights;
    }  

    // ==================================

    public String toString() {
        String role = "\n ROLE #"+id+" "+name;
        role += "\nRights:";
        if(rights != null) {
            for (Right right : rights) {
                role += "\n"+right;
            }
        }
        return role;
    }  

}

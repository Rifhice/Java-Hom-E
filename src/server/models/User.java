package server.models;

import java.util.ArrayList;

public class User {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String pseudo; 
    private String password;
    
    // Attributes from others tables
    private int roleId;
    private String roleName;    
    
    private ArrayList<Right> rights;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public User() {}

    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }
    
    public User(int id, String pseudo, String password) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
    }
    
    public User(String pseudo, String password, int roleId, String roleName) {
        this.pseudo = pseudo;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    public User(int id, String pseudo, String password, int roleId, String roleName) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    public User(int id, String pseudo, String password, int roleId, String roleName, ArrayList<Right> rights) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.roleId = roleId;
        this.roleName = roleName;
        this.rights = rights;
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

    public String getPseudo() {
        return this.pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    

    public ArrayList<Right> getRights() {
        return rights;
    }

    public void setRights(ArrayList<Right> rights) {
        this.rights = rights;
    }

    // ==================================
    
    public String toString() {
        String user = "USER n°"+ id +"\n";
        user += pseudo;
        user += "\n" + password;
        user += "\nRole n°" + roleId + ": "+roleName;
        user += "\nRights:";
        for (Right right : rights) {
            user += "\n# "+right.getId() +" " + right.getDenomination() + " - " + right.getDescription();
        }
        return user;
    }

}

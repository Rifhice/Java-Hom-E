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
    private Role role;    
    private ArrayList<Right> rights;
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public User() {}

    public User(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public User(String pseudo, Role role) {
        this.pseudo = pseudo;
        this.role = role;
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
    
    public User(String pseudo, String password, Role role) {
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
    }
    
    public User(int id, String pseudo, String password, Role role) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;;
    }
    
    public User(int id, String pseudo, String password, Role role, ArrayList<Right> rights) {
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ArrayList<Right> getRights() {
        return rights;
    }

    public void setRights(ArrayList<Right> rights) {
        this.rights = rights;
    }

    // ==================================
    
    public String toString() {
        String user = "USER #"+ id;
        user += "\nPseudo: " + pseudo;
        user += "  Password: " + password;
        user += "\nRole #" + role.getId() + ": "+role.getName();
        user += "\nRights:";
        if(rights != null) {
            for (Right right : rights) {
                user += "\n"+right;
            }
        }
        return user;
    }
    
    public static void main(String[] args) {
        User u = new User("test", new Role(1,"bla"));
        System.out.println(u);
    }

}

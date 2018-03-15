package models;

public class User {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String pseudo; 
    private String password;
    private USERTYPE type;
    
    public static enum USERTYPE {
        FAMILY,
        OWNER,
        GUEST
    }
    
    // Attributes from others tables
    private int roleId;
    private String roleName;    
    
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
    
    public User(String pseudo, USERTYPE type) {
        this.pseudo = pseudo;
        this.type = type;
    }

    public User(String pseudo, String password, USERTYPE type) {
        this.pseudo = pseudo;
        this.password = password;
        this.type = type;
    }
    
    public User(String pseudo, String password, USERTYPE type, int roleId, String roleName) {
        this.pseudo = pseudo;
        this.password = password;
        this.type = type;
        this.roleId = roleId;
        this.roleName = roleName;
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

    public USERTYPE getType() {
        return this.type;
    }

    public void setType(USERTYPE type) {
        this.type = type;
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

    // ==================================
    
    public String toString() {
        String user = "USER n°"+ id +"\n";
        user += pseudo;
        user += "\n" + password;
        user += "\n" + type;
        user += "\n" + roleId;
        user += "\n" + roleName;
        return user;
    }

}

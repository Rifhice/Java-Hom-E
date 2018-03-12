package models;

public class User {

    // ==== ATTRIBUTES ==== //
    private String id;
    private String pseudo; 
    private String password;
    private USERTYPE type;

    public enum USERTYPE {
        FAMILY,
        OWNER
    }

    // ==== CONSTRUCTORS ==== //
    public User() {}

    public User(String pseudo) {
        this.pseudo = pseudo;
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public User(String pseudo, String password, USERTYPE type) {
        this.pseudo = pseudo;
        this.password = password;
    }

    // ==== METHODS ==== //
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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

}

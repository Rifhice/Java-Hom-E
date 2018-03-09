package models;

public class User {

	private String pseudo; 
	private String password;
	private USERTYPE type;
	
	public enum USERTYPE {
		FAMILY,
		OWNER
	}
	
	public User(String pseudo, String password, USERTYPE type) {
		this.pseudo = pseudo;
		this.password = password;
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
	
	public User() {}
	
	public User(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public User(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
	}
	
}

package models;

public class User {

	private String pseudo; 
	private String password;
	
	public User() {}
	
	public User(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public User(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
	}
	
}

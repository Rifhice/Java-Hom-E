package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDriver {
	  
	// URL de connexion
	private String url = "jdbc:postgresql://localhost:5432";
	
	// Nom du user
	private String user = "user";
	
	// Mot de passe de l'utilisateur
	private String passwd = "pass";
	
	// Objet Connection
	private static Connection connect;

	// Private constructor (singleton pattern)
	private ConnectionDriver(){
		try {
			connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getInstance(){
		if(connect == null){
			new ConnectionDriver();
		}
		return connect;   
	}   

}


package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DriverConnection {
	  
	// URL de connexion
	private String url = "jdbc:sqlite:hom-e.db";
	
	// Objet Connection
	private static Connection connect;

	// Private constructor (singleton pattern)
	private DriverConnection(){
		try {
			connect = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getInstance(){
		if(connect == null){
			new DriverConnection();
		}
		return connect;   
	}   

}


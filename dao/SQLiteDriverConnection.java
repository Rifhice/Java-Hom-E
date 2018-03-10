package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteDriverConnection {
	  
	// URL de connexion
	private String url = "jdbc:sqlite:sqlite/db/hom-e.db";
	
	// Objet Connection
	private static Connection connect;

	// Private constructor (singleton pattern)
	private SQLiteDriverConnection(){
		try {
			connect = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getInstance(){
		if(connect == null){
			new SQLiteDriverConnection();
		}
		return connect;   
	}   

}


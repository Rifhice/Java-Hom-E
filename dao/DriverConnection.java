package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnection {
	  
	private String url = "jdbc:sqlite:hom-e.db";
	private static Connection connect;

	/**
	 * Private constructor (singleton pattern)
	 */
	private DriverConnection(){
		try {
			connect = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the DriverConnection singleton.
	 * @return DriverConnection
	 */
	public static Connection getInstance(){
		if(connect == null){
			new DriverConnection();
		}
		return connect;   
	}   

}


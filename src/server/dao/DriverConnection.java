package server.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverConnection {
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    public static final int SQLITE_DRIVER = 1;
    public static final int POSTGRESQL_DRIVER = 2;
    public static final int ORACLE_DRIVER = 3;
	  
	private static String SQLiteUrl = "jdbc:sqlite:hom-e.db";
	
	private static String PostGreSQLUrl = "";
	private static int PostGreSQLPort = 0000;
	
	private static String OracleUrl = "";
	private static int OraclePort = 0000;
	
	private static Connection connect;
	
	// ==================== //

	/**
	 * Private constructor (singleton pattern)
	 */
	private DriverConnection(String url){
		try {
			connect = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the DriverConnection singleton according to the type given.
	 * Destroy the old DriverConnection. 
	 * @return DriverConnection
	 */
	public static Connection getInstance(int type){
		if(connect == null){
		    switch(type){
		        case(SQLITE_DRIVER):
		            new DriverConnection(SQLiteUrl);
		        break;
		        
		        case(POSTGRESQL_DRIVER):
                    new DriverConnection(PostGreSQLUrl);
                break;
                
		        case(ORACLE_DRIVER):
                    new DriverConnection(OracleUrl);
                break;
		    }
		}
		return connect;   
	}   

}


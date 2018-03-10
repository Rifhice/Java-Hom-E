package Tools;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DriverConnection;

public class SQLiteDatabaseManager {
	
	private static Connection conn;
	
	
	public static void main(String args[]) {
		conn = DriverConnection.getInstance();
		SQLiteDatabaseManager.fillDatabase();
	}
	
	private static void fillDatabase() {
		String sql1 = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";
        String sql2 = "INSERT INTO users ('pseudo', 'password') VALUES ('owner', 'password');";
        
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql1);
            stmt.execute(sql2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        		
	}
}

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
		String createTableUsers = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL UNIQUE,\n"
                + "	password text NOT NULL\n"
                + ");";
        String insertUsers = "INSERT INTO users ('pseudo', 'password') VALUES ('owner', 'password');";
        
        String createTableHistory = "CREATE TABLE IF NOT EXISTS history (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n"
                + " user text NOT NULL, \n"
                + "	type text NOT NULL, \n"
                + " action text NOT NULL"
                + ");";
        String insertHistory = "INSERT INTO history ('user', 'type', 'action') VALUES ('owner', 'command', 'Switch light on');";
        
        
        try (Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(createTableUsers);
            stmt.execute(insertUsers);
            stmt.execute(createTableHistory);
            stmt.execute(insertHistory);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        		
	}
}
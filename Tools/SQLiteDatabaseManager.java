package Tools;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DriverConnection;

public class SQLiteDatabaseManager {
	
	private static Connection conn;
	
	
	public static void main(String args[]) {
		conn = DriverConnection.getInstance();
		SQLiteDatabaseManager.createDatabase();
	}
	
	private static void createDatabase() {
		String sqlUsers = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String sqlRoles = "CREATE TABLE IF NOT EXISTS Roles (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL\n"
                + ");";        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlRoles);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	private static void insertUsers() {
	    String sqlOwner = "INSERT INTO users ('pseudo', 'password') VALUES ('owner', 'password');";
	    String sqlFamilyMember = "";
	}

}

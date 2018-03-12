package Tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DriverConnection;

public class SQLiteDatabaseManager {

    private static Connection conn;

    // ================ //
    // ==== CREATE ==== //
    // ================ //
    private static void createDatabase() {
        String createTableUsers = "CREATE TABLE IF NOT EXISTS users (\n" + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL UNIQUE,\n" + "	password text NOT NULL\n" + ");";

        String createTableHistory = "CREATE TABLE IF NOT EXISTS history (\n" + "	id integer PRIMARY KEY,\n"
                + "	date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" + " user text NOT NULL, \n"
                + "	type text NOT NULL, \n" + " action text NOT NULL" + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableUsers);
            stmt.execute(createTableHistory);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // ================= //
    // ==== SEEDERS ==== //
    // ================= //
    private static void insertUsers() {
        String insertUsers = "INSERT INTO users ('pseudo', 'password') VALUES ('owner', 'password');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertUsers);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insertHistories() {
        String insertHistory = "INSERT INTO history ('user', 'type', 'action') VALUES ('owner', 'command', 'Switch light on');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertHistory);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== //
    public static void main(String args[]) {
        conn = DriverConnection.getInstance();
        SQLiteDatabaseManager.createDatabase();
        SQLiteDatabaseManager.insertUsers();
        SQLiteDatabaseManager.insertHistories();
    }
}
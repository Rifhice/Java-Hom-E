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
        String createTableUsers = "CREATE TABLE IF NOT EXISTS users (\n" 
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL UNIQUE,\n" 
                + "	password text NOT NULL\n" 
                + ");";

        String createTableHistories = "CREATE TABLE IF NOT EXISTS histories (\n" 
                + "	id integer PRIMARY KEY,\n"
                + "	date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" 
                + " user text NOT NULL, \n"
                + "	type text NOT NULL, \n" 
                + " action text NOT NULL" 
                + ");";
        
        String createTableRoles = "CREATE TABLE IF NOT EXISTS roles (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " name text NOT NULL \n"
                + ");";
        
        String createTableRights = "CREATE TABLE IF NOT EXISTS rights (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " denomination text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableActuators = "CREATE TABLE IF NOT EXISTS actuators (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableSensors = "CREATE TABLE IF NOT EXISTS sensors (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableActuatorCategories = "CREATE TABLE IF NOT EXISTS actuatorCategories (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableSensorCategories = "CREATE TABLE IF NOT EXISTS sensorCategories (\n" 
                + "    id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableUsers);
            stmt.execute(createTableHistories);
            stmt.execute(createTableRoles);
            stmt.execute(createTableRights);
            stmt.execute(createTableActuators);
            stmt.execute(createTableSensors);
            stmt.execute(createTableActuatorCategories);
            stmt.execute(createTableSensorCategories);
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
            System.out.println("ERROR inserting Users : " + e.getMessage());
        }
    }

    private static void insertHistories() {
        String insertHistory = "INSERT INTO histories ('user', 'type', 'action') VALUES ('owner', 'command', 'Switch light on');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertHistory);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Histories : " + e.getMessage());
        }
    }
    
    private static void insertRoles() {
        String insertRoleOwner = "INSERT INTO roles ('name') VALUES ('owner');";
        String insertRoleFamilyMember = "INSERT INTO roles ('name') VALUES ('family member');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRoleOwner);
            stmt.execute(insertRoleFamilyMember);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Roles : " + e.getMessage());
        }
    }
    
    private static void insertRights() {
        String insertRightLRLights = "INSERT INTO rights ('denomination', 'description') VALUES ('Switch the living room lights.','Allow to switch on and off the lights of the living room.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRightLRLights);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Rights : " + e.getMessage());
        }
    }
    
    private static void insertActuators() {
        String insertActuator1 = "INSERT INTO actuators ('name', 'description') VALUES ('Philips 70W Bulb','Powerfull yellow bulb');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertActuator1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Actuators : " + e.getMessage());
        }
    }
    
    private static void insertSensors() {
        String insertSensor1 = "INSERT INTO sensors ('name', 'description') VALUES ('Presensor','Sense the presence of something (10m range)');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensor1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== //
    
    public static void main(String args[]) {
        conn = DriverConnection.getInstance();
        SQLiteDatabaseManager.createDatabase();
        
        // Seeders
        SQLiteDatabaseManager.insertUsers();
        SQLiteDatabaseManager.insertHistories();
        SQLiteDatabaseManager.insertRoles();
        SQLiteDatabaseManager.insertRights();
        SQLiteDatabaseManager.insertActuators();
        SQLiteDatabaseManager.insertSensors();
        
        System.out.println("Terminating.");
    }
}
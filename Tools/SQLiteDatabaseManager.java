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
        
        // ==== DROP TABLES SQL
        String dropActuators = "DROP TABLE IF EXISTS actuators;";
        String dropActuatorCategories = "DROP TABLE IF EXISTS actuatorsCategories;";
        String dropHistories = "DROP TABLE IF EXISTS histories;";
        String dropRights = "DROP TABLE IF EXISTS rights;";
        String dropRoles = "DROP TABLE IF EXISTS roles;";
        String dropSensors = "DROP TABLE IF EXISTS sensors;";
        String dropSensorCategories = "DROP TABLE IF EXISTS sensors;";
        String dropUsers = "DROP TABLE IF EXISTS users;";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(dropActuators);
            stmt.execute(dropActuatorCategories);
            stmt.execute(dropHistories);
            stmt.execute(dropRights);
            stmt.execute(dropRoles);
            stmt.execute(dropSensors);
            stmt.execute(dropSensorCategories);
            stmt.execute(dropUsers);
        } catch (SQLException e) {
            System.out.println("ERROR dropping tables : " + e.getMessage());
        }
        
        // ==== CREATE TABLES SQL
        String createTableUsers = "CREATE TABLE IF NOT EXISTS users (\n" 
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL UNIQUE,\n" 
                + "	password text NOT NULL,\n"
                + " fk_role_id integer NOT NULL,\n"
                + " FOREIGN KEY (fk_role_id) REFERENCES Roles(id) \n"
                + ");";

        String createTableHistories = "CREATE TABLE IF NOT EXISTS histories (\n" 
                + "	id integer PRIMARY KEY,\n"
                + "	date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" 
                + " user text NOT NULL, \n"
                + "	type text NOT NULL, \n" 
                + " action text NOT NULL" 
                + ");";
        
        String createTableRoles = "CREATE TABLE IF NOT EXISTS roles (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL \n"
                + ");";
        
        String createTableRights = "CREATE TABLE IF NOT EXISTS rights (\n" 
                + " id integer PRIMARY KEY,\n"
                + " denomination text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableActuators = "CREATE TABLE IF NOT EXISTS actuators (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text, \n"
                + " fk_actuatorCategorie_id integer,\n"
                + " FOREIGN KEY (fk_actuatorCategorie_id) REFERENCES actuatorCategories(id) \n"
                + ");";
        
        String createTableSensors = "CREATE TABLE IF NOT EXISTS sensors (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text, \n"
                + " fk_sensorCategorie_id integer,\n"
                + " FOREIGN KEY (fk_sensorCategorie_id) REFERENCES sensorCategories(id) \n"
                + ");";
        
        String createTableActuatorCategories = "CREATE TABLE IF NOT EXISTS actuatorCategories (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";
        
        String createTableSensorCategories = "CREATE TABLE IF NOT EXISTS sensorCategories (\n" 
                + " id integer PRIMARY KEY,\n"
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
            System.out.println("ERROR creating tables : " + e.getMessage());
        }
        
     // ==== CREATE RELATIONSHIP TABLES SQL
        String createOwns = "CREATE TABLE IF NOT EXISTS owns (\n" 
                + " fk_right_id integer, \n"
                + " fk_user_id integer, \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES Rights(id), \n"
                + " FOREIGN KEY (fk_user_id) REFERENCES Users(id) \n"
                + ");";
        
        String createOwnsByDefault = "CREATE TABLE IF NOT EXISTS ownsbydefault (\n" 
                + " fk_right_id integer, \n"
                + " fk_role_id integer, \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES Rights(id), \n"
                + " FOREIGN KEY (fk_role_id) REFERENCES Roles(id) \n"
                + ");";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createOwns);
            stmt.execute(createOwnsByDefault);
        } catch (SQLException e) {
            System.out.println("ERROR creating relationship tables : " + e.getMessage());
        }
      
    }
    
    // ================= //
    // ==== SEEDERS ==== //
    // ================= //
    private static void insertRoles() {
        String insertRoleOwner = "INSERT INTO roles ('id','name') VALUES (1,'owner');";
        String insertRoleFamilyMember = "INSERT INTO roles ('id', 'name') VALUES (2,'family member');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRoleOwner);
            stmt.execute(insertRoleFamilyMember);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Roles : " + e.getMessage());
        }
    }
    
    private static void insertUsers() {
        String insertUsers = "INSERT INTO users ('pseudo', 'password', 'fk_role_id') VALUES ('owner', 'password', 1);";
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
        String insertSensor1 = "INSERT INTO sensors ('name', 'description') VALUES ('Presensor3000','Sense the presence of something (10m range)');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensor1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }
    
    private static void insertActuatorCategories() {
        String insertActuatorCat1 = "INSERT INTO actuatorCategories ('name', 'description') VALUES ('Bulb','Provide light.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertActuatorCat1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ActuatorCategories : " + e.getMessage());
        }
    }
    
    private static void insertSensorCategories() {
        String insertSensorCat1 = "INSERT INTO sensorCategories ('name', 'description') VALUES ('Movement detector','Detect movement.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensorCat1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== //
    
    public static void main(String args[]) {
        conn = DriverConnection.getInstance();
        
        // Creation
        System.out.print("Init DB... ");
        createDatabase();
        System.out.println("DB created.");
        
        // Seeders
        System.out.print("Inserting data... ");
        insertRoles();
        insertUsers();
        insertHistories();
        insertRights();
        insertActuators();
        insertSensors();
        insertActuatorCategories();
        insertSensorCategories();
        System.out.println("Data inserted.");
        
        System.out.println("**** Process complete ! ****");
    }
}
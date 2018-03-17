package server.tools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import server.dao.DriverConnection;

public class SQLiteDatabaseManager {

    private static Connection conn = DriverConnection.getInstance(DriverConnection.SQLITE_DRIVER);

    // ================ //
    // ==== CREATE ==== //
    // ================ //
    private static void createDatabase() {

        // ==== DROP TABLES SQL
        String dropActuators = "DROP TABLE IF EXISTS actuators;";
        String dropActuatorCategories = "DROP TABLE IF EXISTS actuatorsCategories;";
        String dropAmbiences = "DROP TABLE IF EXISTS ambiences;";
        String dropBehaviours = "DROP TABLE IF EXISTS behaviours;";
        String dropBlocks = "DROP TABLE IF EXISTS blocks;";
        String dropCommands = "DROP TABLE IF EXISTS commands;";
        String dropContinuousValues = "DROP TABLE IF EXISTS continuousValues;";
        String dropDiscreteValues = "DROP TABLE IF EXISTS discreteValues;";
        String dropEnvironmentValues = "DROP TABLE IF EXISTS environmentValues;";
        String dropExpressions = "DROP TABLE IF EXISTS expressions;";
        String dropHistories = "DROP TABLE IF EXISTS histories;";
        String dropRights = "DROP TABLE IF EXISTS rights;";
        String dropRoles = "DROP TABLE IF EXISTS roles;";
        String dropSensors = "DROP TABLE IF EXISTS sensors;";
        String dropSensorCategories = "DROP TABLE IF EXISTS sensors;";
        String dropUsers = "DROP TABLE IF EXISTS users;";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(dropActuators);
            stmt.execute(dropActuatorCategories);
            stmt.execute(dropAmbiences);
            stmt.execute(dropBehaviours);
            stmt.execute(dropBlocks);
            stmt.execute(dropCommands);
            stmt.execute(dropContinuousValues);
            stmt.execute(dropDiscreteValues);
            stmt.execute(dropEnvironmentValues);
            stmt.execute(dropExpressions);
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
        String createTableActuators = "CREATE TABLE IF NOT EXISTS actuators (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text, \n"
                + " fk_actuatorCategory_id integer,\n"
                + " FOREIGN KEY (fk_actuatorCategory_id) REFERENCES actuatorCategories(id) \n"
                + ");";

        String createTableActuatorCategories = "CREATE TABLE IF NOT EXISTS actuatorCategories (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";

        String createTableAmbiences = "CREATE TABLE IF NOT EXISTS ambiences (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";

        String createTableBehaviours = "CREATE TABLE IF NOT EXISTS behaviours (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " is_activated integer, \n"
                + " fk_expression_id integer, \n"
                + " FOREIGN KEY (fk_expression_id) REFERENCES expressions(id) \n"
                + ");";

        String createTableBlocks = "CREATE TABLE IF NOT EXISTS blocks (\n" 
                + " id integer PRIMARY KEY, \n"
                + " operator text NOT NULL, \n"
                + " fk_environmentVariable_id integer, \n"
                + " fk_value_id integer, \n"
                + " FOREIGN KEY (fk_environmentVariable_id) REFERENCES environmentVariables(id), \n"
                + " FOREIGN KEY (fk_value_id) REFERENCES environmentValues(id) \n"
                + ");";
        
        String createTableCommands = "CREATE TABLE IF NOT EXISTS commands (\n" 
                + " id integer PRIMARY KEY, \n"
                + " name text NOT NULL, \n"
                + " fk_actuator_id integer, \n"
                + " FOREIGN KEY (fk_actuator_id) REFERENCES actuators(id) \n"
                + ");";
        
        String createTableContinuousValues ="CREATE TABLE IF NOT EXISTS continuousValues (\n"
                + " fk_environment_value_id integer PRIMARY KEY, \n"
                + " min real, \n"
                + " max real, \n"
                + " precision real, \n"
                + " FOREIGN KEY (fk_environment_value_id) REFERENCES environmentValues(id) \n"
                + ");";
        
        String createTableDiscreteValues ="CREATE TABLE IF NOT EXISTS discreteValues (\n"
                + " fk_environment_value_id integer PRIMARY KEY, \n"
                + " possible_values text, \n" 
                + " FOREIGN KEY (fk_environment_value_id) REFERENCES environmentValues(id) \n"
                + ");";
        
        String createTableEnvironmentValues ="CREATE TABLE IF NOT EXISTS environmentValues (\n"
                + " id integer PRIMARY KEY, \n"
                + " name text \n" 
                + ");";

        String createTableExpressions = "CREATE TABLE IF NOT EXISTS expressions (\n" 
                + " id integer PRIMARY KEY,\n"
                + " operators text, \n"
                + " fk_behaviour_id integer, \n"
                + " FOREIGN KEY (fk_behaviour_id) REFERENCES behaviours(id) \n"
                + ");";

        String createTableHistories = "CREATE TABLE IF NOT EXISTS histories (\n" 
                + " id integer PRIMARY KEY,\n"
                + " date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" 
                + " user text NOT NULL, \n"
                + " type text NOT NULL, \n" 
                + " action text NOT NULL" 
                + ");";

        String createTableRights = "CREATE TABLE IF NOT EXISTS rights (\n" 
                + " id integer PRIMARY KEY,\n"
                + " denomination text NOT NULL, \n"
                + " description text \n"
                + ");";

        String createTableRoles = "CREATE TABLE IF NOT EXISTS roles (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL \n"
                + ");";

        String createTableSensors = "CREATE TABLE IF NOT EXISTS sensors (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text, \n"
                + " fk_sensorCategory_id integer,\n"
                + " FOREIGN KEY (fk_sensorCategory_id) REFERENCES sensorCategories(id) \n"
                + ");";

        String createTableSensorCategories = "CREATE TABLE IF NOT EXISTS sensorCategories (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " description text \n"
                + ");";

        String createTableUsers = "CREATE TABLE IF NOT EXISTS users (\n" 
                + "	id integer PRIMARY KEY,\n"
                + "	pseudo text NOT NULL UNIQUE,\n" 
                + "	password text NOT NULL,\n"
                + " fk_role_id integer NOT NULL,\n"
                + " FOREIGN KEY (fk_role_id) REFERENCES Roles(id) \n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableActuators);
            stmt.execute(createTableActuatorCategories);
            stmt.execute(createTableAmbiences);
            stmt.execute(createTableBehaviours);
            stmt.execute(createTableBlocks);
            stmt.execute(createTableCommands);
            stmt.execute(createTableContinuousValues);
            stmt.execute(createTableDiscreteValues);
            stmt.execute(createTableEnvironmentValues);
            stmt.execute(createTableExpressions);
            stmt.execute(createTableHistories);
            stmt.execute(createTableRights);
            stmt.execute(createTableRoles);
            stmt.execute(createTableSensors);
            stmt.execute(createTableSensorCategories);
            stmt.execute(createTableUsers);
        } catch (SQLException e) {
            System.out.println("ERROR creating tables : " + e.getMessage());
        }

        // ==== CREATE RELATIONSHIP TABLES SQL
        String createOwns = "CREATE TABLE IF NOT EXISTS owns (\n" 
                + " fk_right_id integer, \n"
                + " fk_user_id integer, \n"
                + " PRIMARY KEY (fk_right_id, fk_user_id), \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES rights(id), \n"
                + " FOREIGN KEY (fk_user_id) REFERENCES users(id) \n"
                + ");";

        String createOwnsByDefault = "CREATE TABLE IF NOT EXISTS ownsbydefault (\n" 
                + " fk_right_id integer, \n"
                + " fk_role_id integer, \n"
                + " PRIMARY KEY (fk_right_id, fk_role_id), \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES rights(id), \n"
                + " FOREIGN KEY (fk_role_id) REFERENCES roles(id) \n"
                + ");";
        
        String createIsPartOf = "CREATE TABLE IF NOT EXISTS ispartof (\n" 
                + " fk_expression_id integer, \n"
                + " fk_block_id integer, \n"
                + " PRIMARY KEY (fk_expression_id, fk_block_id), \n"
                + " FOREIGN KEY (fk_expression_id) REFERENCES expressions(id), \n"
                + " FOREIGN KEY (fk_block_id) REFERENCES blocks(id) \n"
                + ");";
        
        String createIsMemberOf = "CREATE TABLE IF NOT EXISTS ismemberof (\n" 
                + " fk_expression_id_1 integer, \n"
                + " fk_expression_id_2 integer, \n"
                + " PRIMARY KEY (fk_expression_id_1, fk_expression_id_2), \n"
                + " FOREIGN KEY (fk_expression_id_1) REFERENCES expressions(id), \n"
                + " FOREIGN KEY (fk_expression_id_2) REFERENCES expressions(id) \n"
                + ");";
        
        String createComposes = "CREATE TABLE IF NOT EXISTS composes (\n"
                + " fk_behaviour_id integer, \n"
                + " fk_ambience_id integer, \n"
                + " PRIMARY KEY (fk_behaviour_id, fk_ambience_id), \n"
                + " FOREIGN KEY (fk_behaviour_id) REFERENCES behaviours(id), \n"
                + " FOREIGN KEY (fk_ambience_id) REFERENCES ambiences(id) \n"
                + ");";
        
        String createRequires = "CREATE TABLE IF NOT EXISTS requires (\n"
                + " fk_command_id integer, \n"
                + " fk_right_id integer, \n"
                + " PRIMARY KEY (fk_command_id, fk_right_id), \n"
                + " FOREIGN KEY (fk_command_id) REFERENCES commands(id), \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES rights(id) \n"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createOwns);
            stmt.execute(createOwnsByDefault);
            stmt.execute(createIsPartOf);
            stmt.execute(createIsMemberOf);
            stmt.execute(createComposes);
            stmt.execute(createRequires);
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
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
        String dropActuatorCategories = "DROP TABLE IF EXISTS actuatorCategories;";
        String dropAmbiences = "DROP TABLE IF EXISTS ambiences;";
        String dropAtomicActions = "DROP TABLE IF EXISTS atomicActions;";
        String dropBehaviours = "DROP TABLE IF EXISTS behaviours;";
        String dropBlocks = "DROP TABLE IF EXISTS blocks;";
        String dropCommands = "DROP TABLE IF EXISTS commands;";
        String dropComplexActions = "DROP TABLE IF EXISTS complexActions;";
        String dropContinuousEnvironmentVariables = "DROP TABLE IF EXISTS continuousEnvironmentVariables;";
        String dropContinuousValues = "DROP TABLE IF EXISTS continuousValues;";
        String dropDiscreteEnvironmentVariables = "DROP TABLE IF EXISTS discreteEnvironmentVariables;";
        String dropDiscreteValues = "DROP TABLE IF EXISTS discreteValues;";
        String dropEnvironmentValues = "DROP TABLE IF EXISTS environmentValues;";
        String dropEnvironmentVariables = "DROP TABLE IF EXISTS environmentVariables;";
        String dropExpressions = "DROP TABLE IF EXISTS expressions;";
        String dropHistories = "DROP TABLE IF EXISTS histories;";
        String dropRights = "DROP TABLE IF EXISTS rights;";
        String dropRoles = "DROP TABLE IF EXISTS roles;";
        String dropSensors = "DROP TABLE IF EXISTS sensors;";
        String dropSensorCategories = "DROP TABLE IF EXISTS sensorCategories;";
        String dropUsers = "DROP TABLE IF EXISTS users;";
        
        String dropOwns = "DROP TABLE IF EXISTS owns;";
        String dropOwnsByDefault = "DROP TABLE IF EXISTS ownsByDefault;";
        String dropIsPartOf = "DROP TABLE IF EXISTS isPartOf;";
        String dropIsMemberOf = "DROP TABLE IF EXISTS isMemberOf;";
        String dropComposes = "DROP TABLE IF EXISTS composes;";
        String dropRequires = "DROP TABLE IF EXISTS requires;";
        String dropExecutes = "DROP TABLE IF EXISTS executes;";
        String dropLaunches = "DROP TABLE IF EXISTS launches;";
        String dropGathers = "DROP TABLE IF EXISTS gathers;";
        String dropIsA = "DROP TABLE IF EXISTS isA;";        

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(dropActuators);
            stmt.execute(dropActuatorCategories);
            stmt.execute(dropAmbiences);
            stmt.execute(dropAtomicActions);
            stmt.execute(dropBehaviours);
            stmt.execute(dropBlocks);
            stmt.execute(dropCommands);
            stmt.execute(dropComplexActions);
            stmt.execute(dropContinuousEnvironmentVariables);
            stmt.execute(dropContinuousValues);
            stmt.execute(dropDiscreteEnvironmentVariables);
            stmt.execute(dropDiscreteValues);
            stmt.execute(dropEnvironmentValues);
            stmt.execute(dropEnvironmentVariables);
            stmt.execute(dropExpressions);
            stmt.execute(dropHistories);
            stmt.execute(dropRights);
            stmt.execute(dropRoles);
            stmt.execute(dropSensors);
            stmt.execute(dropSensorCategories);
            stmt.execute(dropUsers);
            
            stmt.execute(dropOwns);
            stmt.execute(dropOwnsByDefault);
            stmt.execute(dropIsPartOf);
            stmt.execute(dropIsMemberOf);
            stmt.execute(dropComposes);
            stmt.execute(dropRequires);
            stmt.execute(dropExecutes);
            stmt.execute(dropLaunches);
            stmt.execute(dropGathers);
            stmt.execute(dropIsA);
            
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
        
        String createTableAtomicActions = "CREATE TABLE IF NOT EXISTS atomicActions (\n" 
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL, \n"
                + " executable text NOT NULL, \n"
                + " fk_actuator_id integer, \n"
                + " FOREIGN KEY (fk_actuator_id) REFERENCES actuators(id) \n"
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
        
        String createTableComplexActions = "CREATE TABLE IF NOT EXISTS complexActions (\n" 
                + " id integer PRIMARY KEY, \n"
                + " name text NOT NULL \n"
                + ");";
        
        String createTableContinuousEnvironmentVariables ="CREATE TABLE IF NOT EXISTS continuousEnvironmentVariables (\n"
                + " fk_environment_variable_id integer PRIMARY KEY, \n"
                + " value_min real, \n"
                + " value_max real, \n"
                + " current_value real, \n"
                + " precision real, \n"
                + " FOREIGN KEY (fk_environment_variable_id) REFERENCES environmentVariables(id) \n"
                + ");";
        
        String createTableContinuousValues ="CREATE TABLE IF NOT EXISTS continuousValues (\n"
                + " fk_environment_value_id integer PRIMARY KEY, \n"
                + " min real, \n"
                + " max real, \n"
                + " precision real, \n"
                + " FOREIGN KEY (fk_environment_value_id) REFERENCES environmentValues(id) \n"
                + ");";
        
        String createTableDiscreteEnvironmentVariables ="CREATE TABLE IF NOT EXISTS discreteEnvironmentVariables (\n"
                + " fk_environment_variable_id integer PRIMARY KEY, \n"
                + " current_value text, \n"
                + " possible_values text, \n" 
                + " FOREIGN KEY (fk_environment_variable_id) REFERENCES environmentVariables(id) \n"
                + ");";
        
        String createTableDiscreteValues ="CREATE TABLE IF NOT EXISTS discreteValues (\n"
                + " fk_environment_value_id integer PRIMARY KEY, \n"
                + " possible_values text, \n" 
                + " FOREIGN KEY (fk_environment_value_id) REFERENCES environmentValues(id) \n"
                + ");";
        
        String createTableEnvironmentValues ="CREATE TABLE IF NOT EXISTS environmentValues (\n"
                + " id integer PRIMARY KEY, \n"
                + " name text, \n"
                + " fk_command_id integer, \n"
                + " FOREIGN KEY (fk_command_id) REFERENCES commands(id) \n"
                + ");";
        
        String createTableEnvironmentVariables ="CREATE TABLE IF NOT EXISTS environmentVariables (\n"
                + " id integer PRIMARY KEY, \n"
                + " name text, \n" 
                + " description text, \n"
                + " unit text, \n"
                + " fk_sensor_id integer, \n"
                + " FOREIGN KEY (fk_sensor_id) REFERENCES sensors(id) \n"
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
            stmt.execute(createTableAtomicActions);
            stmt.execute(createTableBehaviours);
            stmt.execute(createTableBlocks);
            stmt.execute(createTableCommands);
            stmt.execute(createTableComplexActions);
            stmt.execute(createTableContinuousValues);
            stmt.execute(createTableDiscreteEnvironmentVariables);
            stmt.execute(createTableDiscreteValues);
            stmt.execute(createTableContinuousEnvironmentVariables);            
            stmt.execute(createTableEnvironmentValues);
            stmt.execute(createTableEnvironmentVariables);            
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

        String createOwnsByDefault = "CREATE TABLE IF NOT EXISTS ownsByDefault (\n" 
                + " fk_right_id integer, \n"
                + " fk_role_id integer, \n"
                + " PRIMARY KEY (fk_right_id, fk_role_id), \n"
                + " FOREIGN KEY (fk_right_id) REFERENCES rights(id), \n"
                + " FOREIGN KEY (fk_role_id) REFERENCES roles(id) \n"
                + ");";
        
        String createIsPartOf = "CREATE TABLE IF NOT EXISTS isPartOf (\n" 
                + " fk_expression_id integer, \n"
                + " fk_block_id integer, \n"
                + " PRIMARY KEY (fk_expression_id, fk_block_id), \n"
                + " FOREIGN KEY (fk_expression_id) REFERENCES expressions(id), \n"
                + " FOREIGN KEY (fk_block_id) REFERENCES blocks(id) \n"
                + ");";
        
        String createIsMemberOf = "CREATE TABLE IF NOT EXISTS isMemberOf (\n" 
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
        
        String createExecutes = "CREATE TABLE IF NOT EXISTS executes (\n"
                + " fk_behaviour_id integer, \n"
                + " fk_complexAction_id integer, \n"
                + " PRIMARY KEY (fk_behaviour_id, fk_complexAction_id), \n"
                + " FOREIGN KEY (fk_behaviour_id) REFERENCES behaviours(id), \n"
                + " FOREIGN KEY (fk_complexAction_id) REFERENCES complexActions(id) \n"
                + ");";
        
        String createLaunches = "CREATE TABLE IF NOT EXISTS launches (\n"
                + " fk_behaviour_id integer, \n"
                + " fk_atomicAction_id integer, \n"
                + " PRIMARY KEY (fk_behaviour_id, fk_atomicAction_id), \n"
                + " FOREIGN KEY (fk_behaviour_id) REFERENCES behaviours(id), \n"
                + " FOREIGN KEY (fk_atomicAction_id) REFERENCES atomicActions(id) \n"
                + ");";
        
        String createGathers = "CREATE TABLE IF NOT EXISTS gathers (\n"
                + " fk_complexAction_id integer, \n"
                + " fk_atomicAction_id integer, \n"
                + " PRIMARY KEY (fk_complexAction_id, fk_atomicAction_id), \n"
                + " FOREIGN KEY (fk_complexAction_id) REFERENCES complexActions(id), \n"
                + " FOREIGN KEY (fk_atomicAction_id) REFERENCES atomicActions(id) \n"
                + ");";
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createOwns);
            stmt.execute(createOwnsByDefault);
            stmt.execute(createIsPartOf);
            stmt.execute(createIsMemberOf);
            stmt.execute(createComposes);
            stmt.execute(createRequires);
            stmt.execute(createExecutes);
            stmt.execute(createLaunches);
            stmt.execute(createGathers);
        } catch (SQLException e) {
            System.out.println("ERROR creating relationship tables : " + e.getMessage());
        }

    }

    // ================= //
    // ==== SEEDERS ==== //
    // ================= //

    private static void insertActuators() {
        String insertActuator1 = "INSERT INTO actuators ('id', 'name', 'description','fk_actuatorCategory_id') VALUES (1, 'Philips 70W Bulb','Powerfull yellow bulb.',1);";
        String insertActuator2 = "INSERT INTO actuators ('id', 'name', 'description','fk_actuatorCategory_id') VALUES (2, 'Samsaoule 500W heater','So hot!',2);";
        String insertActuator3 = "INSERT INTO actuators ('id', 'name', 'description','fk_actuatorCategory_id') VALUES (3, 'Samsaoule 700W heater','So hot!',2);";
        String insertActuator4 = "INSERT INTO actuators ('id', 'name', 'description','fk_actuatorCategory_id') VALUES (4, 'Teasla 200','The sun-made tea.',3);";
        String insertActuator5 = "INSERT INTO actuators ('id', 'name', 'description','fk_actuatorCategory_id') VALUES (5, 'Soli 100W Bulb','The sun is yours.',1);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertActuator1);
            stmt.execute(insertActuator2);
            stmt.execute(insertActuator3);
            stmt.execute(insertActuator4);
            stmt.execute(insertActuator5);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Actuators : " + e.getMessage());
        }
    }

    private static void insertActuatorCategories() {
        String insertActuatorCat1 = "INSERT INTO actuatorCategories ('id','name','description') VALUES (1,'Bulb','Provide light.');";
        String insertActuatorCat2 = "INSERT INTO actuatorCategories ('id','name','description') VALUES (2,'Heater','Heat the room.');";
        String insertActuatorCat3 = "INSERT INTO actuatorCategories ('id','name','description') VALUES (3,'Cooking','Make food');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertActuatorCat1);
            stmt.execute(insertActuatorCat2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ActuatorCategories : " + e.getMessage());
        }
    }
    
    private static void insertCommands() {
        String insertCommand1 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (1,'switch on',1);";
        String insertCommand2 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (2,'switch off',1);";
        String insertCommand3 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (3,'set temperature',2);";
        String insertCommand4 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (4,'set temperature',3);";
        String insertCommand5 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (5,'make tea',4);";
        String insertCommand6 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (6,'switch on',5);";
        String insertCommand7 = "INSERT INTO commands ('id', 'name', 'fk_actuator_id') VALUES (7,'switch off',5);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertCommand1);
            stmt.execute(insertCommand2);
            stmt.execute(insertCommand3);
            stmt.execute(insertCommand4);
            stmt.execute(insertCommand5);
            stmt.execute(insertCommand6);
            stmt.execute(insertCommand7);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Commands : " + e.getMessage());
        }       
    }

    private static void insertEnvironmentValues() {
        String insertEnvironmentValue1 = "INSERT INTO environmentValues ('id', 'name', 'fk_command_id') VALUES (1,'temperature',3);";
        String insertEnvironmentValue2 = "INSERT INTO environmentValues ('id', 'name', 'fk_command_id') VALUES (2,'temperature',4);";
        String insertEnvironmentValue3 = "INSERT INTO environmentValues ('id', 'name', 'fk_command_id') VALUES (3,'light',1);";
        String insertEnvironmentValue4 = "INSERT INTO environmentValues ('id', 'name', 'fk_command_id') VALUES (4,'light',2);";
        String insertEnvironmentValue5 = "INSERT INTO environmentValues ('id', 'name', 'fk_command_id') VALUES (5,'tea temperature',7);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertEnvironmentValue1);
            stmt.execute(insertEnvironmentValue2);
            stmt.execute(insertEnvironmentValue3);
            stmt.execute(insertEnvironmentValue4);
            stmt.execute(insertEnvironmentValue5);
        } catch (SQLException e) {
            System.out.println("ERROR inserting EnvironmentValues : " + e.getMessage());
        }
    }

    private static void insertHistories() {
        String insertHistory = "INSERT INTO histories ('date', 'type', 'action', 'user') VALUES ('2018-03-10 08:42:42', 'command', 'Switch light on', 'owner');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertHistory);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Histories : " + e.getMessage());
        }
    }

    private static void insertOwns() {
        String insertOwns10 = "INSERT INTO owns ('fk_user_id','fk_right_id') VALUES (1,1);";
        String insertOwns11 = "INSERT INTO owns ('fk_user_id','fk_right_id') VALUES (1,2);";
        String insertOwns20 = "INSERT INTO owns ('fk_user_id','fk_right_id') VALUES (2,1);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertOwns10);
            stmt.execute(insertOwns11);
            stmt.execute(insertOwns20);
        } catch (SQLException e) {
            System.out.println("ERROR inserting OwnsByDefault : " + e.getMessage());
        }
    }
    
    private static void insertOwnsByDefault() {
        String insertOwnsByDefault10 = "INSERT INTO ownsByDefault ('fk_role_id','fk_right_id') VALUES (1,1);";
        String insertOwnsByDefault11 = "INSERT INTO ownsByDefault ('fk_role_id','fk_right_id') VALUES (1,2);";
        String insertOwnsByDefault20 = "INSERT INTO ownsByDefault ('fk_role_id','fk_right_id') VALUES (2,1);";
        String insertOwnsByDefault21 = "INSERT INTO ownsByDefault ('fk_role_id','fk_right_id') VALUES (2,2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertOwnsByDefault10);
            stmt.execute(insertOwnsByDefault11);
            stmt.execute(insertOwnsByDefault20);
            stmt.execute(insertOwnsByDefault21);
        } catch (SQLException e) {
            System.out.println("ERROR inserting OwnsByDefault : " + e.getMessage());
        }
    }
    
    private static void insertRights() {
        String insertRightLRLights = "INSERT INTO rights ('id', 'denomination', 'description') VALUES (1, 'Switch the living room lights.','Allow to switch on and off the lights of the living room.');";
        String insertRightKHeats = "INSERT INTO rights ('id','denomination', 'description') VALUES (2, 'Set kitchen-heater temperature','You can change the temperature of the kitchen.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRightLRLights);
            stmt.execute(insertRightKHeats);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Rights : " + e.getMessage());
        }
    }
    
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

    private static void insertSensors() {
        String insertSensor1 = "INSERT INTO sensors ('name', 'description') VALUES ('Presensor3000','Sense the presence of something (10m range)');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensor1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }

    private static void insertSensorCategories() {
        String insertSensorCat1 = "INSERT INTO sensorCategories ('name', 'description') VALUES ('Movement detector','Detect movement.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensorCat1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
        insertSensorCat1 = "INSERT INTO sensorCategories ('name', 'description') VALUES ('Light detector','Detect degree of light.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensorCat1);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }

    private static void insertUsers() {
        String insertUser1 = "INSERT INTO users ('pseudo', 'password', 'fk_role_id') VALUES ('owner', 'password', 1);";
        String insertUser2 = "INSERT INTO users ('pseudo', 'password', 'fk_role_id') VALUES ('Rifhice', 'password', 2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertUser1);
            stmt.execute(insertUser2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Users : " + e.getMessage());
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
        insertHistories();
        // Users
        insertRoles();
        insertRights();
        insertUsers();
        insertOwnsByDefault();
        insertOwns();
        
        insertCommands();
        
        insertEnvironmentValues();
        
        insertActuatorCategories();
        insertActuators();
        insertSensorCategories();
        insertSensors();
        System.out.println("Data inserted.");

        System.out.println("**** Process complete ! ****");
    }
}
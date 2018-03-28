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
    
    private static void dropDatabase() {
        String dropActuators = "DROP TABLE IF EXISTS actuators;";
        String dropActuatorCategories = "DROP TABLE IF EXISTS actuatorCategories;";
        String dropAmbiences = "DROP TABLE IF EXISTS ambiences;";
        String dropAtomicActions = "DROP TABLE IF EXISTS atomicActions;";
        String dropBehaviours = "DROP TABLE IF EXISTS behaviours;";
        String dropBlocks = "DROP TABLE IF EXISTS blocks;";
        String dropCommands = "DROP TABLE IF EXISTS commands;";
        String dropComplexActions = "DROP TABLE IF EXISTS complexActions;";
        String dropContinuousEnvironmentVariables = "DROP TABLE IF EXISTS continuousVValues;";
        String dropContinuousCommandValues = "DROP TABLE IF EXISTS continuousCommandValues;";
        String dropDiscreteEnvironmentVariables = "DROP TABLE IF EXISTS discreteVValues;";
        String dropDiscreteCommandValues = "DROP TABLE IF EXISTS discreteCommandValues;";
        String dropCommandValues = "DROP TABLE IF EXISTS commandValues;";
        String dropEnvironmentVariables = "DROP TABLE IF EXISTS environmentVariables;";
        String dropExpressions = "DROP TABLE IF EXISTS expressions;";
        String dropHistories = "DROP TABLE IF EXISTS histories;";
        String dropRights = "DROP TABLE IF EXISTS rights;";
        String dropRoles = "DROP TABLE IF EXISTS roles;";
        String dropSensors = "DROP TABLE IF EXISTS sensors;";
        String dropSensorCategories = "DROP TABLE IF EXISTS sensorCategories;";
        String dropUsers = "DROP TABLE IF EXISTS users;";
        String dropValues = "DROP TABLE IF EXISTS vvalues;";
        
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
            stmt.execute(dropContinuousCommandValues);
            stmt.execute(dropDiscreteEnvironmentVariables);
            stmt.execute(dropDiscreteCommandValues);
            stmt.execute(dropCommandValues);
            stmt.execute(dropEnvironmentVariables);
            stmt.execute(dropExpressions);
            stmt.execute(dropHistories);
            stmt.execute(dropRights);
            stmt.execute(dropRoles);
            stmt.execute(dropSensors);
            stmt.execute(dropSensorCategories);
            stmt.execute(dropUsers);
            stmt.execute(dropValues);
            
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
    }
    
    private static void createDatabase() {       
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
                + " description text, \n"
                + " is_activated integer, \n"
                + " fk_expression_id integer, \n"
                + " FOREIGN KEY (fk_expression_id) REFERENCES expressions(id) \n"
                + ");";

        String createTableBlocks = "CREATE TABLE IF NOT EXISTS blocks (\n" 
                + " id integer PRIMARY KEY, \n"
                + " operator text NOT NULL, \n"
                + " fk_environmentVariable_id integer, \n"
                + " fk_vvalue_id integer, \n"
                + " FOREIGN KEY (fk_environmentVariable_id) REFERENCES environmentVariables(id) \n"
                + " FOREIGN KEY (fk_vvalue_id) REFERENCES vvalues(id) \n"
                + ");";
        
        String createTableCommands = "CREATE TABLE IF NOT EXISTS commands (\n" 
                + " id integer PRIMARY KEY, \n"
                + " name text NOT NULL, \n"
                + " key text NOT NULL, \n"
                + " description text, \n"
                + " fk_actuator_id integer, \n"
                + " FOREIGN KEY (fk_actuator_id) REFERENCES actuators(id) \n"
                + ");";
        
        String createTableComplexActions = "CREATE TABLE IF NOT EXISTS complexActions (\n" 
                + " id integer PRIMARY KEY, \n"
                + " name text NOT NULL \n"
                + ");";
        
        String createTableContinuousVValues ="CREATE TABLE IF NOT EXISTS continuousVValues (\n"
                + " fk_vvalue_id integer PRIMARY KEY, \n"
                + " value_min real, \n"
                + " value_max real, \n"
                + " current_value real, \n"
                + " precision real, \n"
                + " FOREIGN KEY (fk_vvalue_id) REFERENCES vvalues(id) \n"
                + ");";
        
        String createTableContinuousCommandValues ="CREATE TABLE IF NOT EXISTS continuousCommandValues (\n"
                + " fk_commandValue_id integer PRIMARY KEY, \n"
                + " min real, \n"
                + " max real, \n"
                + " precision real, \n"
                + " FOREIGN KEY (fk_commandValue_id) REFERENCES commandValues(id) \n"
                + ");";
        
        String createTableDiscreteVValues ="CREATE TABLE IF NOT EXISTS discreteVValues (\n"
                + " fk_vvalue_id integer PRIMARY KEY, \n"
                + " current_value text, \n"
                + " possible_values text, \n" 
                + " FOREIGN KEY (fk_vvalue_id) REFERENCES vvalues(id) \n"
                + ");";       
        
        String createTableDiscreteCommandValues ="CREATE TABLE IF NOT EXISTS discreteCommandValues (\n"
                + " fk_commandValue_id integer PRIMARY KEY, \n"
                + " possible_values text, \n" 
                + " FOREIGN KEY (fk_commandValue_id) REFERENCES commandValues(id) \n"
                + ");";
        
        String createTableCommandValues ="CREATE TABLE IF NOT EXISTS commandValues (\n"
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
                + " fk_vvalue_id integer, \n"
                + " FOREIGN KEY (fk_vvalue_id) REFERENCES vvalues(id), \n"
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
        
        String createTableVValues = "CREATE TABLE IF NOT EXISTS vvalues (\n" 
                + " id integer PRIMARY KEY \n"
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
            stmt.execute(createTableContinuousCommandValues);
            stmt.execute(createTableDiscreteVValues);
            stmt.execute(createTableDiscreteCommandValues);
            stmt.execute(createTableContinuousVValues);            
            stmt.execute(createTableCommandValues);
            stmt.execute(createTableEnvironmentVariables);            
            stmt.execute(createTableExpressions);
            stmt.execute(createTableHistories);
            stmt.execute(createTableRights);
            stmt.execute(createTableRoles);
            stmt.execute(createTableSensors);
            stmt.execute(createTableSensorCategories);
            stmt.execute(createTableUsers);
            stmt.execute(createTableVValues);
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
            stmt.execute(insertActuatorCat3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ActuatorCategories : " + e.getMessage());
        }
    }
    
    private static void insertAmbiences() {
        String insertAmbience1 = "INSERT INTO ambiences ('id','name','description') VALUES (1,'Winter','Winter is here...');";
        String insertAmbience2 = "INSERT INTO ambiences ('id','name','description') VALUES (2,'Lounge','Very nice ambience. Beautiful.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertAmbience1);
            stmt.execute(insertAmbience2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Ambiences : " + e.getMessage());
        }
    }
    
    private static void insertAtomicActions() {
        String insertAtomicAction1 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (1, 'Switch On','switch on',1);";
        String insertAtomicAction2 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (2, 'Switch Off','switch off',1);";
        String insertAtomicAction3 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (3, 'Set Temperature 22','setTemperature 22',2);";
        String insertAtomicAction4 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (4, 'Set Temperature 24','setTemperature 24',3);";
        String insertAtomicAction5 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (5, 'Make tea','make tea',4);";
        String insertAtomicAction6 = "INSERT INTO atomicActions ('id', 'name', 'executable', 'fk_actuator_id') VALUES (6, 'Switch On','switch on',5);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertAtomicAction1);
            stmt.execute(insertAtomicAction2);
            stmt.execute(insertAtomicAction3);
            stmt.execute(insertAtomicAction4);
            stmt.execute(insertAtomicAction5);
            stmt.execute(insertAtomicAction6);
        } catch (SQLException e) {
            System.out.println("ERROR inserting AtomicActions : " + e.getMessage());
        }
    }
    
    private static void insertBehaviours() {
        String insertBehaviour1 = "INSERT INTO behaviours ('id','name', 'description', 'is_activated', 'fk_expression_id') VALUES (1,'Light presence kitchen', 'Automatic light in kitchen', 1, 1);";
        String insertBehaviour2 = "INSERT INTO behaviours ('id','name', 'description', 'is_activated', 'fk_expression_id') VALUES (2,'So hot','Cool the house when it''s really hot',0,2);";
        String insertBehaviour3 = "INSERT INTO behaviours ('id','name', 'description', 'is_activated', 'fk_expression_id') VALUES (3,'It''s cold : I want tea', 'Make a tea if it''s cold',1,3);";      
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertBehaviour1);
            stmt.execute(insertBehaviour2);
            stmt.execute(insertBehaviour3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Behaviours : " + e.getMessage());
        }
    }
    
    private static void insertBlocks() {
        String insertBlock1 = "INSERT INTO blocks ('id','operator', 'fk_environmentVariable_id', 'fk_vvalue_id') VALUES (1,'<', 1, 1);";
        String insertBlock2 = "INSERT INTO blocks ('id','operator', 'fk_environmentVariable_id', 'fk_vvalue_id') VALUES (2,'==', 2, 2);";
        String insertBlock3 = "INSERT INTO blocks ('id','operator', 'fk_environmentVariable_id', 'fk_vvalue_id') VALUES (3,'>=', 3, 3);";
        String insertBlock4 = "INSERT INTO blocks ('id','operator', 'fk_environmentVariable_id', 'fk_vvalue_id') VALUES (4,'>', 1, 1);";
        String insertBlock5 = "INSERT INTO blocks ('id','operator', 'fk_environmentVariable_id', 'fk_vvalue_id') VALUES (5,'!=', 2, 2);";        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertBlock1);
            stmt.execute(insertBlock2);
            stmt.execute(insertBlock3);
            stmt.execute(insertBlock4);
            stmt.execute(insertBlock5);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ActuatorCategories : " + e.getMessage());
        }
    }
    
    private static void insertCommands() {
        String insertCommand1 = "INSERT INTO commands ('id', 'name', 'key', 'description', 'fk_actuator_id') VALUES (1,'switch','sw','Switch on or off',1);";
        String insertCommand2 = "INSERT INTO commands ('id', 'name', 'key', 'description', 'fk_actuator_id') VALUES (2,'set temperature','set','',2);";
        String insertCommand3 = "INSERT INTO commands ('id', 'name', 'key', 'description', 'fk_actuator_id') VALUES (3,'set temperature','set','',3);";
        String insertCommand4 = "INSERT INTO commands ('id', 'name', 'key', 'description', 'fk_actuator_id') VALUES (4,'make tea','mkt','',4);";
        String insertCommand5 = "INSERT INTO commands ('id', 'name', 'key', 'description', 'fk_actuator_id') VALUES (5,'switch','sw','Switch on or off',5);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertCommand1);
            stmt.execute(insertCommand2);
            stmt.execute(insertCommand3);
            stmt.execute(insertCommand4);
            stmt.execute(insertCommand5);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Commands : " + e.getMessage());
        }       
    }
    
    private static void insertCommandValues() {
        String insertCommandValue1 = "INSERT INTO commandValues ('id', 'name', 'fk_command_id') VALUES (1,'temperature',3);";
        String insertCommandValue2 = "INSERT INTO commandValues ('id', 'name', 'fk_command_id') VALUES (2,'light',1);";
        String insertCommandValue3 = "INSERT INTO commandValues ('id', 'name', 'fk_command_id') VALUES (3,'hey',2);";
        String insertCommandValue4 = "INSERT INTO commandValues ('id', 'name', 'fk_command_id') VALUES (4,'jesaispas',2);";
        String insertCommandValue5 = "INSERT INTO commandValues ('id', 'name', 'fk_command_id') VALUES (5,'superargument',2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertCommandValue1);
            stmt.execute(insertCommandValue2);
            stmt.execute(insertCommandValue3);
            stmt.execute(insertCommandValue4);
            stmt.execute(insertCommandValue5);
        } catch (SQLException e) {
            System.out.println("ERROR inserting CommandValues : " + e.getMessage());
        }
    }
    
    private static void insertComposes() {
        String insertComposes1 = "INSERT INTO composes ('fk_ambience_id', 'fk_behaviour_id') VALUES (1,2);";
        String insertComposes2 = "INSERT INTO composes ('fk_ambience_id', 'fk_behaviour_id') VALUES (2,1);";
        String insertComposes3 = "INSERT INTO composes ('fk_ambience_id', 'fk_behaviour_id') VALUES (2,3);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertComposes1);
            stmt.execute(insertComposes2);
            stmt.execute(insertComposes3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Composes : " + e.getMessage());
        }
    }

    private static void insertContinuousCommandValues() {
        String insertContinuousCommandValue1 = "INSERT INTO continuousCommandValues ('min', 'max', 'precision', 'fk_commandValue_id') VALUES (10, 30, 0.5, 1);";
        String insertContinuousCommandValue2 = "INSERT INTO continuousCommandValues ('min', 'max', 'precision', 'fk_commandValue_id') VALUES (10, 30, 1.0, 2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertContinuousCommandValue1);
            stmt.execute(insertContinuousCommandValue2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ContinuousCommandValues : " + e.getMessage());
        }
    }
    
    private static void insertContinuousVValues() {
        String insertContinuousVValue1 = "INSERT INTO continuousVValues ('value_min', 'value_max', 'current_value', 'precision', 'fk_vvalue_id') VALUES (0, 30, 19, 1.0, 1);";
        String insertContinuousVValue2 = "INSERT INTO continuousVValues ('value_min', 'value_max', 'current_value', 'precision', 'fk_vvalue_id') VALUES (10, 50, 25, 1.0, 3);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertContinuousVValue1);
            stmt.execute(insertContinuousVValue2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting ContinuousVValues : " + e.getMessage());
        }
    }
    
    private static void insertDiscreteCommandValues() {
        String insertDiscreteValue1 = "INSERT INTO discreteCommandValues ('possible_values', 'fk_commandValue_id') VALUES ('{possibleValues : [on,off]}',3);";
        String insertDiscreteValue2 = "INSERT INTO discreteCommandValues ('possible_values', 'fk_commandValue_id') VALUES ('{possibleValues : [on,off]}',4);";
        String insertDiscreteValue3 = "INSERT INTO discreteCommandValues ('possible_values', 'fk_commandValue_id') VALUES ('{possibleValues : [very hot,hot,ice]}',5);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertDiscreteValue1);
            stmt.execute(insertDiscreteValue2);
            stmt.execute(insertDiscreteValue3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting DiscreteCommandValues : " + e.getMessage());
        }
    }
    
    private static void insertDiscreteVValues() {
        String insertDiscreteVValue1 = "INSERT INTO discreteVValues ('possible_values', 'current_value', 'fk_vvalue_id') VALUES ('{possibleValues : [\"true\", \"false\"]}', 'true', 2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertDiscreteVValue1);
         
        } catch (SQLException e) {
            System.out.println("ERROR inserting DiscreteVValues : " + e.getMessage());
        }
    }
    
    private static void insertEnvironmentVariables() {
        String insertEnvironmentVariable1 = "INSERT INTO environmentVariables ('id', 'name', 'description', 'unit', 'fk_sensor_id', 'fk_vvalue_id') VALUES (1,'temperature','Variable temperature', '�C', '3', 1);";
        String insertEnvironmentVariable2 = "INSERT INTO environmentVariables ('id', 'name', 'description', 'unit', 'fk_sensor_id', 'fk_vvalue_id') VALUES (2,'presence','Is anybody in there ?', '', '1', '2');";
        String insertEnvironmentVariable3 = "INSERT INTO environmentVariables ('id', 'name', 'description', 'unit', 'fk_sensor_id', 'fk_vvalue_id') VALUES (3,'light','Measure the light intensity', 'L', '2', '3');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertEnvironmentVariable1);
            stmt.execute(insertEnvironmentVariable2);
            stmt.execute(insertEnvironmentVariable3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting EnvironmentVariables : " + e.getMessage());
        }
    }
    
    private static void insertExpressions() {
        String insertExpression1 = "INSERT INTO expressions ('id', 'operators', 'fk_behaviour_id') VALUES (1,'{operators: [&&,||]}',1);";
        String insertExpression2 = "INSERT INTO expressions ('id', 'operators', 'fk_behaviour_id') VALUES (2,'{operators: [||]}',2);";
        String insertExpression3 = "INSERT INTO expressions ('id', 'operators', 'fk_behaviour_id') VALUES (3,'{operators: [&&]}',3);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertExpression1);
            stmt.execute(insertExpression2);
            stmt.execute(insertExpression3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Expressions : " + e.getMessage());
        }
    }

    private static void insertHistories() {
        String insertHistory1 = "INSERT INTO histories ('date', 'type', 'action', 'user') VALUES ('2018-03-10 08:42:42', 'command', 'Switch light on', 'owner');";
        String insertHistory2 = "INSERT INTO histories ('date', 'type', 'action', 'user') VALUES ('2018-03-12 13:10:12', 'command', 'Set temperature to 30C', 'Rifhice');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertHistory1);
            stmt.execute(insertHistory2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Histories : " + e.getMessage());
        }
    }
    
    private static void insertIsPartOf() {
        String insertIsPartOf1 = "INSERT INTO isPartOf ('fk_block_id', 'fk_expression_id') VALUES (1,1);";
        String insertIsPartOf2 = "INSERT INTO isPartOf ('fk_block_id', 'fk_expression_id') VALUES (2,1);";
        String insertIsPartOf3 = "INSERT INTO isPartOf ('fk_block_id', 'fk_expression_id') VALUES (2,3);";
        String insertIsPartOf4 = "INSERT INTO isPartOf ('fk_block_id', 'fk_expression_id') VALUES (4,2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertIsPartOf1);
            stmt.execute(insertIsPartOf2);
            stmt.execute(insertIsPartOf3);
            stmt.execute(insertIsPartOf4);
        } catch (SQLException e) {
            System.out.println("ERROR inserting IsPartOf : " + e.getMessage());
        }
    }
    
    private static void insertLaunches() {
        String insertLaunches1 = "INSERT INTO launches ('fk_behaviour_id', 'fk_atomicAction_id') VALUES (1,1);";
        String insertLaunches2 = "INSERT INTO launches ('fk_behaviour_id', 'fk_atomicAction_id') VALUES (1,6);";
        String insertLaunches3 = "INSERT INTO launches ('fk_behaviour_id', 'fk_atomicAction_id') VALUES (2,3);";
        String insertLaunches4 = "INSERT INTO launches ('fk_behaviour_id', 'fk_atomicAction_id') VALUES (3,5);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertLaunches1);
            stmt.execute(insertLaunches2);
            stmt.execute(insertLaunches3);
            stmt.execute(insertLaunches4);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Launches : " + e.getMessage());
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
    
    private static void insertRequires() {
        String insertRequire1 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (1,1);";
        String insertRequire2 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (2,1);";
        String insertRequire3 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (6,1);";
        String insertRequire4 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (7,1);";
        String insertRequire5 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (3,2);";
        String insertRequire6 = "INSERT INTO requires ('fk_command_id','fk_right_id') VALUES (4,2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRequire1);
            stmt.execute(insertRequire2);
            stmt.execute(insertRequire3);
            stmt.execute(insertRequire4);
            stmt.execute(insertRequire5);
            stmt.execute(insertRequire6);
        } catch (SQLException e) {
            System.out.println("ERROR inserting OwnsByDefault : " + e.getMessage());
        }
    }
    
    private static void insertRights() {
        String insertRightLRLights = "INSERT INTO rights ('id', 'denomination', 'description') VALUES (1, 'Switch the living room lights.','Allow to switch on and off the lights of the living room.');";
        String insertRightKHeats = "INSERT INTO rights ('id','denomination', 'description') VALUES (2, 'Set kitchen-heater temperature.','You can change the temperature of the kitchen.');";
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
        String insertRoleGuest = "INSERT INTO roles ('id', 'name') VALUES (3,'guest');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertRoleOwner);
            stmt.execute(insertRoleFamilyMember);
            stmt.execute(insertRoleGuest);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Roles : " + e.getMessage());
        }
    }

    private static void insertSensors() {
        String insertSensor1 = "INSERT INTO sensors ('id', 'name', 'description', 'fk_sensorCategory_id') VALUES (1, 'Presensor3000','Sense the presence of something (10m range)',1);";
        String insertSensor2 = "INSERT INTO sensors ('id', 'name', 'description', 'fk_sensorCategory_id') VALUES (2, 'LightCaptor5000','Measure the light intensity', 2);";
        String insertSensor3 = "INSERT INTO sensors ('id', 'name', 'description', 'fk_sensorCategory_id') VALUES (3, 'Thermometer450','What is the temperature ?', 3);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensor1);
            stmt.execute(insertSensor2);
            stmt.execute(insertSensor3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Sensors : " + e.getMessage());
        }
    }

    private static void insertSensorCategories() {
        String insertSensorCat1 = "INSERT INTO sensorCategories ('id', 'name', 'description') VALUES (1, 'Movement detector','Detect movement.');";
        String insertSensorCat2 = "INSERT INTO sensorCategories ('id', 'name', 'description') VALUES (2, 'Luminosity sensor','Measure the light intensity.');";
        String insertSensorCat3 = "INSERT INTO sensorCategories ('id', 'name', 'description') VALUES (3, 'Thermometer','Measure the temperature.');";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertSensorCat1);
            stmt.execute(insertSensorCat2);
            stmt.execute(insertSensorCat3);
        } catch (SQLException e) {
            System.out.println("ERROR inserting SensorCategories : " + e.getMessage());
        }
    }

    private static void insertUsers() {
        String insertUser1 = "INSERT INTO users ('pseudo', 'password', 'fk_role_id') VALUES ('The Boss', 'password', 1);";
        String insertUser2 = "INSERT INTO users ('pseudo', 'password', 'fk_role_id') VALUES ('Rifhice', 'password', 2);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertUser1);
            stmt.execute(insertUser2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Users : " + e.getMessage());
        }
    }
    
    private static void insertComplexAction() {
       String insertComplexAction1 = "INSERT INTO complexactions ('name') VALUES ('All lights');";
       String insertComplexAction2 = "INSERT INTO complexactions ('name') VALUES ('All heat');";
       try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertComplexAction1);
            stmt.execute(insertComplexAction2);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Users : " + e.getMessage());
        }
    }
    
    private static void insertGathers() {
        String insertGathers = "INSERT INTO gathers ('fk_complexAction_id','fk_atomicAction_id') VALUES (1,1);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertGathers);
        } catch (SQLException e) {
            System.out.println("ERROR inserting Ambiences : " + e.getMessage());
        }
    }
    
    private static void insertVValues() {
        String insertVValue1 = "INSERT INTO vvalues ('id') VALUES (1);";
        String insertVValue2 = "INSERT INTO vvalues ('id') VALUES (2);";
        String insertVValue3 = "INSERT INTO vvalues ('id') VALUES (3);";
        String insertVValue4 = "INSERT INTO vvalues ('id') VALUES (4);";
        String insertVValue5 = "INSERT INTO vvalues ('id') VALUES (5);";
        String insertVValue6 = "INSERT INTO vvalues ('id') VALUES (6);";
        String insertVValue7 = "INSERT INTO vvalues ('id') VALUES (7);";
        String insertVValue8 = "INSERT INTO vvalues ('id') VALUES (8);";
        String insertVValue9 = "INSERT INTO vvalues ('id') VALUES (9);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(insertVValue1);
            stmt.execute(insertVValue2);
            stmt.execute(insertVValue3);
            stmt.execute(insertVValue4);
            stmt.execute(insertVValue5);
            stmt.execute(insertVValue6);
            stmt.execute(insertVValue7);
            stmt.execute(insertVValue8);
            stmt.execute(insertVValue9);
        } catch (SQLException e) {
            System.out.println("ERROR inserting VValues : " + e.getMessage());
        }
    }

    // ============== //
    // ==== MAIN ==== //
    // ============== //
    public static void main(String args[]) {
        dropDatabase();
       
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
                
        // Commands
        insertCommands();
        insertRequires();
        insertCommandValues();
        insertDiscreteCommandValues();
        insertContinuousCommandValues();
        
        // Values and Env Variables
        insertEnvironmentVariables();
        insertBlocks();
        insertVValues();
        insertDiscreteVValues();
        insertContinuousVValues();
       
        // Behaviours
        insertExpressions();
        insertIsPartOf();
        insertBehaviours();
        insertAmbiences();
        insertComposes();
        insertActuatorCategories();
        insertActuators();
        
        // Actions
        insertComplexAction();
        insertAtomicActions();
        insertGathers();
        insertLaunches();
        
        // Sensors
        insertSensorCategories();
        insertSensors();
        
        
        System.out.println("Data inserted.");
        System.out.println("**** Process complete ! ****");
       
    }
}
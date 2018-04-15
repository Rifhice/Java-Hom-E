package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.DAOException;
import server.factories.AbstractDAOFactory;
import server.managers.SystemManager;
import server.models.Actuator;
import server.models.AtomicAction;
import server.models.Command;
import server.models.commandValue.*;
import server.models.categories.ActuatorCategory;

public class SQLiteActuatorDAO extends ActuatorDAO  {
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }    

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    
    public int createDiscreteCommandValue(DiscreteCommandValue value) throws SQLException{
    	int created = 0;
    	String sql = 
        		" INSERT INTO DiscreteCommandValues"
        		+ "(possible_values, fk_commandValue_id) VALUES"
        		+ "(?,?)";
    	
    	try {
    		PreparedStatement prepStatCommand = this.connect.prepareStatement(sql);
	        //TODO comment rentrer une array dans la base de donn�e
    		JSONObject json = new JSONObject();
            for (String possibleValue : value.getPossibleValues()) {
				json.append("possibleValues", possibleValue);
            }
            prepStatCommand.setString(1, json.toString());
	        prepStatCommand.setFloat(2, value.getId());
	        created = prepStatCommand.executeUpdate();
	        if (created > 0 ) {
	        	value.setId(SQLiteDAOTools.getLastId(connect));
	        }
    	}
    	catch(SQLException e) {
    		throw new SQLException("SQLException : DiscreteCommandValue (" + value.getId() + ") :" + e.getMessage(), e);
    	}
    	return created;
    }
    
    public int createContinuousCommandValue(ContinuousCommandValue value) throws SQLException{
    	int created = 0;
    	
    	String sql = 
        		" INSERT INTO ContinuousCommandValues"
        		+ "(min, max, precision, fk_commandValue_id) VALUES"
        		+ "(?,?,?,?)";
    	
    	try {
    		PreparedStatement prepStatCommand = this.connect.prepareStatement(sql);
	        prepStatCommand.setFloat(1, (float) value.getValueMin());
	        prepStatCommand.setFloat(2, (float) value.getValueMax());
	        prepStatCommand.setFloat(3, (float) value.getPrecision());
	        prepStatCommand.setFloat(4, (float) value.getId());
	        created = prepStatCommand.executeUpdate();
	        if (created > 0 ) {
	        	value.setId(SQLiteDAOTools.getLastId(connect));
	        }
    		
    	}
    	catch(SQLException e) {
    		throw new SQLException("SQLException : CommandValue (" + value.getId() + ") :" + e.getMessage(), e);
    	}
    	return created;
    }
    
    public int createCommandValue(Command obj, CommandValue value) throws SQLException{
    	int created = 0;
    	String sqlCommandValues = 
        		" INSERT INTO CommandValues"
        		+ "(name, fk_command_id) VALUES"
        		+ "(?,?)";
    	try {
    		PreparedStatement prepStatCommand = this.connect.prepareStatement(sqlCommandValues);
	        prepStatCommand.setString(1, value.getName());
	        prepStatCommand.setInt(2, obj.getId());
	        created = prepStatCommand.executeUpdate();
	        if (created > 0 ) {
	        	value.setId(SQLiteDAOTools.getLastId(connect));
	        	int res = 0;
	        	if (value instanceof ContinuousCommandValue) {
	        		ContinuousCommandValue cvalue =  (ContinuousCommandValue) value;
					res = createContinuousCommandValue(cvalue);	
				}
	        	else {
	        		DiscreteCommandValue dvalue = (DiscreteCommandValue) value;
	        		res = createDiscreteCommandValue(dvalue);
	        	}
	        	if(res == 0) {
	        		return 0;
	        	}
	        }
    		
    	}
    	catch(SQLException e) {
    		throw new SQLException("SQLException : CommandValue (" + obj.getId() + ") :" + e.getMessage(), e);
    	}
    		
    	return created;
    }
    
    //Create Command
    
    public int createCommand(Actuator obj, Command command) throws SQLException{
    	
    	String sqlCommands = 
        		" INSERT INTO Commands"
        		+ "(name, key, description, fk_actuator_id) VALUES"
        		+ "(?,?,?,?)";
    	
    	int created = 0;
	    	
	    		try {
			    	PreparedStatement prepStatCommand = this.connect.prepareStatement(sqlCommands);
			        prepStatCommand.setString(1, command.getName());
			        prepStatCommand.setString(2, command.getKey());
			        prepStatCommand.setString(3, command.getDescription());
			        prepStatCommand.setInt(4, obj.getId());
			        created = prepStatCommand.executeUpdate();
			        // command is created in the database
			        if (created > 0 ) {
			        	command.setId(SQLiteDAOTools.getLastId(connect));
			        	// command contains values
			        	if (command.getCommandValues() != null) {
				        	int valuesCreated = 0;
				        	// the program creates each value
				        	for (CommandValue commandValue : command.getCommandValues()) {
				        		valuesCreated = createCommandValue( command, commandValue);
				        		if (valuesCreated == 0) {
				        			return 0;
				        		}
				        	}
				        }
			        }
			        
		        }
	    		catch (SQLException e) {
	                throw new SQLException("SQLException : Command (" + command.getId() + ") :" + e.getMessage(), e);
	            }
    	return created; 
    }

	//Create an actuator which has been loaded in the database
    
    @Override
    public Actuator create(Actuator obj) throws DAOException {
        Actuator actuator = obj;
        
        String sql = "INSERT INTO Actuators "
                + "(name, description, fk_actuatorCategory_id, isActivated) VALUES "
                + "(?, ?, ?, ?)";
        
        
        // Insert the object
        int created = 0;
       
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setString(2, obj.getDescription());
            if(obj.getActuatorCategory() != null) {
                prepStat.setInt(3, obj.getActuatorCategory().getId());
            }
            if(obj.isActivated()) {
            	prepStat.setInt(4, 1);
            }
            else {
            	prepStat.setInt(4, 0);
            }
            created = prepStat.executeUpdate();
            // Get the id generated for this object
            if(created > 0) {
            	actuator.setId(SQLiteDAOTools.getLastId(connect));
            	if(obj.getCommands() != null) {
	                int commandCreated = 0;
	                for (Command command : obj.getCommands()) {
	                	commandCreated = createCommand(actuator, command);
	                }
	                if (commandCreated == 0) {
	                	return null;
	                }
            	}
            }
            else {
                actuator = null;
            }
             

        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        return actuator;
    }

    public Actuator getById(int id) throws DAOException {
        Actuator actuator = null;
        String sql = "SELECT * "
                + "FROM Actuators AS A "
                + "WHERE A.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            boolean res = rs.next();
            System.out.println(res);
            if (res) {
                actuator = new Actuator();
                actuator.setId(rs.getInt("id"));
                actuator.setName(rs.getString("name"));
                actuator.setDescription(rs.getString("description"));
                if(rs.getObject("fk_actuatorCategory_id") != null) {
                    actuator.setActuatorCategory(getCategory(rs.getInt("fk_actuatorCategory_id")));
                }
                actuator.setCommands(AbstractDAOFactory.getFactory(SystemManager.db).getCommandDAO().getByActuatorId(rs.getInt("id")));
                if(rs.getInt("isactivated") == 1) {
                	actuator.enable();
                }
                else {
                	actuator.disable();
                }
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getById(" + id + ") :" + e.getMessage(), e);
        }
            
        // Get Atomic Actions
        String sqlAA = "SELECT A.id AS id, A.name AS name, A.description AS description, "
                + "AA.id AS AAid, AA.executable AS AAexecutable "
                + "FROM Actuators AS A "
                + "JOIN AtomicActions AS AA ON AA.fk_actuator_id = A.id "
                + "WHERE A.id = ?";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlAA);
            prepStat.setInt(1, id);
            ResultSet rsAA = prepStat.executeQuery();

            ArrayList<AtomicAction> atomicActions = new ArrayList<AtomicAction>();
            if(rsAA.next()) {
                do {
                    int atomicActionId = rsAA.getInt("AAid");
                    String atomicActionExecutable = rsAA.getString("AAexecutable");
                    AtomicAction aa = new AtomicAction(atomicActionId, atomicActionExecutable);
                    atomicActions.add(aa); 
                } while(rsAA.next());
                actuator.setAtomicActions(atomicActions);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return actuator;
    }
    
    public ActuatorCategory getCategory(int idCategory) {
    	ActuatorCategory category = null;
        String sql = "SELECT * "
                + "FROM ActuatorCategories AS A "
                + "WHERE A.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, idCategory);
            ResultSet rs = prepStat.executeQuery();
            boolean res = rs.next();
            System.out.println(res);
            if (res) {
            	category = new ActuatorCategory();
            	category.setId(rs.getInt("id"));
            	category.setName(rs.getString("name"));
            	category.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getById(" + idCategory + ") :" + e.getMessage(), e);
        }
        return category;
    }

    
    @Override
    public int update(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    /**
     * Delete an Actuator, his Atomic Actions and his Commands
     */
    public int delete(int id) throws DAOException {
        String sqlActuator = "DELETE FROM Users "
                + "WHERE id = ?";

        String sqlAtomic = "DELETE FROM AtomicActions "
                + "WHERE fk_actuator_id = ?";
        
        String sqlCommands = "DELETE FROM Commands "
                + "WHERE fk_actuator_id = ?";

        int deletedActuator = 0;
        int deletedAtomic = 0;
        int deletedCommands = 0;
        try {
            PreparedStatement prepStatActuator = this.connect.prepareStatement(sqlActuator);
            PreparedStatement prepStatAtomic = this.connect.prepareStatement(sqlAtomic);
            PreparedStatement prepStatCommands = this.connect.prepareStatement(sqlCommands);

            prepStatActuator.setInt(1, id);
            prepStatAtomic.setInt(1, id);
            prepStatCommands.setInt(1, id);

            deletedActuator = prepStatActuator.executeUpdate();
            deletedAtomic = prepStatAtomic.executeUpdate();
            deletedCommands = prepStatCommands.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO delete(" + id + ") :" + e.getMessage(), e);
        }
        return deletedActuator + deletedAtomic + deletedCommands;
    }

    public int changeIsActivated(int id,boolean bool) throws DAOException {
    	String sql = "UPDATE actuators "
                + "SET isactivated = ? "
                + "WHERE id = ?";
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              if(bool) {
            	  prepStat.setInt(1, 1);
              }
              else {
            	  prepStat.setInt(1, 0);
              }
              prepStat.setInt(2, id);
              updated= prepStat.executeUpdate();

          } catch (SQLException e) {
        	  throw new DAOException("DAOException : Actuator activate(" + id + ") :" + e.getMessage(), e); 
          }
        return updated;
	}
    
    @Override
    public ArrayList<Actuator> getAll() throws DAOException {  
        
        // Get all the id
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String sqlAllId = "SELECT id FROM Actuators";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlAllId);
            ResultSet rs = prepStat.executeQuery();
            while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch(SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getAll() :" + e.getMessage(), e);
        }
        // Get all Actuators using getBydId()
        ArrayList<Actuator> actuators = new ArrayList<Actuator>();
        for (int id : ids) {
        	try {
        		System.out.println(id);
        		actuators.add(this.getById(id));
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        	}
        }
        return actuators;
    }

	@Override
	public int update(int idActuator, String name, String description, int idCategory) {
    	String sql = "UPDATE actuators "
                + "SET name = ?, description = ?, fk_actuatorcategory_id = ? "
                + "WHERE id = ?";
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, name);
              prepStat.setString(2, description);
              prepStat.setInt(3, idCategory);
              prepStat.setInt(4, idActuator);
              updated= prepStat.executeUpdate();

          } catch (SQLException e) {
        	  throw new DAOException("DAOException : Actuator update(" + idActuator + ") :" + e.getMessage(), e); 
          }
        return updated;
	}
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //

    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        ActuatorDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getActuatorDAO();
        ArrayList<Actuator> list = test.getAll();
        for (Actuator actuator : list) {
            System.out.println(actuator+"\n===============");
        }
    }

}

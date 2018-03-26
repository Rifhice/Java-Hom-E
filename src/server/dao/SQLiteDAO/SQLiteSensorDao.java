package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.SensorDAO;
import server.factories.AbstractDAOFactory;
import server.models.Sensor;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;

public class SQLiteSensorDao extends SensorDAO{
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteSensorDao(Connection connectionDriver) {
        super(connectionDriver);
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public Sensor create(Sensor obj) throws DAOException {
        Sensor sensor = obj;
        
        String sql = "INSERT INTO Sensors "
                + "(name, description, fk_sensorCategory_id) VALUES "
                + "(?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              if(obj.getSensorCategory() != null) {
            	  prepStat.setInt(3, obj.getSensorCategory().getId());
              }
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  sensor.setId(SQLiteDAOTools.getLastId(connect));
                  for (int i = 0; i < sensor.getEnvironmentVariable().size(); i++) {
                	boolean tmp = createEnvironmentVariable(sensor.getEnvironmentVariable().get(i));
                	if(!tmp) {
						return null;
					}
				}
              }
              else {
                  sensor = null;
              }
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return sensor;
    }

    private boolean createEnvironmentVariable(EnvironmentVariable variable) {
    	String sql = "INSERT INTO environmentVariables "
                + "(name, description,unit, fk_sensor_id) VALUES "
                + "(?, ?, ?,?)";
        
        // Insert the object
        int created = 0;
          try {
        	  
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, variable.getName());
              prepStat.setString(2, variable.getDescription());
              prepStat.setString(3, variable.getUnit());
              prepStat.setInt(4, variable.getSensor().getId());
              created = prepStat.executeUpdate();
              // Get the id generated for this object
              if(created > 0) {
                  variable.setId(SQLiteDAOTools.getLastId(connect));
                  if(variable instanceof ContinuousValue) {
                	  if(!createContinuousVariable((ContinuousValue)variable)) {
                		  return false;
                	  }
                  }
                  else if(variable instanceof DiscreteValue) {
                	  if(!createDiscreteVariable((DiscreteValue)variable)) {
                		  return false;
                	  }
                  }
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + variable.getId() + ") :" + e.getMessage(), e); 
          }
          return true;
    }
    
    private boolean createContinuousVariable(ContinuousValue variable) {
    	String sql = "INSERT INTO continuousEnvironmentVariables "
                + "(fk_environmentVariable_id, value_min, value_max,current_value,precision) VALUES "
                + "(?, ?, ?,?,?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setInt(1, variable.getId());
              prepStat.setFloat(2,(float) variable.getValueMin());
              prepStat.setFloat(3, (float)variable.getValueMax());
              prepStat.setFloat(4,(float) variable.getCurrentValue());
              prepStat.setFloat(5, (float)variable.getPrecision());          
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  return true;
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + variable.getId() + ") :" + e.getMessage(), e); 
          }
    }
    
    private boolean createDiscreteVariable(DiscreteValue variable) {
    	String sql = "INSERT INTO discreteEnvironmentVariables "
                + "(fk_environmentVariable_id, current_value, possible_values) VALUES "
                + "(?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setInt(1, variable.getId());
              prepStat.setString(2,variable.getCurrentValue());
              JSONObject json = new JSONObject();
              for (int i = 0; i < variable.getPossibleValues().size(); i++) {
				json.append("possibleValues", variable.getPossibleValues().get(i));
              }
              prepStat.setString(3, json.toString());       
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  return true;
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + variable.getId() + ") :" + e.getMessage(), e); 
          }
    }
    
    // TODO : return ArrayList<Command>
    @Override
    public Sensor getById(int id) throws DAOException {
        Sensor sensor = null;
        String sql = "SELECT S.id AS id, S.name AS name, S.description AS description "
                + "FROM Sensors AS S "
                + "WHERE S.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
            	sensor = new Sensor();
            	sensor.setId(rs.getInt("id"));
            	sensor.setName(rs.getString("name"));
            	sensor.setDescription(rs.getString("description"));
                // TODO list of commands
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return sensor;
    }

    @Override
    public int update(Sensor obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    // TODO JOINS for list of Env Variables + category
    public ArrayList<Sensor> getAll() throws DAOException {
    	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        String sql = "SELECT * FROM Sensors";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            Sensor sensor = null;
            while (rs.next()) {
            	sensor = new Sensor();
            	sensor.setId(rs.getInt("id"));
            	sensor.setName(rs.getString("name"));
            	sensor.setDescription(rs.getString("description"));
                sensors.add(sensor);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getAll() :" + e.getMessage(), e);
        }
        return sensors;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        SensorDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO();
        System.out.println(test.create(new Sensor("test","qsd")));
        System.out.println(test.getAll());
    }
}

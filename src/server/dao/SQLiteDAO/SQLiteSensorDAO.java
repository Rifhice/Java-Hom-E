package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.SensorDAO;
import server.factories.AbstractDAOFactory;
import server.managers.SystemManager;
import server.models.Sensor;
import server.models.categories.SensorCategory;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;
import server.models.environmentVariable.Value;

public class SQLiteSensorDAO extends SensorDAO{
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteSensorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public Sensor create(Sensor obj) throws DAOException {
        Sensor sensor = obj;
        
        String sql = "INSERT INTO Sensors "
                + "(name, description, fk_sensorCategory_id, isActivated) VALUES "
                + "(?, ?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              if(obj.getSensorCategory() != null) {
            	  prepStat.setInt(3, obj.getSensorCategory().getId());
              }
              if(obj.isActivated()) {
                  prepStat.setInt(4, 1);
              }
              else {
            	  prepStat.setInt(4, 0);
              }
              created = prepStat.executeUpdate();
              
              if(created > 0) {
                  sensor.setId(SQLiteDAOTools.getLastId(connect));
                	boolean tmp = createEnvironmentVariable(sensor.getEnvironmentVariables());
                	if(!tmp) {
						return null;
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
	  	  if(!createValue(variable.getValue())) {
			  return false;
	      }
    	String sql = "INSERT INTO environmentVariables "
                + "(name, description,unit, fk_sensor_id,fk_vvalue_id) VALUES "
                + "(?, ?, ?,?,?)";
        
        // Insert the object
        int created = 0;
          try {
        	  
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, variable.getName());
              prepStat.setString(2, variable.getDescription());
              prepStat.setString(3, variable.getUnit());
              prepStat.setInt(4, variable.getSensor().getId());
              prepStat.setInt(5, SQLiteDAOTools.getLastId(connect));
              created = prepStat.executeUpdate();
              // Get the id generated for this object
              if(created > 0) {
                  variable.setId(SQLiteDAOTools.getLastId(connect));
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + variable.getId() + ") :" + e.getMessage(), e); 
          }
          return true;
    }
    
    /**
     * Create a value object in the database
     * @param value
     * @return boolean, true if created else false
     */
    private boolean createValue(Value value) {
        String sql = "";
        if(value.getId() != 0) {
            sql = "INSERT INTO VValues "
                    + "(id) VALUES "
                    + "(?)";
        }
        else {
            sql = "INSERT INTO VValues DEFAULT VALUES;";
        }
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              if(value.getId() != 0) {
                  prepStat.setInt(1, value.getId());   
              }
              created = prepStat.executeUpdate();
              
              if(created > 0) {
                  value.setId(SQLiteDAOTools.getLastId(connect));
                  if(value instanceof ContinuousValue) {
                      if(!createContinuousValue((ContinuousValue)value)) {
                          return false;
                      }
                  }
                  else if(value instanceof DiscreteValue) {
                      if(!createDiscreteValue((DiscreteValue)value)) {
                          return false;
                      }
                  }
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO createValue(" + value.getId() + ") :" + e.getMessage(), e); 
          }
          return true;
    }
    
    
    private boolean createContinuousValue(ContinuousValue value) {
    	String sql = "INSERT INTO continuousVValues "
                + "(fk_vvalue_id, value_min, value_max,current_value,precision) VALUES "
                + "(?, ?, ?,?,?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setInt(1, value.getId());
              prepStat.setFloat(2,(float) value.getValueMin());
              prepStat.setFloat(3, (float)value.getValueMax());
              prepStat.setFloat(4,(float) value.getCurrentValue());
              prepStat.setFloat(5, (float)value.getPrecision());  
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  return true;
              }
              else {
                  return false;
              }
              
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO createContinuousValue(" + value.getId() + ") :" + e.getMessage(), e); 
          }
    }
    
    private boolean createDiscreteValue(DiscreteValue value) {
    	String sql = "INSERT INTO discreteVValues "
                + "(fk_vvalue_id, current_value, possible_values) VALUES "
                + "(?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setInt(1, value.getId());
              prepStat.setString(2,value.getCurrentValue());
              JSONObject json = new JSONObject();
              for (int i = 0; i < value.getPossibleValues().size(); i++) {
				json.append("possibleValues", value.getPossibleValues().get(i));
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
              throw new DAOException("DAOException : SensorDAO createDiscreteValue(" + value.getId() + ") :" + e.getMessage(), e); 
          }
    }
    
    // TODO : return ArrayList<Command>
    @Override
    public Sensor getById(int id) throws DAOException {
        Sensor sensor = null;
        String sql = "SELECT S.id AS id, S.name AS name, S.description AS description, S.isActivated AS isActivated "
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
            	if(rs.getInt("isActivated") == 1) {
                	sensor.enable();
            	}
            	else {
            		sensor.disable();
            	}
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
            	if(rs.getInt("isActivated") == 1) {
                	sensor.enable();
            	}
            	else {
            		sensor.disable();
            	}
            	sensor.setEnvironmentVariable(getEnvironmentVariable(sensor.getId()));
            	if(rs.getInt("fk_sensorCategory_id") != 0) {
            		try {
            			sensor.setSensorCategory(AbstractDAOFactory.getFactory(SystemManager.db).getSensorCategoriesDAO().getById(rs.getInt("fk_sensorCategory_id")));
            		}
            		catch (Exception e) {
						e.printStackTrace();
					}
            	}
                sensors.add(sensor);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getAll() :" + e.getMessage(), e);
        }
        return sensors;
    }
    
    public EnvironmentVariable getEnvironmentVariable(int id) throws DAOException {
    	EnvironmentVariable variable = null;
        String sql = "SELECT * FROM environmentVariables WHERE fk_sensor_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
            	variable = new EnvironmentVariable();
            	variable.setId(rs.getInt("id"));
            	variable.setName(rs.getString("name"));
            	variable.setDescription(rs.getString("description"));
            	variable.setUnit(rs.getString("unit"));
            	DiscreteValue dvalue = getDiscreteValue(rs.getInt("fk_vvalue_id"));
            	if(dvalue == null) {
            		ContinuousValue cvalue = getContinuousValue(rs.getInt("fk_vvalue_id"));
            		if(cvalue == null) {
            			System.out.println("AUCUNE VALUE TROUVE");
            		}
            		else {
            			variable.setValue(cvalue);
            		}
            	}
            	else {
            		variable.setValue(dvalue);
            	}
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getEnvironmentVariable() :" + e.getMessage(), e);
        }
        return variable;
    }
    
    public DiscreteValue getDiscreteValue(int id) {
    	DiscreteValue value = null;
        String sql = "SELECT * FROM discreteVValues WHERE fk_vvalue_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
            	value = new DiscreteValue();
            	value.setId(rs.getInt("current_value"));
            	ArrayList<String> possibleValues = new ArrayList<String>();
            	JSONObject json = new JSONObject(rs.getString("possible_values"));
            	JSONArray array = json.getJSONArray("possibleValues");
            	for (int i = 0; i < array.length(); i++) {
					possibleValues.add(array.getString(i));
				}
            	value.setCurrentValue(rs.getString("current_value"));
            	value.setPossibleValues(possibleValues);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getDiscreteValue() :" + e.getMessage(), e);
        }
        return value;
    }
    
    public ContinuousValue getContinuousValue(int id) {
    	ContinuousValue value = null;
        String sql = "SELECT * FROM continuousVValues WHERE fk_vvalue_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
            	value = new ContinuousValue();
            	value.setValueMin(rs.getDouble("value_min"));
            	value.setValueMax(rs.getDouble("value_max"));
            	value.setCurrentValue(rs.getDouble("current_value"));
            	value.setPrecision(rs.getDouble("precision"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getContinuousValue() :" + e.getMessage(), e);
        }
        return value;
    }
    
	public int changeIsActivated(int id,boolean bool) throws DAOException {
    	String sql = "UPDATE sensors "
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
        	  throw new DAOException("DAOException : Sensor activate(" + id + ") :" + e.getMessage(), e); 
          }
        return updated;
	}
	
	@Override
	public int update(int idSensor, String name, String description, int idCategory) {
    	String sql = "UPDATE sensors "
                + "SET name = ?, description = ?, fk_sensorcategory_id = ? "
                + "WHERE id = ?";
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, name);
              prepStat.setString(2, description);
              prepStat.setInt(3, idCategory);
              prepStat.setInt(4, idSensor);
              updated= prepStat.executeUpdate();

          } catch (SQLException e) {
        	  throw new DAOException("DAOException : Sensor update(" + idSensor + ") :" + e.getMessage(), e); 
          }
        return updated;
	}
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {/*
        SensorDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO();
       
        Value value = new ContinuousValue(45, -50.0, 50.0, 0.5, 20);
        EnvironmentVariable ev = new EnvironmentVariable(44,"Temperature","C'est la température.","C",new Sensor(),value);
        ArrayList<EnvironmentVariable> list = new ArrayList<EnvironmentVariable>();
        list.add(ev);        
        
        Sensor sensor = new Sensor("thermomètre", "il mesure bien la chaleur", list);    
        System.out.println(test.create(sensor));
        System.out.println(test.getAll());*/
    }

}

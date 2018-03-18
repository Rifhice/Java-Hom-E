package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.SensorCategoriesDAO;
import server.models.Actuator;
import server.models.Sensor;
import server.models.categories.ActuatorCategory;
import server.models.categories.SensorCategory;

public class SQLiteSensorCategoriesDAO extends SensorCategoriesDAO{

	public SQLiteSensorCategoriesDAO(Connection connect) {
		super(connect);
	}


	@Override
	public SensorCategory create(SensorCategory obj) throws DAOException {
		SensorCategory sensorCategory = obj;
        
        String sql = "INSERT INTO sensorCategories "
                + "(name, description) VALUES "
                + "(?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  String sqlGetLastId = "SELECT last_insert_rowid()";
                  PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                  int id = prepStatLastId.executeQuery().getInt(1);
                  sensorCategory.setId(id);
              }
              else {
            	  sensorCategory = null;
              }
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorCategoriesDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return sensorCategory;
	}

	@Override
	public SensorCategory getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(SensorCategory obj) throws DAOException {
    	String sql = "UPDATE sensorCategories "
                + "SET name = ?, description = ?"
                + "WHERE id = ?";
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              prepStat.setInt(3, obj.getId());
              updated= prepStat.executeUpdate();

          } catch (SQLException e) {
        	  throw new DAOException("DAOException : SensorCategories update(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return updated > 0;
	}

	@Override
	public int delete(int id) throws DAOException {
        String sql = "DELETE FROM sensorCategories WHERE id = ?";
        int deleted = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            deleted = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorCategories delete(" + id + ") :" + e.getMessage(), e);
        }
        return deleted;
	}
	
    @Override
    public ArrayList<SensorCategory> getAll() throws DAOException {
        ArrayList<SensorCategory> sensors = new ArrayList<SensorCategory>();
        String sql = "SELECT * FROM sensorCategories";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            SensorCategory sensor = null;
            while (rs.next()) {
            	sensor = new SensorCategory();
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
}

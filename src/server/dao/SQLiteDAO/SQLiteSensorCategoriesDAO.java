package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.SensorCategoriesDAO;
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
                  sensorCategory.setId(SQLiteDAOTools.getLastId(connect));
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
    	String sql = "SELECT * "
                + "FROM sensorCategories "
                + "WHERE id = ?";
    	SensorCategory category = null;
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setInt(1, id);
              ResultSet rs = prepStat.executeQuery();
              category = new SensorCategory(rs.getInt("id"),rs.getString("name"), rs.getString("description"));
          } catch (SQLException e) {
        	  throw new DAOException("DAOException : SensorCategories getById(" + id + ") :" + e.getMessage(), e); 
          }
        return category;
	}

	@Override
	public int update(SensorCategory obj) throws DAOException {
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
        return updated;
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

package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.ActuatorCategoriesDAO;
import server.dao.abstractDAO.DAOException;
import server.models.categories.ActuatorCategory;

public class SQLiteActuatorCategoriesDAO extends ActuatorCategoriesDAO{

	public SQLiteActuatorCategoriesDAO(Connection connect) {
		super(connect);
	}

	@Override
	public ActuatorCategory create(ActuatorCategory obj) throws DAOException {
		ActuatorCategory actuatorCategory = obj;
        
        String sql = "INSERT INTO actuatorCategories "
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
                  actuatorCategory.setId(SQLiteDAOTools.getLastId(connect));
              }
              else {
            	  actuatorCategory = null;
              }
          } catch (SQLException e) {
              throw new DAOException("DAOException : ActuatorCategoriesDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return actuatorCategory;
	}

	@Override
	public ActuatorCategory getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ActuatorCategory obj) throws DAOException {
    	String sql = "UPDATE actuatorCategories "
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
        	  throw new DAOException("DAOException : ActuatorCategories update(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return updated;
	}

	@Override
	public int delete(int id) throws DAOException {
        String sql = "DELETE FROM actuatorCategories WHERE id = ?";
        int deleted = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            deleted = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorCategories delete(" + id + ") :" + e.getMessage(), e);
        }
        return deleted;
	}
	
    @Override
    public ArrayList<ActuatorCategory> getAll() throws DAOException {
        ArrayList<ActuatorCategory> actuators = new ArrayList<ActuatorCategory>();
        String sql = "SELECT * FROM actuatorCategories";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            ActuatorCategory actuator = null;
            while (rs.next()) {
            	actuator = new ActuatorCategory();
            	actuator.setId(rs.getInt("id"));
            	actuator.setName(rs.getString("name"));
            	actuator.setDescription(rs.getString("description"));
            	actuators.add(actuator);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getAll() :" + e.getMessage(), e);
        }
        return actuators;
    }
	
}

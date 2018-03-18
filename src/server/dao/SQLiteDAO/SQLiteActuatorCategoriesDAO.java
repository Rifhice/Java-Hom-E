package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.ActuatorCategoriesDAO;
import server.dao.abstractDAO.DAOException;
import server.models.Actuator;
import server.models.categories.ActuatorCategory;
import server.models.categories.SensorCategory;

public class SQLiteActuatorCategoriesDAO extends ActuatorCategoriesDAO{

	public SQLiteActuatorCategoriesDAO(Connection connect) {
		super(connect);
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

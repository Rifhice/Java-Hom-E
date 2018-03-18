package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.SensorCategoriesDAO;
import server.models.Actuator;
import server.models.categories.ActuatorCategory;
import server.models.categories.SensorCategory;

public class SQLiteSensorCategoriesDAO extends SensorCategoriesDAO{

	public SQLiteSensorCategoriesDAO(Connection connect) {
		super(connect);
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

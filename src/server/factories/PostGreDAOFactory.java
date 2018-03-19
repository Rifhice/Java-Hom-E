package server.factories;

import java.sql.Connection;

import server.dao.DriverConnection;
import server.dao.SQLiteDAO.SQLiteActuatorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteSensorCategoriesDAO;
import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.RoleDAO;
import server.dao.abstractDAO.SensorDAO;
import server.dao.abstractDAO.UserDAO;

public class PostGreDAOFactory extends AbstractDAOFactory {
    protected Connection connect = DriverConnection.getInstance(DriverConnection.POSTGRESQL_DRIVER); 

	@Override
	public UserDAO getUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ActuatorDAO getActuatorDAO() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public SensorDAO getSensorDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLiteSensorCategoriesDAO getSensorCategoriesDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLiteActuatorCategoriesDAO getActuatorCategoriesDAO() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public RoleDAO getRoleDAO() {
        // TODO Auto-generated method stub
        return null;
    }

}

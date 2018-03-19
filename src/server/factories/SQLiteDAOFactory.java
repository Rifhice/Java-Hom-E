package server.factories;

import java.sql.Connection;

import server.dao.*;
import server.dao.SQLiteDAO.SQLiteSensorDao;
import server.dao.SQLiteDAO.SQLiteActuatorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteActuatorDAO;
import server.dao.SQLiteDAO.SQLiteRoleDAO;
import server.dao.SQLiteDAO.SQLiteSensorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteUserDAO;
import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.RoleDAO;
import server.dao.abstractDAO.SensorDAO;
import server.dao.abstractDAO.UserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
    protected Connection connect = DriverConnection.getInstance(DriverConnection.SQLITE_DRIVER); 

    @Override
	public UserDAO getUserDAO() {
		return new SQLiteUserDAO(connect);
	}

    @Override
    public ActuatorDAO getActuatorDAO() {
        return new SQLiteActuatorDAO(connect);
    }
	
    public SensorDAO getSensorDAO() {
    	return new SQLiteSensorDao(connect);
    }
    
    public SQLiteSensorCategoriesDAO getSensorCategoriesDAO() {
    	return new SQLiteSensorCategoriesDAO(connect);
    }
    
    public SQLiteActuatorCategoriesDAO getActuatorCategoriesDAO() {
    	return new SQLiteActuatorCategoriesDAO(connect);
    }

    @Override
    public RoleDAO getRoleDAO() {
        return new SQLiteRoleDAO(connect);
    }
    
}

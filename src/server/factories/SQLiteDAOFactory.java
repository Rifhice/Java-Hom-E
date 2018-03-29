package server.factories;

import java.sql.Connection;

import server.dao.*;
import server.dao.SQLiteDAO.SQLiteSensorDAO;
import server.dao.SQLiteDAO.SQLiteActuatorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteActuatorDAO;
import server.dao.SQLiteDAO.SQLiteAmbienceDAO;
import server.dao.SQLiteDAO.SQLiteBehaviourDAO;
import server.dao.SQLiteDAO.SQLiteCommandDAO;
import server.dao.SQLiteDAO.SQLiteComplexActionDAO;
import server.dao.SQLiteDAO.SQLiteEnvironmentVariableDAO;
import server.dao.SQLiteDAO.SQLiteRightDAO;
import server.dao.SQLiteDAO.SQLiteRoleDAO;
import server.dao.SQLiteDAO.SQLiteSensorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteUserDAO;
import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.AmbienceDAO;
import server.dao.abstractDAO.BehaviourDAO;
import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.ComplexActionDAO;
import server.dao.abstractDAO.EnvironmentVariableDAO;
import server.dao.abstractDAO.RightDAO;
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
    
    @Override
    public BehaviourDAO getBehaviourDAO() {
        return new SQLiteBehaviourDAO(connect);
    }
	
    public SensorDAO getSensorDAO() {
    	return new SQLiteSensorDAO(connect);
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

	@Override
	public AmbienceDAO getAmbienceDAO() {
		return new SQLiteAmbienceDAO(connect);
	}

	@Override
	public CommandDAO getCommandDAO() {
		return new SQLiteCommandDAO(connect);
	}

	@Override
	public ComplexActionDAO getComplexActionDAO() {
		return new SQLiteComplexActionDAO(connect);
	}

	@Override
	public RightDAO getRightDAO() {
		return new SQLiteRightDAO(connect);

	}

    @Override
    public EnvironmentVariableDAO getEnvironmentVariableDAO() {
        return new SQLiteEnvironmentVariableDAO(connect);
    }
    
}

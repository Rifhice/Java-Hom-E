package server.factories;

import java.sql.Connection;

import server.dao.DriverConnection;
import server.dao.SQLiteDAO.SQLiteActuatorCategoriesDAO;
import server.dao.SQLiteDAO.SQLiteSensorCategoriesDAO;
import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.AmbienceDAO;
import server.dao.abstractDAO.BehaviourDAO;
import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.ComplexActionDAO;
import server.dao.abstractDAO.RoleDAO;
import server.dao.abstractDAO.SensorDAO;
import server.dao.abstractDAO.UserDAO;
import server.models.ComplexAction;

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
    public BehaviourDAO getBehaviourDAO() {
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

	@Override
	public AmbienceDAO getAmbienceDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandDAO getCommandDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplexActionDAO getComplexActionDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}

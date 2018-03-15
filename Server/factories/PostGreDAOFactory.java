package factories;

import java.sql.Connection;

import dao.ActuatorDAO;
import dao.DriverConnection;
import dao.UserDAO;

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

}

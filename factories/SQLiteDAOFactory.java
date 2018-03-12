package factories;

import java.sql.Connection;

import dao.DriverConnection;
import dao.SQLiteUserDAO;
import dao.UserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
    protected Connection connect = DriverConnection.getInstance(DriverConnection.SQLITE_DRIVER); 

	public UserDAO getUserDAO() {
		return new SQLiteUserDAO(connect);
	}
	
}

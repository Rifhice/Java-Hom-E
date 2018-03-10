package factories;

import java.sql.Connection;

import dao.DriverConnection;
import dao.SQLiteUserDAO;
import dao.UserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = DriverConnection.getInstance(); 
	
	public UserDAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

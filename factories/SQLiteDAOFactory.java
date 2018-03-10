package factories;

import java.sql.Connection;

import dao.DAO;
import dao.DriverConnection;
import dao.SQLiteUserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = DriverConnection.getInstance(); 
	
	public DAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

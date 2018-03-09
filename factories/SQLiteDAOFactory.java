package factories;

import java.sql.Connection;
import dao.ConnectionDriver;
import dao.DAO;
import dao.SQLiteUserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = ConnectionDriver.getInstance(); 
	
	public DAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

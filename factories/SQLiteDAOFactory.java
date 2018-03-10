package factories;

import java.sql.Connection;
import dao.SQLiteDriverConnection;
import dao.DAO;
import dao.SQLiteUserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = SQLiteDriverConnection.getInstance(); 
	
	public DAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

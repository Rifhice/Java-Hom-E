package factories;

import java.sql.Connection;

//github.com/Rifhice/Hom-E.git
import dao.DAO;
import dao.DriverConnection;
import dao.SQLiteUserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = DriverConnection.getInstance(); 
	
	public DAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

package factories;

import java.sql.Connection;
<<<<<<< HEAD
import dao.SQLiteDriverConnection;
=======

>>>>>>> branch 'develop' of https://github.com/Rifhice/Hom-E.git
import dao.DAO;
import dao.DriverConnection;
import dao.SQLiteUserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
<<<<<<< HEAD
	protected static final Connection conn = SQLiteDriverConnection.getInstance(); 
=======
	protected static final Connection conn = DriverConnection.getInstance(); 
>>>>>>> branch 'develop' of https://github.com/Rifhice/Hom-E.git
	
	public DAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

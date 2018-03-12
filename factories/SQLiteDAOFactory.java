package factories;

import dao.SQLiteUserDAO;
import dao.UserDAO;

public class SQLiteDAOFactory extends AbstractDAOFactory{
	
	public UserDAO getUserDAO() {
		return new SQLiteUserDAO();
	}
	
}

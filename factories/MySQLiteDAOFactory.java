package factories;

import java.sql.Connection;
import dao.ConnectionDriver;

public class MySQLiteDAOFactory extends AbstractDAOFactory{
	
	protected static final Connection conn = ConnectionDriver.getInstance(); 
	
	
}

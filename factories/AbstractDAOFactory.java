package factories;

public abstract class AbstractDAOFactory {
	
	public static final int SQLITE_DAO_FACTORY = 0;
	public static final int POSTGRES_DAO_FACTORY = 1;

	public static AbstractDAOFactory getFactory(int type){
		
		return new MySQLiteDAOFactory();
	}
}

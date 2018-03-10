package factories;

public abstract class AbstractDAOFactory {
	
	public static final int SQLITE_DAO_FACTORY = 0;
	public static final int POSTGRES_DAO_FACTORY = 1;

	/**
	 * Return a DAO factory according to the type. 
	 * 0 = SQLLite DAO
	 * 1 = PostGRES DAO
	 * @param type, int
	 * @return AsbstractDAOFactory
	 */
	public static AbstractDAOFactory getFactory(int type){
	
		switch(type) { 
			case(SQLITE_DAO_FACTORY):
				return new SQLiteDAOFactory();
			case(POSTGRES_DAO_FACTORY):
				return new PostGreDAOFactory();
			default:
				return null;
		}
	}
}

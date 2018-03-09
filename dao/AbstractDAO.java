package dao;

public abstract class AbstractDAO {

	private String host;
	private int port;
	
	abstract public void connect();
}

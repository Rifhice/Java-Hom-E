package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.User;
import server.models.Right;

public abstract class RightDAO extends DAO<Right>{
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public RightDAO(Connection connectionDriver) {
        super(connectionDriver);
    }


	@Override
	public abstract ArrayList<Right> getAll() throws DAOException;


	public abstract ArrayList<Right> getByUser(User user) throws DAOException;	

}

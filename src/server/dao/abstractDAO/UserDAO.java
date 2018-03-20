package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Right;
import server.models.User;

public abstract class UserDAO extends DAO<User> {
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public UserDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    /**
     * Return the user according to the pseudo given. If none found, return null.
     * @param pseudo, String
     * @return User
     * @throws DAOException
     */
	public abstract User getByPseudo(String pseudo) throws DAOException;
	
	/**
	 * Return the list of rights owns by the user.
	 * @param id, int : the id of the user
	 * @return ArrayList<Right>
	 * @throws DAOException
	 */
	public abstract ArrayList<Right> getRights(int id) throws DAOException;
}

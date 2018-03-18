package server.dao.abstractDAO;

import java.sql.Connection;

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
     */
	public abstract User getByPseudo(String pseudo) throws DAOException;
}

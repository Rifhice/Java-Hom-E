package dao;

import models.User;

public abstract class UserDAO extends DAO<User> {
    
    /**
     * Return the user according to the pseudo given. If none found, return null.
     * @param pseudo, String
     * @return User
     */
	public abstract User getByPseudo(String pseudo);
}

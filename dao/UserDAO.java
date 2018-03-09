package dao;

import models.User;

public abstract class UserDAO extends DAO<User> {
    
	public abstract User getByPseudo(String pseudo);
}

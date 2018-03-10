package dao;

import java.sql.*;

import models.User;

public class SQLiteUserDAO extends UserDAO {

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
	}
	//Get by id
	@Override
	public User getById(String id) {
		User user = null;
		
		try {
			PreparedStatement prepStat = this.connect.prepareStatement(
				"SELECT * FROM Users WHERE id = ?"
			);
			prepStat.setString(1, id);
			ResultSet result = prepStat.executeQuery();
			
			if(result.first()) {
				user = new User(
					result.getString("pseudo")
				);         
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean update(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getByPseudo(String pseudo) throws DAOException {
		User user = null;
		try {
			PreparedStatement prepStat = this.connect.prepareStatement(
				"SELECT * FROM Users WHERE pseudo = ?"
			);
			prepStat.setString(0, pseudo);
			ResultSet result = prepStat.executeQuery();

			if(result.first()) {
				user = new User(
					result.getString("pseudo"),
					result.getString("password")
				);         
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		System.out.println(user.getPseudo());
		return user;
	}
}

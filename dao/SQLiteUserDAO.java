package dao;

import java.sql.*;

import models.User;

public class SQLiteUserDAO extends DAO<User> {

	@Override
	public boolean create(User obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getById(String id) {
		User user = new User();
		
		try {
			PreparedStatement prepStat = this.connect.prepareStatement(
				"SELECT * FROM Users WHERE id = ?"
			);
			prepStat.setString(0, id);
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
	
	public User getByPseudo(String pseudo) {
	User user = new User();
		try {
			PreparedStatement prepStat = this.connect.prepareStatement(
				"SELECT * FROM Users WHERE pseudo = ?"
			);
			prepStat.setString(0, pseudo);
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


}

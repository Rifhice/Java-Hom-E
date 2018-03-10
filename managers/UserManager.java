package managers;


import java.util.ArrayList;

import org.json.JSONObject;

import dao.DAO;
import dao.DAOException;
import dao.SQLiteUserDAO;
import dao.UserDAO;
import models.EnvironmentVariable;
import models.Sensor;
import models.User;

public class UserManager extends Manager{
	
	ArrayList<User> users;
	
	UserDAO userDAO = new SQLiteUserDAO();
	
	private static UserManager manager = null;
	
	private UserManager() {
		users = new ArrayList<User>();
	}
	
	public static UserManager getManager() {
		if(manager == null) 
			manager = new UserManager();
		return manager;
	}
	
	public User login(String pseudo, String password) {
		User user = null;
		try {
			user = userDAO.getByPseudo(pseudo);
		} catch(DAOException exception) {
			
		}
		if(user != null) {
			String hashPassword = password;
			String storedPassword = user.getPassword();
			if(hashPassword.equals(storedPassword)) {
				users.add(user);
			}
		}
		return user;
	}

	@Override
	public void handleMessage(JSONObject json) {
		// TODO Auto-generated method stub
		
	}
}

package managers;


import java.util.ArrayList;

import org.json.JSONObject;

import dao.DAO;
import dao.DAOException;
import models.EnvironmentVariable;
import models.Sensor;
import models.User;

public class UserManager {
	
	ArrayList<Sensor> sensors;
	
	DAO<User> userDAO = SystemManager.getManager().getDAOFactory().getUserDAO();
	
	private static UserManager manager = null;
	
	private UserManager() {
		sensors = new ArrayList<Sensor>();
	}
	
	public static UserManager getManager() {
		if(manager == null) 
			manager = new UserManager();
		return manager;
	}
	
	public User login(String pseudo, String password) {
		User user;
		try {
			user = userDAO.getUserByPseudo(pseudo);
		} catch(DAOException exception) {
			
		}
		if(user != null) {
			String hashPassword = password;
			String storedPassword = user.getPassword();
			if(hashPassword.equals(storedPassword)) {
				return user;
			}
		}
	}
}

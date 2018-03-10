package managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import Tools.Security;
import dao.DAOException;
import dao.UserDAO;
import factories.AbstractDAOFactory;
import models.User;
import ocsf.server.ConnectionToClient;

public class UserManager extends Manager{
	
	ArrayList<User> users;
	
	UserDAO userDAO = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO();
	
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
			System.out.println("Error login.");
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
	public void handleMessage(JSONObject json, ConnectionToClient client) {
		String action = json.getString("action");
		switch(action) {
			case "login":
				String pseudo = json.getString("pseudo");
				String password = json.getString("password");
				User user = login(pseudo, password);
				if(user != null) {
					JSONObject token = new JSONObject();
					token.put("id", user.getId());
					JSONObject result = new JSONObject();
					result.put("token", Security.encrypt(token.toString()));
					try {
						client.sendToClient(result.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					//User not in database
				}
				break;
			}
	}
}

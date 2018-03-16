package managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import dao.DAOException;
import dao.UserDAO;
import factories.AbstractDAOFactory;
import javafx.util.Pair;
import models.User;
import ocsf.serverConnection.ConnectionToClient;
import tools.Security;

public class UserManager extends Manager{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private ArrayList<User> users;
    private UserDAO userDAO = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO();
    private static UserManager manager = null;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    private UserManager() {
        users = new ArrayList<User>();
    }

    public static UserManager getManager() {
        if(manager == null) 
            manager = new UserManager();
        return manager;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= // 

    /**
     * Return a pair with the user and a code defining success or failure : 
     * 0 = success, 
     * -1 = wrong password, 
     * -2 = wrong pseudo
     * @param pseudo
     * @param password
     * @return Pair<User,Integer>
     */
    public Pair<User,Integer> login(String pseudo, String password) {
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
                return new Pair<User,Integer>(user,0);
            }
            else {
                return new Pair<User,Integer>(null,-1);
            }
        }
        return new Pair<User,Integer>(null,-2);
    }
    
    public User loginAsGuest(String pseudo) {
    	if(pseudo.equals("")) {
    		//Donner un pseudo
    	}
    	
    	// TODO : Use a RoleDAO() to get the id of the role "guest"
    	// User user = new User(pseudo,User.USERTYPE.GUEST);
    	User user = new User(pseudo);
    	users.add(user);
    	return user;
    }

    @Override
    public void handleMessage(JSONObject json, ConnectionToClient client) {
        String action = json.getString("action");
        System.out.println(action);
        String pseudo;
        String password;
        switch(action) {
	        case "login":
	            pseudo = json.getString("pseudo");
	            password = json.getString("password");
	            Pair<User,Integer> user = login(pseudo, password);
	            if(user.getKey() != null) {
	                JSONObject token = new JSONObject();
	                token.put("id", user.getKey().getId());
	                token.put("type", user.getKey().getRoleId());
	                
	                JSONObject result = new JSONObject();
	                result.put("result","success");
	                result.put("token", Security.encrypt(token.toString()));
	                try {
	                    client.sendToClient(result.toString());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            else {
	                JSONObject result = new JSONObject();
	                System.out.println(user.getValue());
	                if(user.getValue() == -1) {
	                    result.put("result","wrong_password");
	                }
	                else {
	                    result.put("result","wrong_pseudo");
	                }
	
	                try {
	                    client.sendToClient(result.toString());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            break;
	        case "loginAsGuest":
	        	User tmp = null;
	        	try {
		        	tmp = loginAsGuest(json.getString("pseudo"));
				} catch (Exception e) {
					tmp = loginAsGuest("");
				}
	        	System.out.println(tmp);
                JSONObject token = new JSONObject();
                token.put("type", tmp.getRoleId());
                token.put("pseudo", tmp.getPseudo());
                
                JSONObject result = new JSONObject();
                result.put("result","success");
                result.put("token", Security.encrypt(token.toString()));
                try {
                    client.sendToClient(result.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
	        	break;
        }
    }
}

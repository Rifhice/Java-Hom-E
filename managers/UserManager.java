package managers;


import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import Tools.Security;
import dao.DAOException;
import dao.UserDAO;
import factories.AbstractDAOFactory;
import javafx.util.Pair;
import models.User;
import ocsf.server.ConnectionToClient;

public class UserManager extends Manager{

    // ==== ATTRIBUTES ==== //
    ArrayList<User> users;
    UserDAO userDAO = AbstractDAOFactory.getFactory(SystemManager.db).getUserDAO();
    private static UserManager manager = null;

    // ==== CONSTRUCTORS ==== //
    private UserManager() {
        users = new ArrayList<User>();
    }

    public static UserManager getManager() {
        if(manager == null) 
            manager = new UserManager();
        return manager;
    }

    // ==== METHODS ==== //

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

    @Override
    public void handleMessage(JSONObject json, ConnectionToClient client) {
        String action = json.getString("action");
        switch(action) {
        case "login":
            String pseudo = json.getString("pseudo");
            String password = json.getString("password");
            Pair<User,Integer> user = login(pseudo, password);
            if(user.getKey() != null) {
                JSONObject token = new JSONObject();
                token.put("id", user.getKey().getId());
                token.put("type", user.getKey().getType());
                
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
        }
    }
}

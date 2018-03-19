package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Right;
import server.models.Role;
import server.models.User;

public class SQLiteUserDAO extends UserDAO {
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteUserDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public User create(User obj) throws DAOException {
        User user = obj;
        
        String sql = "INSERT INTO Users "
                + "(id, pseudo, password, fk_role_id) VALUES "
                + "(?, ?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              if(obj.getId() != 0) {
                  prepStat.setInt(1, obj.getId());
              }
              prepStat.setString(2, obj.getPseudo());
              prepStat.setString(3, obj.getPassword());
              if(obj.getRole() != null) {
                  prepStat.setInt(4, obj.getRole().getId());
              }
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  String sqlGetLastId = "SELECT last_insert_rowid()";
                  PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                  int id = prepStatLastId.executeQuery().getInt(1);
                  user.setId(id);
              }
              else {
                  user = null;
              }
          } catch (SQLException e) {
              throw new DAOException("DAOException : UserDAO create(" + obj.getPseudo() + ") :" + e.getMessage(), e); 
          }
        return user;
    }

    @Override
    public User getById(int id) throws DAOException {
        User user = null;
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "R.id AS Rid, R.name AS Rname, Ri.denomination AS Ridenomination, "
                + "Ri.description AS Ridescription, Ri.id AS Riid "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "JOIN Owns AS O ON O.fk_user_id = U.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + "WHERE U.id = ?;";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            
            ArrayList<Right> rights = new ArrayList<Right>();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
                user.setRole(new Role(rs.getInt("Rid"),rs.getString("Rname")));          
                
                // Construct right
                int rightId = rs.getInt("Riid");
                String rightDenomination = rs.getString("Ridenomination");
                String rightDescription = rs.getString("Ridescription");
                Right right = new Right(rightId, rightDenomination, rightDescription);
                rights.add(right);
            }
            user.setRights(rights);
            
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean update(User obj) throws DAOException {

    	String sql = "UPDATE Users "
                + "SET pseudo = ?, password = ?, fk_role_id = ? "
                + "WHERE id = ?";
    	int updated = 0;
    	  try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getPseudo());
              prepStat.setString(2, obj.getPassword());
              prepStat.setInt(3, obj.getRole().getId());
              prepStat.setInt(4, obj.getId());
              updated= prepStat.executeUpdate();

          } catch (SQLException e) {
        	  throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return updated > 0;
    }

    @Override
    public int delete(int id) throws DAOException {
         String sql = "DELETE FROM Users WHERE id = ?";
         int deleted = 0;
         try {
             PreparedStatement prepStat = this.connect.prepareStatement(sql);
             prepStat.setInt(1, id);
             deleted = prepStat.executeUpdate();
         } catch (SQLException e) {
             throw new DAOException("DAOException : UserDAO delete(" + id + ") :" + e.getMessage(), e);
         }
         return deleted;
    }
    
    @Override
    public ArrayList<User> getAll() throws DAOException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id;";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
                
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getAll() :" + e.getMessage(), e);
        }
        return users;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //

    @Override
    public User getByPseudo(String pseudo) throws DAOException {
        User user = null;
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "WHERE pseudo = ? ";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, pseudo);
            ResultSet rs = prepStat.executeQuery();

            // If no user found, we do nothing and return null.
            if (rs.next()) {
            	user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getByPseudo(" + pseudo + ") :" + e.getMessage(), e);
        }
        return user;
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        UserDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getUserDAO();
        User user = new User("pseudqsdsqoqsdsqds", "MDP", new Role(1,"test"));
        System.out.println(test.create(user));
    }

    
}

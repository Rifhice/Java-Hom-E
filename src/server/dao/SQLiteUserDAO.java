package server.dao;

import java.sql.*;
import java.util.ArrayList;

import server.factories.AbstractDAOFactory;
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
    public boolean create(User obj) throws DAOException {
        String sql = "INSERT INTO Users "
                + "(pseudo, password, fk_role_id) VALUES "
                + "(?, ?, ?)";
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getPseudo());
              prepStat.setString(2, obj.getPassword());
              prepStat.setInt(3, obj.getRoleId());
              created = prepStat.executeUpdate();
          } catch (SQLException e) {
              throw new DAOException("DAOException : UserDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return created > 0;
    }

    @Override
    public User getById(int id) throws DAOException {
        User user = null;
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "WHERE U.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("Rid"));
                user.setRoleName(rs.getString("Rname"));
            }
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
              prepStat.setInt(3, obj.getRoleId());
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
                user.setRoleId(rs.getInt("Rid"));
                user.setRoleName(rs.getString("Rname"));
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
                user.setRoleId(rs.getInt("Rid"));
                user.setRoleName(rs.getString("Rname"));
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
        System.out.println(test.getAll());
    }

    
}

package dao;

import java.sql.*;

import models.User;

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
    public boolean create(User obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User getById(String id) throws DAOException {
        User user = null;
        String sql = "SELECT * FROM Users WHERE id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id+ ") :" + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean update(User obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int delete(String id) {
         String sql = "DELETE FROM Users WHERE id = ?";
         int deleted = 0;
         try {
             PreparedStatement prepStat = this.connect.prepareStatement(sql);
             prepStat.setString(1, id);
             deleted = prepStat.executeUpdate();
         } catch (SQLException e) {
             throw new DAOException("DAOException : UserDAO delete(" + id+ ") :" + e.getMessage(), e);
         }
         return deleted;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //

    @Override
    public User getByPseudo(String pseudo) throws DAOException {
        User user = null;
        String sql = "SELECT * FROM Users WHERE pseudo = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, pseudo);
            ResultSet rs = prepStat.executeQuery();

            // If no user found, we do nothing and return null.
            if (rs.next()) {
            	user = new User();
                user.setId(rs.getString("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getByPseudo(" + pseudo + ") :" + e.getMessage(), e);
        }
        return user;
    }
}

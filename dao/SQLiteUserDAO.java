package dao;

import java.sql.*;

import models.User;

public class SQLiteUserDAO extends UserDAO {

    @Override
    public boolean create(User obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public User getById(String id) {
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
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
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
            e.printStackTrace();
        }
        return user;
    }

    // ============== //
    // ==== MAIN ==== //
    // ============== //
    public static void main(String args[]) {
        SQLiteUserDAO uDAO = new SQLiteUserDAO();
        System.out.println(uDAO.getByPseudo("owner").getPassword());
    }
}

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
    /**
     * Rights of the user are the ones in owns + ownsByDefault.
     * At the creation, his rights will be the ones in ownsByDefault.
     */
    public User create(User obj) throws DAOException {
        User user = obj;

        String sql = "INSERT INTO Users "
                + "(pseudo, password, fk_role_id) VALUES "
                + "(?, ?, ?);";

        // Insert the User
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getPseudo());
            prepStat.setString(2, obj.getPassword());
            prepStat.setInt(3, obj.getRole().getId());
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
        System.out.println("Nouveau membre créé.");
        return user;
    }

    @Override
    public User getById(int id) throws DAOException {
        User user = null;
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "WHERE U.id = ?;";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
                user.setRole(new Role(rs.getInt("Rid"),rs.getString("Rname"))); 
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }
       
        user.setRights(this.getRights(user.getId()));
        
        return user;
    }

    @Override
    /**
     * Update the users and his rights. 
     * If no rights provided, delete all his rights.
     */
    public int update(User obj) throws DAOException {
        // Update User
        String sql = "UPDATE Users "
                + "SET pseudo = ?, password = ?, fk_role_id = ? "
                + "WHERE id = ?";
        int userUpdated = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getPseudo());
            prepStat.setString(2, obj.getPassword());
            prepStat.setInt(3, obj.getRole().getId());
            prepStat.setInt(4, obj.getId());
            userUpdated = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        // Delete his rights
        String sqlDeleteRights = "DELETE FROM Owns "
                + "WHERE fk_user_id = ?";
        int rightsDeleted = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlDeleteRights);
            prepStat.setInt(1, obj.getId());
            rightsDeleted = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }        

        // Update his rights
        String sqlInsertRights = "INSERT INTO Owns "
                + "(fk_user_id, fk_right_id) VALUES "
                + "(?, ?);";

        int rightsInserted = 0;
        if(obj.getRights() != null) {
            for (Right right : obj.getRights()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, right.getId());
                    rightsInserted = prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
                }
            } 
        }

        return userUpdated + rightsDeleted + rightsInserted;
    }

    @Override
    /**
     * Delete in Users and Owns (rights)
     */
    public int delete(int id) throws DAOException {
        String sqlUser = "DELETE FROM Users "
                + "WHERE id = ?";

        String sqlOwns = "DELETE FROM Owns "
                + "WHERE fk_user_id = ?";

        int deletedUser = 0;
        int deletedOwns = 0;
        try {
            PreparedStatement prepStatUser = this.connect.prepareStatement(sqlUser);
            PreparedStatement prepStatOwns = this.connect.prepareStatement(sqlOwns);

            prepStatUser.setInt(1, id);
            prepStatOwns.setInt(1, id);

            deletedUser = prepStatUser.executeUpdate();
            deletedOwns = prepStatOwns.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO delete(" + id + ") :" + e.getMessage(), e);
        }
        return deletedUser + deletedOwns;
    }

    @Override
    public ArrayList<User> getAll() throws DAOException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, U.fk_role_id As Rid, "
        		+ "R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
                
                do {           
                		User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setPseudo(rs.getString("pseudo"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(new Role(rs.getInt("Rid"),rs.getString("Rname")));                    
                        users.add(user);
                } while (rs.next());
                // Push the last user

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
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "WHERE U.pseudo = ?;";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, pseudo);
            ResultSet rs = prepStat.executeQuery();

            // If no user found, we do nothing and return null.
            if(rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setPseudo(rs.getString("pseudo"));
                user.setPassword(rs.getString("password"));
                user.setRole(new Role(rs.getInt("Rid"),rs.getString("Rname"))); 
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getByPseudo(" + pseudo + ") :" + e.getMessage(), e);
        }
        
        if(user != null) {
            user.setRights(this.getRights(user.getId()));
        }
        
        return user;
    }
    
    public ArrayList<Right> getRights(int id) throws DAOException {
        // Get owns rights
        String sql = "SELECT Ri.denomination AS Ridenomination, "
                + "Ri.description AS Ridescription, Ri.id AS Riid "
                + "FROM Users AS U "
                + "JOIN Owns AS O ON O.fk_user_id = U.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + "WHERE U.id = ?;";
        
        ArrayList<Right> rightsOwns = new ArrayList<Right>();
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                do {
                    // Construct rights in owns
                    int rightId = rs.getInt("Riid");
                    String rightDenomination = rs.getString("Ridenomination");
                    String rightDescription = rs.getString("Ridescription");
                    Right right = new Right(rightId, rightDenomination, rightDescription);
                    rightsOwns.add(right);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getRights(" + id + ") (owns):" + e.getMessage(), e);
        }
        
        // Get rights from Owns By Default
        String sqlRightsOBD = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "Ri.description AS Ridescription, Ri.id AS Riid, Ri.denomination AS Ridenomination "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "JOIN OwnsByDefault AS OBD ON OBD.fk_role_id = R.id "
                + "JOIN Rights AS Ri ON Ri.id = OBD.fk_right_id "
                + "WHERE U.id = ?;";
        
        ArrayList<Right> rightsOBD = new ArrayList<Right>();
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlRightsOBD);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {
                do {
                    // Construct rights in owns
                    int rightId = rs.getInt("Riid");
                    String rightDenomination = rs.getString("Ridenomination");
                    String rightDescription = rs.getString("Ridescription");
                    Right right = new Right(rightId, rightDenomination, rightDescription);
                    rightsOBD.add(right);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getRights(" + id + ") (ownsByDefault) :" + e.getMessage(), e);
        }
        
        // Build the list of DISTINCT rights
        ArrayList<Right> allRights = rightsOBD;
        boolean isAlreadyHere;
        if(rightsOwns != null) {
            for (Right rightO : rightsOwns) {
                isAlreadyHere = false;
                for (Right right : allRights) {
                    if(rightO.getId() == right.getId()) {
                        isAlreadyHere = true;
                    }
                }
                if(!isAlreadyHere) {
                    allRights.add(rightO);
                }
            }
        }        
        return allRights;
    }


    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        UserDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getUserDAO();
        System.out.println(test.getByPseudo("The Boss"));
    }


}

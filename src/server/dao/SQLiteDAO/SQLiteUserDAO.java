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
    // TODO : Insert rights -> if no rights provided, rights = ownsByDefault from his role. 
    public User create(User obj) throws DAOException {
        User user = obj;

        String sql = "INSERT INTO Users "
                + "(pseudo, password, fk_role_id) VALUES "
                + "(?, ?, ?)";

        // Insert the object
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getPseudo());
            prepStat.setString(2, obj.getPassword());
            if(obj.getRole() != null) {
                prepStat.setInt(3, obj.getRole().getId());
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

            if(rs.next()) {
                ArrayList<Right> rights = new ArrayList<Right>();
                do {
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
                } while (rs.next());
                user.setRights(rights);
            }


        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return user;
    }

    @Override
    // TODO : Update rights
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
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "R.id AS Rid, R.name AS Rname, Ri.denomination AS Ridenomination, "
                + "Ri.description AS Ridescription, Ri.id AS Riid "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "JOIN Owns AS O ON O.fk_user_id = U.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                // Know if the user manipulated changed.
                int previousId = 0;
                ArrayList<Right> rights = new ArrayList<Right>();
                User user = new User();
                do {
                    /* New User : 
                     *  - Add the rights to the previous one
                     *  - Add the previous one to users
                     *  - Empty the rights variable
                     *  - Empty the user variable
                     */  
                    if(previousId != rs.getInt("id")) {
                        // Not first user : push the previous user
                        if(previousId != 0) {
                            user.setRights(rights);
                            users.add(user);   

                            rights = new ArrayList<Right>();
                            user = new User();  
                        }   

                        user.setId(rs.getInt("id"));
                        user.setPseudo(rs.getString("pseudo"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(new Role(rs.getInt("Rid"),rs.getString("Rname"))); 
                        
                        previousId = rs.getInt("id");                      
                    }
                    
                    // Same user as previous ony, we add the next right
                    int rightId = rs.getInt("Riid");
                    String rightDenomination = rs.getString("Ridenomination");
                    String rightDescription = rs.getString("Ridescription");
                    Right right = new Right(rightId, rightDenomination, rightDescription);
                    rights.add(right);

                } while (rs.next());
                // Push the last user
                user.setRights(rights);
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
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, "
                + "R.id AS Rid, R.name AS Rname,"
                + "Ri.id AS Riid, Ri.denomination AS Ridenomination, Ri.description AS Ridescription "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "JOIN Owns AS O ON O.fk_user_id = U.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + "WHERE U.pseudo = ?;";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, pseudo);
            ResultSet rs = prepStat.executeQuery();

            // If no user found, we do nothing and return null.
            if(rs.next()) {
                ArrayList<Right> rights = new ArrayList<Right>();
                do {
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
                } while (rs.next());
                user.setRights(rights);
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
        System.out.println(test.getAll().get(0));
        System.out.println(test.delete(1));

    }


}

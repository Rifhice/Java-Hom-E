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
     * If no rights provided, rights = ownsByDefault from his role. 
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

        // Insert his rights
        String sqlInsertRights = "INSERT INTO Owns "
                + "(fk_user_id, fk_user_id) VALUES "
                + "(?, ?);";

        // Rights provided, insert them
        if(obj.getRights() != null) {
            for (Right right : obj.getRights()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, right.getId());
                    prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : UserDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
                }
            } 
        }
        // No rights provided, use OwnsByDefault
        else {
            // Get rights of OwnsByDefault
            ArrayList<Right> rights = new ArrayList<Right>();
            int roleId = obj.getRole().getId();
            String ownsByDefaultSql = "SELECT O.fk_right_id AS ofk_right_id, "
                    + " R.id AS Rid, R.denomination AS Rdenomination, R.description AS Rdescription "
                    + " FROM ownsByDefault AS O "
                    + " JOIN Rights AS R ON O.fk_right_id = R.id "
                    + " WHERE fk_role_id = ?;";
            try {
                PreparedStatement prepStat = this.connect.prepareStatement(ownsByDefaultSql);
                prepStat.setInt(1, roleId);
                ResultSet rs = prepStat.executeQuery();
                
                if(rs.next()) {
                    do {
                        // Construct right
                        rights.add(new Right( rs.getInt("Rid"), rs.getString("Rdenomination"), rs.getString("Rdescription") ));
                    } while (rs.next());
                }                
            } catch (SQLException e) {
                throw new DAOException("DAOException : UserDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
            }
            
            // Insert rights into owns
            String sqlInsertRights2 = "INSERT INTO Owns "
                    + "(fk_user_id, fk_user_id) VALUES "
                    + "(?, ?);";

            if(rights != null) {
                for (Right right : rights) {
                    try {
                        PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights2);
                        prepStat.setInt(1, user.getId());
                        prepStat.setInt(2, right.getId());
                        prepStat.executeUpdate();
                    } catch (SQLException e) {
                        throw new DAOException("DAOException : UserDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
                    }
                } 
                user.setRights(rights);
            }
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
            prepStat.setInt(1, obj.getId());
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
                + "(fk_user_id, fk_user_id) VALUES "
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
        User u = test.getById(1);
        System.out.println(u);
    }


}

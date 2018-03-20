package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.RoleDAO;
import server.factories.AbstractDAOFactory;
import server.models.Right;
import server.models.Role;
import server.models.User;

public class SQLiteRoleDAO extends RoleDAO {
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteRoleDAO(Connection connectionDriver) {
        super(connectionDriver);
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public Role create(Role obj) throws DAOException {
        Role role = obj;
        String sql = "INSERT INTO roles "
                + "(name) VALUES "
                + "(?)";

        // Insert the object
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            created = prepStat.executeUpdate();

            // Get the id generated for this object
            if(created > 0) {
                role.setId(SQLiteDAOTools.getLastId(connect));
            }
            else {
                role = null;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : RoleDAO create(" + obj.getName() + ") :" + e.getMessage(), e); 
        }
        return role;
    }

    @Override
    public Role getById(int id) throws DAOException {
    	
    	Role role = null;   	
    	String sql = "SELECT  R.id AS id, R.name AS name, "
    			+ "Ri.description AS Ridescription, Ri.id AS Riid, "
    			+ "Ri.denomination AS Ridenomination "  
                + "FROM Roles AS R "
                + "JOIN OwnsByDefault AS O ON O.fk_role_id = R.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + "WHERE R.id = ?;";

    	try {
    		PreparedStatement prepStat = this.connect.prepareStatement(sql);
    		prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {
                ArrayList<Right> rights = new ArrayList<Right>();
                role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name")); 
                do {
                    // Construct right
                    int rightId = rs.getInt("Riid");
                    String rightDenomination = rs.getString("Ridenomination");
                    String rightDescription = rs.getString("Ridescription");
                    Right right = new Right(rightId, rightDenomination, rightDescription);
                    rights.add(right);
                } while (rs.next());
                role.setRights(rights);
            }
    		
    	}
    	catch(SQLException e) {
    		throw new DAOException("DAOException : RoleDAO getbyId(" + id + ") : " + e.getMessage(),e);
    		
    	}
    	
        return role;
    }

    @Override
    public int update(Role obj) throws DAOException {
    	// Update Role
        String sql = "UPDATE roles "
                + "SET name = ? "
                + "WHERE id = ?";
        int roleUpdated = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setInt(2, obj.getId());
            roleUpdated = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : RoleDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        // Delete role's rights
        String sqlDeleteRights = "DELETE FROM OwnsByDefault "
                + "WHERE fk_role_id = ?";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlDeleteRights);
            prepStat.setInt(1, obj.getId());
            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : RoleDAO update.rightsdeletion(" + obj.getId() + ") :" + e.getMessage(), e); 
        }        

        // Update role's rights
        String sqlInsertRights = "INSERT INTO OwnsByDefault "
                + "(fk_role_id, fk_right_id) VALUES "
                + "(?, ?);";

        if(obj.getRights() != null) {
            for (Right right : obj.getRights()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, right.getId());
                    prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : roleDAO update.rightsAddition(" + obj.getId() + ") :" + e.getMessage(), e); 
                }
            } 
        }
        return roleUpdated;

    }

    @Override
    public int delete(int id) throws DAOException {
    	return 0;
    }

    @Override
    public ArrayList<Role> getAll() throws DAOException {
    	ArrayList<Role> roles = new ArrayList<Role>();
        String sql = "SELECT R.id AS id, R.name AS name, "
                + "Ri.denomination AS Ridenomination, "
                + "Ri.description AS Ridescription, Ri.id AS Riid "
                + "FROM Roles AS R "
                + "JOIN OwnsByDefault AS O ON O.fk_role_id = R.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                // Know if the user manipulated changed.
                int previousId = 0;
                ArrayList<Right> rights = new ArrayList<Right>();
                Role role = new Role();
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
                            role.setRights(rights);
                            roles.add(role);   

                            rights = new ArrayList<Right>();
                            role = new Role();  
                        }   

                        role.setId(rs.getInt("id"));
                        role.setName(rs.getString("name"));
                        previousId = rs.getInt("id");                      
                    }

                    // Same role as previous only, we add the next right
                    int rightId = rs.getInt("Riid");
                    String rightDenomination = rs.getString("Ridenomination");
                    String rightDescription = rs.getString("Ridescription");
                    Right right = new Right(rightId, rightDenomination, rightDescription);
                    rights.add(right);

                } while (rs.next());
                // Push the last role
                role.setRights(rights);
                roles.add(role);
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : RoleDAO getAll() :" + e.getMessage(), e);
        }
        return roles;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    @Override
    public Role getByName(String name) throws DAOException {
        Role role = null;
        String sql = "SELECT R.id AS id, R.name AS name "
                + "FROM Roles AS R "
                + "WHERE name = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, name);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : RoleDAO getByName(" + name + ") :" + e.getMessage(), e);
        }
        return role;
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        //Test getBy()
    	RoleDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getRoleDAO();
        Role role1 = test.getById(1) ;
        Role role2 = test.getByName("family member") ;
        System.out.println(role1);
        System.out.println(role2);
        
        //Test Update 
        /*
        role2.setRights(role1.getRights());
        test.update(role2);
        System.out.println(role2);
        */
        
        // Test getAll()
        
        System.out.println(" \n\n **Ici commence la requête getAll() ** \n\n");
        System.out.println(test.getAll());
    }

    
}

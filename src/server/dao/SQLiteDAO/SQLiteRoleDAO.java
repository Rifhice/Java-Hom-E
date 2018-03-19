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
                String sqlGetLastId = "SELECT last_insert_rowid()";
                PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                int id = prepStatLastId.executeQuery().getInt(1);
                role.setId(id);
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
    	String sql = "SELECT  R.id AS id, R.name AS name,"
    			+ "Ri.description AS Ridescription, Ri.id AS Riid, "
    			+ "Ri.denomination AS Ridenomination \"\r\n"  
                + "FROM Roles AS R ,"
                + "JOIN OwnsByDefault AS O ON O.fk_role_id = R.id "
                + "JOIN Rights AS Ri ON Ri.id = O.fk_right_id "
                + "WHERE R.id = ?;";

    	try {
    		PreparedStatement prepStat = this.connect.prepareStatement(sql);
    		prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {
                ArrayList<Right> rights = new ArrayList<Right>();
                do {
                    role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setName(rs.getString("name"));         

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
    	
        return null;
    }

    @Override
    public boolean update(Role obj) throws DAOException {
    	//TODO � modifier
    	// Update Role
        String sql = "UPDATE roles "
                + "SET name = ?, rights = ? "
                + "WHERE id = ?";
        int userUpdated = 0;
        /*try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setInt(2, obj.getName());
            prepStat.setInt(3, obj.getId());
            userUpdated = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        // Delete his rights
        String sqlDeleteRights = "DELETE FROM Owns "
                + "WHERE fk_user_id = ?";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlDeleteRights);
            prepStat.setInt(1, obj.getId());
            prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }        

        // Update his rights
        String sqlInsertRights = "INSERT INTO Owns "
                + "(fk_user_id, fk_user_id) VALUES "
                + "(?, ?);";

        if(obj.getRights() != null) {
            for (Right right : obj.getRights()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, right.getId());
                    prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : UserDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
                }
            } 
        }*/

        return userUpdated > 0;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<Role> getAll() throws DAOException {
        ArrayList<Role> roles = new ArrayList<Role>();
        String sql = "SELECT R.id AS id, R.name AS name "
                + "FROM Roles AS R "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
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
        RoleDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getRoleDAO();
        System.out.println(test.getById(1));
    }

    
}

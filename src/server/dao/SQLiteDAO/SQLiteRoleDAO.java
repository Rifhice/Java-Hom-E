package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.RoleDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Role getById(int id) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Role obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
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
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        RoleDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getRoleDAO();

    }
}

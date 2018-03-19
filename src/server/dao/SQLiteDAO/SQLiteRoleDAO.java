package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.RoleDAO;
import server.models.Role;

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
        // TODO Auto-generated method stub
        return null;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
       
    }
}

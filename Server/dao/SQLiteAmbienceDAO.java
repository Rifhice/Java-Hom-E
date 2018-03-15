package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import factories.AbstractDAOFactory;
import models.Ambience;
import models.Behaviour;
import models.User;

public class SQLiteAmbienceDAO extends AmbienceDAO {
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteAmbienceDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public boolean create(Ambience obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Ambience getById(int id) throws DAOException {
        Ambience ambience = null;
        return ambience;
    }

    @Override
    public boolean update(Ambience obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
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
    public ArrayList<Ambience> getAll() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    @Override
    public List<Behaviour> getBehaviours() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        UserDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getUserDAO();
        User u = test.getByPseudo("owner");
        System.out.println(u.getRoleId());
    }

    

	
}

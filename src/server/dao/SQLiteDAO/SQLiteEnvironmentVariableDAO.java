package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.EnvironmentVariableDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Right;
import server.models.environmentVariable.EnvironmentVariable;
import server.models.environmentVariable.Value;

public class SQLiteEnvironmentVariableDAO extends EnvironmentVariableDAO {

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteEnvironmentVariableDAO(Connection connectionDriver) {
        super(connectionDriver);
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //

    @Override
    public EnvironmentVariable create(EnvironmentVariable obj) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EnvironmentVariable getById(int id) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(EnvironmentVariable obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<EnvironmentVariable> getAll() throws DAOException {
        ArrayList<EnvironmentVariable> evs = new ArrayList<EnvironmentVariable>();
        
        String sql = "SELECT Ev.id AS Evid, Ev.name AS Evname, Ev.description AS Evdescription "
                + "FROM EnvironmentVariables AS Ev "
                + ";";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) { 
                do {
                    EnvironmentVariable ev = null;     
                    ev = new EnvironmentVariable();
                    ev.setId(rs.getInt("Evid"));
                    ev.setName(rs.getString("Evname"));
                    ev.setDescription(rs.getString("Evdescription"));    
                    evs.add(ev);
                } while (rs.next());  
            }            
        }
        catch(SQLException e) {
            throw new DAOException("DAOException : EnvironmentVariable getAll() : " + e.getMessage(),e);
        }   
        return evs;
    }


    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        EnvironmentVariableDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getEnvironmentVariableDAO();
        System.out.println(test.getAll());
    }



}

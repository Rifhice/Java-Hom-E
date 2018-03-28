package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.EnvironmentVariableDAO;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.environmentVariable.EnvironmentVariable;

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
        // TODO Auto-generated method stub
        return null;
    }


    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        UserDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getUserDAO();
        System.out.println(test.getByPseudo("The Boss"));
    }



}

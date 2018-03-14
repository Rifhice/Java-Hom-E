package dao;

import java.sql.Connection;

import models.Actuator;

public class SQLiteActuatorDAO extends ActuatorDAO  {
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
        // TODO Auto-generated constructor stub
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public boolean create(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Actuator getById(int id) throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    
}

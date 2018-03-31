package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.Sensor;

public abstract class SensorDAO extends DAO<Sensor>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SensorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    public abstract int changeIsActivated(int id,boolean bool);
    
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
}
package server.dao;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Actuator;
import server.models.Sensor;

public abstract class SensorDAO extends DAO<Sensor>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SensorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
}
package server.dao;

import java.sql.Connection;

import server.models.Actuator;

public abstract class ActuatorDAO extends DAO<Actuator>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
}

package dao;

import java.sql.Connection;

import models.Actuator;

public abstract class ActuatorDAO extends DAO<Actuator>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
}

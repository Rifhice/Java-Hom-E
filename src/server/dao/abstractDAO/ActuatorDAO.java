package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.Actuator;

public abstract class ActuatorDAO extends DAO<Actuator>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    public abstract int changeIsActivated(int id,boolean bool);
    public abstract int update(int idActuator,String name,String description, int idCategory);
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
}

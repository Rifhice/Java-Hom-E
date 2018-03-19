package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.Role;

public abstract class RoleDAO extends DAO<Role>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public RoleDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
}
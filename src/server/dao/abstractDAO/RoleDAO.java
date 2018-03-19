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
    /**
     * Return a Role according to the name given.
     * @param name
     * @return Role, the role matching the name given or null if none
     * @throws DAOException
     */
    public abstract Role getByName(String name) throws DAOException;

}
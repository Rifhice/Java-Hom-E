package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.environmentVariable.EnvironmentVariable;

public abstract class EnvironmentVariableDAO extends DAO<EnvironmentVariable>{
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public EnvironmentVariableDAO(Connection connect) {
        super(connect);
    }
    
    public abstract int update(int id, String name,String description, String unit);
}

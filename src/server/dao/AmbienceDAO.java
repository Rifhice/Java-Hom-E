package server.dao;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Ambience;
import server.models.Behaviour;

public abstract class AmbienceDAO extends DAO<Ambience> {
    
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public AmbienceDAO(Connection connectionDriver) {
        super(connectionDriver);
    }
    
    // ======================== //
    // ==== CUSTOM METHODS ==== //
    // ======================== //
    /**
     * 
     * @return A list of the different behaviours used by the ambience
     * @throws DAOException
     */
	public abstract ArrayList<Behaviour> getBehaviours() throws DAOException;
	
}

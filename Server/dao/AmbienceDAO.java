package dao;

import java.sql.Connection;
import java.util.List;

import models.Ambience;
import models.Behaviour;
import models.Ambience;

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
	public abstract List<Behaviour> getBehaviours() throws DAOException;
	
}

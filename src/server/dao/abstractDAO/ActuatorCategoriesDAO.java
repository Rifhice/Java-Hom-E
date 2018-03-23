package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.categories.ActuatorCategory;

public abstract class ActuatorCategoriesDAO extends DAO<ActuatorCategory>{

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ActuatorCategoriesDAO(Connection connect) {
        super(connect);
	}
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //

}

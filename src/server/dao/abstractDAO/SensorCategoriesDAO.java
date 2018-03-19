package server.dao.abstractDAO;

import java.sql.Connection;

import server.models.categories.SensorCategory;

public abstract class SensorCategoriesDAO extends DAO<SensorCategory>{
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public SensorCategoriesDAO(Connection connect) {
		super(connect);
	}
	  
    // ================= //
    // ==== METHODS ==== //
    // ================= //

}

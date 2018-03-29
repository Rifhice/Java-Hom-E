package server.dao.abstractDAO;

import java.sql.Connection;
import server.models.ComplexAction;

public abstract class ComplexActionDAO extends DAO<ComplexAction>{
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public ComplexActionDAO(Connection connect) {
		super(connect);
	}

}

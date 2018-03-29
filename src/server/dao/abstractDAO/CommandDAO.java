package server.dao.abstractDAO;

import java.sql.Connection;
import server.models.Command;

public abstract class CommandDAO extends DAO<Command>{
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public CommandDAO(Connection connect) {
		super(connect);
	}
}

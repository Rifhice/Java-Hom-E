package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Command;

public abstract class CommandDAO extends DAO<Command>{
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public CommandDAO(Connection connect) {
		super(connect);
	}
	
	public abstract ArrayList<Command> getByActuatorId(int id);
}

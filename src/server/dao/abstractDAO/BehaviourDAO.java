package server.dao.abstractDAO;

import java.sql.Connection;
import server.models.Behaviour;

public abstract class BehaviourDAO extends DAO<Behaviour>{

	public BehaviourDAO(Connection connect) {
		super(connect);
	}
}

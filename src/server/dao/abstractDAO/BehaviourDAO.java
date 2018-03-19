package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Behaviour;

public class BehaviourDAO extends DAO<Behaviour>{

	public BehaviourDAO(Connection connect) {
		super(connect);
	}

	@Override
	public Behaviour create(Behaviour obj) throws DAOException {
		return null;
	}

	@Override
	public Behaviour getById(int id) throws DAOException {
		return null;
	}

	@Override
	public boolean update(Behaviour obj) throws DAOException {
		return false;
	}

	@Override
	public int delete(int id) throws DAOException {
		return 0;
	}

	@Override
	public ArrayList<Behaviour> getAll() throws DAOException {
		return null;
	}

}

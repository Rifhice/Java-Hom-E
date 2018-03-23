package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.Behaviour;
import server.models.Command;

public abstract class CommandDAO extends DAO<Command>{

	public CommandDAO(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Command create(Command obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Command obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Command> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}

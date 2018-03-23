package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.ComplexAction;

public abstract class ComplexActionDAO extends DAO<ComplexAction>{

	public ComplexActionDAO(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ComplexAction create(ComplexAction obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplexAction getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(ComplexAction obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ComplexAction> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}

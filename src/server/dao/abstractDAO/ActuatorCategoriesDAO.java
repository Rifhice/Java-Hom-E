package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.categories.ActuatorCategory;

public class ActuatorCategoriesDAO extends DAO<ActuatorCategory>{

	public ActuatorCategoriesDAO(Connection connect) {
		super(connect);
	}

	@Override
	public ActuatorCategory create(ActuatorCategory obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActuatorCategory getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ActuatorCategory obj) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ActuatorCategory> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}

package server.dao.abstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

import server.models.categories.SensorCategory;

public class SensorCategoriesDAO extends DAO<SensorCategory>{

	public SensorCategoriesDAO(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SensorCategory create(SensorCategory obj) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SensorCategory getById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(SensorCategory obj) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<SensorCategory> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


}

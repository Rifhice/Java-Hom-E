package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.ComplexActionDAO;
import server.dao.abstractDAO.DAOException;
import server.models.Behaviour;
import server.models.Command;
import server.models.ComplexAction;

public class SQLiteComplexActionDAO extends ComplexActionDAO {

	public SQLiteComplexActionDAO(Connection connect) {
		super(connect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ComplexAction create(ComplexAction obj) throws DAOException {
		return null;
	}



	@Override
	public int update(ComplexAction obj) throws DAOException {
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<ComplexAction> getAll() throws DAOException {
		ArrayList<ComplexAction> actions = new ArrayList<ComplexAction>();
        String sql = "SELECT * FROM ComplexActions";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            ComplexAction action = null;
            while (rs.next()) {
            	//TODO manque pleins de parametres
            	action = new ComplexAction();
            	action.setName(rs.getString("name"));
            	action.setId(rs.getInt("id"));
            	actions.add(action);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return actions;
	}
	
}

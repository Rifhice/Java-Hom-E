package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.CommandDAO;
import server.dao.abstractDAO.ComplexActionDAO;
import server.dao.abstractDAO.DAOException;
import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.AtomicAction;
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
            	action.setAtomicActions(getAllAtomicAction(action.getId()));
            	actions.add(action);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ComplexCommandDAO getAll() :" + e.getMessage(), e);
        }
        return actions;
	}
	
	private ArrayList<AtomicAction> getAllAtomicAction(int idComplex) throws DAOException{
		ArrayList<AtomicAction> actions = new ArrayList<AtomicAction>();
        String sql = "SELECT * FROM Gathers WHERE fk_complexAction_id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, idComplex);
            ResultSet rs = prepStat.executeQuery();
            while (rs.next()) {
            	actions.add(getAtomicAction(rs.getInt("fk_atomicAction_id")));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
        return actions;
	}
	
	private AtomicAction getAtomicAction(int id) throws DAOException{
        String sql = "SELECT * FROM AtomicActions WHERE id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();
            AtomicAction action = null;
            while (rs.next()) {
            	action = new AtomicAction();
            	action.setName(rs.getString("name"));
            	action.setId(rs.getInt("id"));
            	action.setExecutable(rs.getString("executable"));
            	action.setActuator(new Actuator(rs.getInt("fk_actuator_id")));
            	return action;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : CommandDAO getAll() :" + e.getMessage(), e);
        }
		return null;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(AbstractDAOFactory.getFactory(0).getComplexActionDAO().getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}

package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.BehaviourDAO;
import server.factories.AbstractDAOFactory;
import server.models.Behaviour;
import server.models.Right;
import server.models.Role;
import server.models.User;
import server.models.evaluable.Expression;

public class SQLiteBehaviourDAO extends BehaviourDAO{



	public SQLiteBehaviourDAO(Connection connectionDriver) {
		super(connectionDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Behaviour create(Behaviour obj) throws DAOException {
		 Behaviour behaviour = obj;

	        String sql = "INSERT INTO Behaviours "
	                + "(name, isActivated, fk_expression_id) VALUES "
	                + "(?, ?, ?);";

	        // Insert the User
	        int created = 0;
	        try {
	            PreparedStatement prepStat = this.connect.prepareStatement(sql);
	            prepStat.setString(1, obj.getName());
	            prepStat.setBoolean(2, obj.isActivated());
	            prepStat.setInt(3, obj.getExpression().getId());
	            created = prepStat.executeUpdate();

	            // Get the id generated for this object
	            if(created > 0) {
	                String sqlGetLastId = "SELECT last_insert_rowid()";
	                PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
	                int id = prepStatLastId.executeQuery().getInt(1);
	                behaviour.setId(id);
	            }
	            else {
	                behaviour = null;
	            }
	        } catch (SQLException e) {
	            throw new DAOException("DAOException : BehaviourDAO create(" + obj.getName() + ") :" + e.getMessage(), e); 
	        }
	        return behaviour;
	}

	@Override
	public Behaviour getById(int id) throws DAOException {
		Behaviour behaviour = null;
        String sql = "SELECT B.id AS id, B.name AS name, B.isActivated AS isActivated, "
                + "E.id AS Eid, E.operators AS Eoperators "
                + "FROM Behaviour AS B "
                + "JOIN Expression AS E ON E.id = B.fk_expression_id "
                + "WHERE E.id = ?;";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                behaviour = new Behaviour();
                behaviour.setId(rs.getInt("id"));
                behaviour.setName(rs.getString("name"));
                behaviour.setActivated(rs.getBoolean("isActivated"));
                try {
					JSONObject JSON = new JSONObject(rs.getString("Eoperators"));
					JSONArray array = JSON.getJSONArray("operators");
					ArrayList<String> arrayl = new ArrayList(array.toList());
					behaviour.setExpression(new Expression(rs.getInt("Eid"), arrayl));
				} catch (Exception e) {
					throw new Exception(e); 
				}
                
               ; 
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }
       
        behaviour.setExpression(this.getExpression(behaviour.getId()));
        
        return behaviour;
	}

	@Override
	public int update(Behaviour obj) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Behaviour> getAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	
}

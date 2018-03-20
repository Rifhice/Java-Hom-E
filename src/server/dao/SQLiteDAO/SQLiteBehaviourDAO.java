package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.BehaviourDAO;
import server.models.Actuator;
import server.models.Behaviour;

public class SQLiteBehaviourDAO extends BehaviourDAO{

    public SQLiteBehaviourDAO(Connection connectionDriver) {
        super(connectionDriver);
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
        // TODO Auto-generated method stub
        return null;
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
        // Get all the id
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String sqlAllIds = "SELECT id FROM Behaviours";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlAllIds);
            ResultSet rs = prepStat.executeQuery();
            while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch(SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getAll() :" + e.getMessage(), e);
        }
        
        // Get all Actuators using getBydId()
        ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
        for (int id : ids) {
           behaviours.add(this.getById(id));
        }
        return behaviours;
    }


}

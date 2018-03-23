package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import server.dao.abstractDAO.AmbienceDAO;
import server.dao.abstractDAO.DAOException;
import server.models.Ambience;
import server.models.Behaviour;
import server.models.Right;

public class SQLiteAmbienceDAO extends AmbienceDAO {

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteAmbienceDAO(Connection connectionDriver) {
        super(connectionDriver);
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public Ambience create(Ambience obj) throws DAOException {
        // TODO Auto-generated method stub
    	Ambience ambience = obj;
    	
    	String sql = "INSERT INTO Ambiences ('name') VALUES (?)";
    	// Insert the ambience
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            created = prepStat.executeUpdate();

            // Get the id generated for this object
            if(created > 0) {
                String sqlGetLastId = "SELECT last_insert_rowid()";
                PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                int id = prepStatLastId.executeQuery().getInt(1);
                ambience.setId(id);
            }
            else {
                ambience = null;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : Ambience create(" + obj.getName() + ") :" + e.getMessage(), e); 
        }
        
        // Adding his behaviours
        String sqlInsertRights = "INSERT INTO Composes "
                + "(fk_ambience_id, fk_behaviour_id) VALUES "
                + "(?, ?);";

        int behaviourInserted = 0;
        if(obj.getBehaviours() != null) {
            for (Behaviour behaviour : obj.getBehaviours()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertRights);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, behaviour.getId());
                    behaviourInserted = prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : Ambience create(" + obj.getId() + ") :" + e.getMessage(), e); 
                }   
            } 
        }
        return ambience;
    }

    @Override
    public Ambience getById(int id) throws DAOException {
        Ambience ambience = null;
        return ambience;
    }

    @Override
    public int update(Ambience obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int delete(int id) throws DAOException {
        String sql = "DELETE FROM Users WHERE id = ?";
        int deleted = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            deleted = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO delete(" + id + ") :" + e.getMessage(), e);
        }
        return deleted;
    }


    // TODO : return the ArrayList<Behaviour> too
    @Override
    public ArrayList<Ambience> getAll() throws DAOException {
        ArrayList<Ambience> ambiences = new ArrayList<Ambience>();
        String sql = "SELECT A.id, A.name, B.id AS id_behaviour"
                + " FROM Ambiences AS A"
        		+ " JOIN Composes AS C ON A.id = C.fk_ambience_id"
                + " JOIN Behaviours AS B ON B.id = C.fk_behaviour_id"
        		+ " GROUP BY A.id";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
        	int previousId = -1;
            Ambience ambience = null;
            while (rs.next()) {
            	int id = rs.getInt("id");
            	if(previousId == -1) {
            		ambience = new Ambience();
                    ambience.setId(id);
                    ambience.setName(rs.getString("name"));
            		previousId = id;
            	}
            	if(id != previousId) {
            		previousId = id;
                    ambiences.add(ambience);
                    ambience = new Ambience();
                    ambience.setId(id);
                    ambience.setName(rs.getString("name"));
            	}
            	Behaviour behaviour = new Behaviour(rs.getInt("id_behaviour"), null);
            	ambience.addBehaviour(behaviour);
                
            }
            ambiences.add(ambience);
        } catch (SQLException e) {
            throw new DAOException("DAOException : AmbienceDAO getAll() :" + e.getMessage(), e);
        }
        return ambiences;
    }

    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    @Override
    public ArrayList<Behaviour> getBehaviours() throws DAOException {
        // TODO Auto-generated method stub
        return null;
    }

    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {

    }




}

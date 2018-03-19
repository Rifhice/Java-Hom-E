package server.dao.SQLiteDAO;

import java.sql.*;
import java.util.ArrayList;

import server.dao.abstractDAO.AmbienceDAO;
import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.UserDAO;
import server.factories.AbstractDAOFactory;
import server.models.Ambience;
import server.models.Behaviour;
import server.models.User;

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
        return obj;
    }

    @Override
    public Ambience getById(int id) throws DAOException {
        Ambience ambience = null;
        return ambience;
    }

    @Override
    public boolean update(Ambience obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
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
        String sql = "SELECT A.id AS id, A.name AS name"
                + "FROM Ambiences AS A;";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();

            while (rs.next()) {
                Ambience ambience = new Ambience();
                ambience.setId(rs.getInt("id"));
                ambience.setName(rs.getString("name"));
                ambiences.add(ambience);
            }

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

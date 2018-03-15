package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Actuator;
import models.User;

public class SQLiteActuatorDAO extends ActuatorDAO  {
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLiteActuatorDAO(Connection connectionDriver) {
        super(connectionDriver);
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public boolean create(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Actuator getById(int id) throws DAOException {
        Actuator actuator = null;
        // TODO
        String sql = "SELECT U.id AS id, U.pseudo AS pseudo, U.password AS password, R.id AS Rid, R.name AS Rname "
                + "FROM Users AS U "
                + "JOIN Roles AS R ON R.id = U.fk_role_id "
                + "WHERE U.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                actuator = new Actuator();
                // TODO
                /*
                actuator.setId(rs.getInt("id"));
                actuator.setName(rs.getString("pseudo"));
                actuator.setDescription(rs.getString("password"));
                */
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return actuator;
    }

    @Override
    public boolean update(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    
}

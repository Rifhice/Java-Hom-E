package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.Sensor;

public class SQLLiteSensorDao extends SensorDAO{
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public SQLLiteSensorDao(Connection connectionDriver) {
        super(connectionDriver);
    }    
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    @Override
    public Sensor create(Sensor obj) throws DAOException {
        Sensor sensor = obj;
        
        String sql = "INSERT INTO Sensors "
                + "(name, description, fk_sensorCategory_id) VALUES "
                + "(?, ?, ?)";
        
        // Insert the object
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              prepStat.setInt(3, obj.getSensorCategoryId());
              created = prepStat.executeUpdate();
              
              // Get the id generated for this object
              if(created > 0) {
                  String sqlGetLastId = "SELECT last_insert_rowid()";
                  PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                  int id = prepStatLastId.executeQuery().getInt(1);
                  sensor.setId(id);
              }
              else {
                  sensor = null;
              }
          } catch (SQLException e) {
              throw new DAOException("DAOException : SensorDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return sensor;
    }

    // TODO : return ArrayList<Command>
    @Override
    public Sensor getById(int id) throws DAOException {
        Sensor sensor = null;
        // TODO
        String sql = "SELECT S.id AS id, S.name AS name, S.description AS description "
                + "FROM Sensors AS S "
                + "WHERE S.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
            	sensor = new Sensor();
            	sensor.setId(rs.getInt("id"));
            	sensor.setName(rs.getString("name"));
            	sensor.setDescription(rs.getString("description"));
                // TODO list of commands
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return sensor;
    }

    @Override
    public boolean update(Sensor obj) throws DAOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int delete(int id) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ArrayList<Sensor> getAll() throws DAOException {
    	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
        // TODO
        String sql = "SELECT * FROM Sensors";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            Sensor sensor = null;
            while (rs.next()) {
            	sensor = new Sensor();
            	sensor.setId(rs.getInt("id"));
            	sensor.setName(rs.getString("name"));
            	sensor.setDescription(rs.getString("description"));
                sensors.add(sensor);
                // TODO list of commands
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getAll() :" + e.getMessage(), e);
        }
        return sensors;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        SensorDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getSensorDAO();
        System.out.println(test.create(new Sensor("test","qsd")));
        System.out.println(test.getAll());
    }
}

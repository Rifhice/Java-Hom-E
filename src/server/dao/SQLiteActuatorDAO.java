package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.Sensor;

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
        String sql = "INSERT INTO Actuators "
                + "(name, description, fk_actuatorCategory_id) VALUES "
                + "(?, ?, ?)";
        int created = 0;
          try {
              PreparedStatement prepStat = this.connect.prepareStatement(sql);
              prepStat.setString(1, obj.getName());
              prepStat.setString(2, obj.getDescription());
              prepStat.setInt(3, obj.getActuatorCategoryId());
              created = prepStat.executeUpdate();
          } catch (SQLException e) {
              throw new DAOException("DAOException : ActuatorDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
          }
        return created > 0;
    }

    // TODO : return ArrayList<Command>
    @Override
    public Actuator getById(int id) throws DAOException {
        Actuator actuator = null;
        // TODO
        String sql = "SELECT A.id AS id, A.name AS name, A.description AS description, AC.id AS ACid, AC.name AS ACname, AC.description AS ACdescription "
                + "FROM Actuators AS A "
                + "JOIN actuatorCategories AS AC ON AC.id = A.actuator_category_id "
                + "WHERE A.id = ?";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if (rs.next()) {
                actuator = new Actuator();
                actuator.setId(rs.getInt("id"));
                actuator.setName(rs.getString("name"));
                actuator.setDescription(rs.getString("description"));
                actuator.setActuatorCategoryId(rs.getInt("ACid"));
                actuator.setActuatorCategoryName(rs.getString("ACname"));
                actuator.setActuatorCategoryDescription(rs.getString("ACdescription"));
                // TODO list of commands
                
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getById(" + id + ") :" + e.getMessage(), e);
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

    @Override
    public ArrayList<Actuator> getAll() throws DAOException {
    	ArrayList<Actuator> actuators = new ArrayList<Actuator>();
        // TODO
        String sql = "SELECT * FROM Actuators";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            Actuator actuator = null;
            while (rs.next()) {
            	actuator = new Actuator();
            	actuator.setId(rs.getInt("id"));
            	actuator.setName(rs.getString("name"));
            	actuator.setDescription(rs.getString("description"));
            	actuators.add(actuator);
                // TODO list of commands
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : SensorDAO getAll() :" + e.getMessage(), e);
        }
        return actuators;
    }
    
    // ======================== //
    // ==== Custom methods ==== //
    // ======================== //
    
    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        ActuatorDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getActuatorDAO();
        System.out.println(test.create(new Actuator("test","qsd")));
        System.out.println(test.getAll());
    }
    
}

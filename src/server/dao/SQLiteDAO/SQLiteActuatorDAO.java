package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.dao.abstractDAO.ActuatorDAO;
import server.dao.abstractDAO.DAOException;
import server.factories.AbstractDAOFactory;
import server.models.Actuator;
import server.models.Command;
import server.models.categories.ActuatorCategory;

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
    public Actuator create(Actuator obj) throws DAOException {
        Actuator actuator = obj;
        
        String sql = "INSERT INTO Actuators "
                + "(name, description, fk_actuatorCategory_id) VALUES "
                + "(?, ?, ?)";
        
        // Insert the object
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setString(2, obj.getDescription());
            if(obj.getActuatorCategory() != null) {
                prepStat.setInt(3, obj.getActuatorCategory().getId());
            }
            created = prepStat.executeUpdate();

            // Get the id generated for this object
            if(created > 0) {
                String sqlGetLastId = "SELECT last_insert_rowid()";
                PreparedStatement prepStatLastId = this.connect.prepareStatement(sqlGetLastId);
                int id = prepStatLastId.executeQuery().getInt(1);
                actuator.setId(id);
            }
            else {
                actuator = null;
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO create(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        return actuator;
    }

    @Override
    public Actuator getById(int id) throws DAOException {
        Actuator actuator = null;
        String sql = "SELECT A.id AS id, A.name AS name, A.description AS description, "
                + "AC.id AS ACid, AC.name AS ACname, AC.description AS ACdescription, "
                + "C.id AS Cid, C.name AS Cname "
                + "FROM Actuators AS A "
                + "JOIN ActuatorCategories AS AC ON AC.id = A.fk_actuatorCategory_id "
                + "JOIN commands AS C ON C.id = A.id "
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
                
                ActuatorCategory ActCat = new ActuatorCategory(rs.getInt("ACid"), rs.getString("ACname"), rs.getString("ACdescription"));
                actuator.setActuatorCategory(ActCat);
                
                ArrayList<Command> commands = new ArrayList<Command>();
                do {
                    int commandId = rs.getInt("Cid");
                    String commandName = rs.getString("Cname");
                    Command command = new Command(commandId, commandName);
                    commands.add(command);
                } while(rs.next());
                actuator.setCommands(commands);
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getById(" + id + ") :" + e.getMessage(), e);
        }
        return actuator;
    }

    @Override
    public int update(Actuator obj) throws DAOException {
        // TODO Auto-generated method stub
        return 0;
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
        ArrayList<Actuator> act = test.getAll();
        for (Actuator actuator : act) {
            System.out.println(actuator);            
        }
        System.out.println(test.getById(1));
    }

}

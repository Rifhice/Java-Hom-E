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
import server.models.AtomicAction;
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
                actuator.setId(SQLiteDAOTools.getLastId(connect));
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
                + "JOIN Commands AS C ON C.fk_actuator_id = A.id "
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
                
                // Get commands
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
            
        // Get Atomic Actions
        String sqlAA = "SELECT A.id AS id, A.name AS name, A.description AS description, "
                + "AA.id AS AAid, AA.name AS AAname, AA.executable AS AAexecutable "
                + "FROM Actuators AS A "
                + "JOIN AtomicActions AS AA ON AA.fk_actuator_id = A.id "
                + "WHERE A.id = ?";
        
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlAA);
            prepStat.setInt(1, id);
            ResultSet rsAA = prepStat.executeQuery();

            ArrayList<AtomicAction> atomicActions = new ArrayList<AtomicAction>();
            if(rsAA.next()) {
                do {
                    int atomicActionId = rsAA.getInt("AAid");
                    String atomicActionName = rsAA.getString("AAname");
                    String atomicActionExecutable = rsAA.getString("AAexecutable");
                    AtomicAction aa = new AtomicAction(atomicActionId, atomicActionName, atomicActionExecutable);
                    atomicActions.add(aa);
                } while(rsAA.next());
                actuator.setAtomicActions(atomicActions);
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
    /**
     * Delete an Actuator, his Atomic Actions and his Commands
     */
    public int delete(int id) throws DAOException {
        String sqlActuator = "DELETE FROM Users "
                + "WHERE id = ?";

        String sqlAtomic = "DELETE FROM AtomicActions "
                + "WHERE fk_actuator_id = ?";
        
        String sqlCommands = "DELETE FROM Commands "
                + "WHERE fk_actuator_id = ?";

        int deletedActuator = 0;
        int deletedAtomic = 0;
        int deletedCommands = 0;
        try {
            PreparedStatement prepStatActuator = this.connect.prepareStatement(sqlActuator);
            PreparedStatement prepStatAtomic = this.connect.prepareStatement(sqlAtomic);
            PreparedStatement prepStatCommands = this.connect.prepareStatement(sqlCommands);

            prepStatActuator.setInt(1, id);
            prepStatAtomic.setInt(1, id);
            prepStatCommands.setInt(1, id);

            deletedActuator = prepStatActuator.executeUpdate();
            deletedAtomic = prepStatAtomic.executeUpdate();
            deletedCommands = prepStatCommands.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO delete(" + id + ") :" + e.getMessage(), e);
        }
        return deletedActuator + deletedAtomic + deletedCommands;
    }

    @Override
    public ArrayList<Actuator> getAll() throws DAOException {  
        
        // Get all the id
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String sqlAllId = "SELECT id FROM Actuators";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlAllId);
            ResultSet rs = prepStat.executeQuery();
            while(rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch(SQLException e) {
            throw new DAOException("DAOException : ActuatorDAO getAll() :" + e.getMessage(), e);
        }
        
        // Get all Actuators using getBydId()
        ArrayList<Actuator> actuators = new ArrayList<Actuator>();
        for (int id : ids) {
           actuators.add(this.getById(id));
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
        ArrayList<Actuator> list = test.getAll();
        for (Actuator actuator : list) {
            System.out.println(actuator+"\n===============");
        }
    }

}

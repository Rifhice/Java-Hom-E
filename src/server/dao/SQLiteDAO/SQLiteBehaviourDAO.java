package server.dao.SQLiteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.dao.abstractDAO.DAOException;
import server.dao.abstractDAO.BehaviourDAO;
import server.factories.AbstractDAOFactory;
import server.models.AtomicAction;
import server.models.Behaviour;
import server.models.ComplexAction;
import server.models.Right;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;
import server.models.environmentVariable.Value;
import server.models.evaluable.Block;
import server.models.evaluable.Evaluable;
import server.models.evaluable.Expression;

public class SQLiteBehaviourDAO extends BehaviourDAO{

    public SQLiteBehaviourDAO(Connection connectionDriver) {
        super(connectionDriver);
        // TODO Auto-generated constructor stub
    }


    @Override
    public Behaviour getById(int id) throws DAOException {
        Behaviour behaviour = null;
        String sql = "SELECT * FROM Behaviours B " +
                "WHERE B.id = ?";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                behaviour = new Behaviour();
                behaviour.setId(rs.getInt("id"));
                behaviour.setDescription(rs.getString("description"));
                behaviour.setName(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getById(" + id + ") :" + e.getMessage(), e);
        }
        return behaviour;
    }

    @Override
    public Behaviour create(Behaviour obj) throws DAOException {
        Behaviour behaviour = obj;

        String sql = "INSERT INTO Behaviours "
                + "(name, description, is_activated, fk_expression_id) VALUES "
                + "(?, ?, ?, ?);";

        // Insert the User
        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setString(2, obj.getDescription());
            prepStat.setBoolean(3, obj.isActivated());
            prepStat.setInt(4, obj.getExpression().getId());
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

    /*@Override
	public Behaviour getById(int id) throws DAOException {
		Behaviour behaviour = null;
        String sql = "SELECT B.id AS id, B.name AS name, B.is_activated AS isActivated, "
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
                behaviour.setActivated(rs.getBoolean("is_activated"));
                try {
					JSONObject JSON = new JSONObject(rs.getString("Eoperators"));
					JSONArray array = JSON.getJSONArray("operators");
					ArrayList<String> arrayl = new ArrayList(array.toList());
					behaviour.setExpression(new Expression(rs.getInt("Eid"), arrayl));
				} catch (Exception e) {

				}

               ; 
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : UserDAO getById(" + id + ") :" + e.getMessage(), e);
        }

        behaviour.setAtomicActions(this.getAtomicActions(behaviour.getId()));
        behaviour.setComplexActions(this.getComplexActions(behaviour.getId()));

        return behaviour;
	}

	 public ArrayList<AtomicAction> getAtomicActions(int id) throws DAOException {
	        Behaviour Behaviour = null;
	        // Get executes atomic actions
	        String sql = "SELECT Ac.name AS Acname, Ac.exececutable AS Acexecutable,"
	                + "Ac.id AS Acid "
	                + "FROM Behaviour AS B "
	                + "JOIN Lauches AS L ON L.fk_behaviour_id = B.id "
	                + "JOIN AtomicAction AS AC ON AC.id = L.fk_atomicaction_id "
	                + "WHERE B.id = ?;";

	        ArrayList<AtomicAction> atomiclaunch = new ArrayList<AtomicAction>();

	        try {
	            PreparedStatement prepStat = this.connect.prepareStatement(sql);
	            prepStat.setInt(1, id);
	            ResultSet rs = prepStat.executeQuery();

	            if(rs.next()) {
	                do {
	                    int atomicId = rs.getInt("Acid");
	                    String atomicName = rs.getString("Acname");
	                    String atomicLaunch = rs.getString("Acexecutable");
	                    AtomicAction atomicAction = new AtomicAction(atomicId, atomicName, atomicLaunch);
	                    atomiclaunch.add(atomicAction);
	                } while (rs.next());
	            }
	        } catch (SQLException e) {
	            throw new DAOException("DAOException : BehaviourDAO getAtomicActions(" + id + ") (executes):" + e.getMessage(), e);
	        }



	        // Build the list of DISTINCT Atomic Actions
	        ArrayList<AtomicAction> allAtomicActions = atomiclaunch;
	        boolean isAlreadyHere;
	        if(atomiclaunch != null) {
	            for (AtomicAction AtomicE : atomiclaunch) {
	                isAlreadyHere = false;
	                for (AtomicAction atomic : allAtomicActions) {
	                    if(AtomicE.getId() == atomic.getId()) {
	                        isAlreadyHere = true;
	                    }
	                }
	                if(!isAlreadyHere) {
	                    allAtomicActions.add(AtomicE);
	                }
	            }
	        }        
	        return allAtomicActions;
	    }*/

    // TODO : missing Arraylist<ComplexAction>

    public ArrayList<ComplexAction> getComplexActions(int id) throws DAOException {
        // Get complex actions
        String sql = "SELECT Ca.name AS Caname "
                + "Ca.id AS Caid "
                + "FROM Behaviour AS B "
                + "JOIN Executes AS E ON E.fk_behaviour_id = B.id "
                + "JOIN ComplexAction AS CA ON CA.id = E.fk_complexaction_id "
                + "WHERE B.id = ?;";

        ArrayList<ComplexAction> complexexecute = new ArrayList<ComplexAction>();

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, id);
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                do {
                    // Construct rights in owns
                    int complexId = rs.getInt("Caid");
                    String complexName = rs.getString("Caname");
                    ComplexAction complexAction = new ComplexAction(complexId, complexName);
                    complexexecute.add(complexAction);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getComplexActions(" + id + ") (executes):" + e.getMessage(), e);
        }

        // Build the list of DISTINCT Complex action
        ArrayList<ComplexAction> allComplexActions = complexexecute;
        boolean isAlreadyHere;
        if(complexexecute != null) {
            for (ComplexAction ComplexE : complexexecute) {
                isAlreadyHere = false;
                for (ComplexAction complex : allComplexActions) {
                    if(ComplexE.getId() == complex.getId()) {
                        isAlreadyHere = true;
                    }
                }
                if(!isAlreadyHere) {
                    allComplexActions.add(ComplexE);
                }
            }
        }  

        return allComplexActions;
    }

    @Override
    public int update(Behaviour obj) throws DAOException {
        // Update Behaviour
        String sql = "UPDATE Behaviour "
                + "SET name = ?, description = ?, is_activated = ?, fk_expression_id = ? "
                + "WHERE id = ?";
        int behaviourUpdated = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setString(2, obj.getDescription());
            prepStat.setBoolean(3, obj.isActivated());
            prepStat.setInt(4, obj.getExpression().getId()); // TODO Check if Expression ID is not null
            prepStat.setInt(5, obj.getId());
            behaviourUpdated = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }

        // Delete his atomicActions
        String sqlDeleteAtomics = "DELETE FROM Launches "
                + "WHERE fk_behaviour_id = ?";
        int atomicsDeleted = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sqlDeleteAtomics);
            prepStat.setInt(1, obj.getId());
            atomicsDeleted = prepStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : AtomicsDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
        }        

        // Update his atomicsActions
        String sqlInsertAtomics = "INSERT INTO Launches "
                + "(fk_behaviour_id, fk_atomicaction_id) VALUES "
                + "(?, ?);";

        int atomicsInserted = 0;
        if(obj.getAtomicActions() != null) {
            for (AtomicAction atomicaction : obj.getAtomicActions()) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sqlInsertAtomics);
                    prepStat.setInt(1, obj.getId());
                    prepStat.setInt(2, atomicaction.getId());
                    atomicsInserted = prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : BehaviourDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
                }
            } 
        }

        return behaviourUpdated + atomicsDeleted + atomicsInserted;
    }

    @Override
    public int delete(int id) throws DAOException {
        String sqlBehaviour = "DELETE FROM Behaviour "
                + "WHERE id = ?";

        String sqlLaunches = "DELETE FROM Launches "
                + "WHERE fk_behaviour_id = ?";

        String sqlExecutes = "DELETE FROM Executes "
                + "WHERE fk_behaviour_id = ?";

        int deletedBehaviour = 0;
        int deletedLaunches = 0;
        int deletedExecutes = 0;
        try {
            PreparedStatement prepStatBehaviour = this.connect.prepareStatement(sqlBehaviour);
            PreparedStatement prepStatLaunches = this.connect.prepareStatement(sqlLaunches);
            PreparedStatement prepStatExecutes = this.connect.prepareStatement(sqlExecutes);

            prepStatBehaviour.setInt(1, id);
            prepStatLaunches.setInt(1, id);
            prepStatExecutes.setInt(1, id);

            deletedBehaviour = prepStatBehaviour.executeUpdate();
            deletedLaunches = prepStatLaunches.executeUpdate();
            deletedExecutes = prepStatExecutes.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO delete(" + id + ") :" + e.getMessage(), e);
        }
        return deletedBehaviour + deletedLaunches + deletedExecutes;
    }

    /**
     * Returns the list of all the behaviours. If none, returns.
     * Included with the behaviour : 
     * <ul>
     * <li>AtomicActions</li>
     * <li>Expression (non-recursive, only blocks)</li>
     * <li>Blocks</li>
     * <li>EnvironmentVariable</li>
     * <li>Values (Block and EV ones)</li>
     * <ul>     
     * @throws DAOException
     */
    @Override
    public ArrayList<Behaviour> getAll() throws DAOException {
        ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
        String sql = "SELECT B.id AS id, B.name AS name, B.description AS description, B.is_activated AS isActivated "
                + "FROM Behaviours AS B "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            if(rs.next()) {
                do {
                    Behaviour behaviour = new Behaviour();
                    behaviour.setId(rs.getInt("id"));
                    behaviour.setName(rs.getString("name"));
                    behaviour.setDescription(rs.getString("description"));
                    behaviour.setActivated(rs.getBoolean("isActivated"));
                                   
                    behaviour.setAtomicActions(getAtomicActions(behaviour));
                    behaviour.setExpression(getExpression(behaviour));
                    
                    behaviours.add(behaviour);
                } while(rs.next());
            }
            
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getAll() :" + e.getMessage(), e);
        }
        return behaviours;
    }

    
    // ================================= //
    // ======== HELPERS METHODS ======== //
    // ================================= //
    /**
     * Returns the expression associated to a Behaviour. If none, return null. 
     * By now, does not support the "recursive" expressions.
     * 
     * TODO : support "recursive" expressions
     * 
     * @param behaviour
     * @return
     * @throws DAOException
     */
    public Expression getExpression(Behaviour behaviour) throws DAOException{
        Expression exp = null;
        String sql = "SELECT E.id AS id, E.operators AS operators "
                + "FROM Expressions AS E " 
                + "WHERE E.fk_behaviour_id = ?";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, behaviour.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                exp = new Expression();
                exp.setId(rs.getInt("id"));
                JSONObject JSON = new JSONObject(rs.getString("operators"));
                JSONArray array = JSON.getJSONArray("operators");
                ArrayList<String> arrayl = new ArrayList(array.toList());
                exp.setOperators(arrayl);

                // Get Evaluables (blocks only for now)
                // TODO : get Expressions recursively 
                ArrayList<Evaluable> evaluables = new ArrayList<Evaluable>();
                evaluables = getEvaluables(exp);
                exp.setEvaluables(evaluables);
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getExpression("+ behaviour.getId()+") :" + e.getMessage(), e);
        }
        return exp;
    }
    
    /**
     * Returns the list of Evaluables of an Expression. If none, returns an empty list.
     * Returns only blocks. TODO : return recursive Expression too.
     * @param exp
     * @return
     * @throws DAOException
     */
    public ArrayList<Evaluable> getEvaluables(Expression exp) throws DAOException {
        ArrayList<Evaluable> evaluables = new ArrayList<Evaluable>();
        String sql = "SELECT B.id AS Bid, B.operator AS Boperator "
                + "FROM isPartOf AS IPO "
                + "LEFT JOIN Blocks AS B ON B.id = IPO.fk_block_id "
                + "WHERE IPO.fk_expression_id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, exp.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                do {
                    Block b = new Block();
                    b.setId(rs.getInt("Bid"));
                    b.setOperator(rs.getString("Boperator"));
                    b.setValue(getValue(b));
                    b.setEnvironmentVariable(getEnvironmentVariable(b));                    
                    evaluables.add(b);                   
                } while(rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getEvaluables("+ exp.getId()+") :" + e.getMessage(), e);
        }
        return evaluables;
    }

    /**
     * Returns the EnvironmentVariable of a block. If none, returns null.
     * @param environmentVariable
     * @return
     * @throws DAOException
     */
    public EnvironmentVariable getEnvironmentVariable(Block block) throws DAOException {
        EnvironmentVariable ev = null;
        String sql = "SELECT EV.id AS id, EV.name AS name, EV.description AS description, EV.unit AS unit "
                + "FROM Blocks AS B "
                + "JOIN EnvironmentVariables AS EV ON EV.id = B.fk_environmentVariable_id "
                + "WHERE B.id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, block.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                ev = new EnvironmentVariable();
                ev.setId(rs.getInt("id"));
                ev.setName(rs.getString("name"));
                ev.setDescription(rs.getString("description"));
                ev.setUnit(rs.getString("unit"));
                ev.setValue(getValue(ev));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getEnvironmentVariable("+ block.getId()+") :" + e.getMessage(), e);
        }
        return ev; 
    }

    /**
     * Returns the Value of an EnvironmentVariable. If none, returns null.
     * @param ev
     * @return
     * @throws DAOException
     */
    public Value getValue(EnvironmentVariable ev) throws DAOException {
        Value value = null;
        String sql = "SELECT V.id AS id, "
                + "CV.value_min AS CVvalue_min, CV.value_max AS CVvalue_max, "
                + "CV.current_value AS CVcurrent_value, CV.precision AS CVprecision, "
                + "DV.current_value AS DVcurrent_value, DV.possible_values AS DVpossible_values "
                + "FROM EnvironmentVariables AS EV "
                + "JOIN Vvalues AS V ON V.id = EV.fk_vvalue_id "
                + "LEFT JOIN ContinuousVvalues AS CV ON CV.fk_vvalue_id = V.id "
                + "LEFT JOIN DiscreteVvalues AS DV ON DV.fk_vvalue_id = V.id "
                + "WHERE V.id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, ev.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {                
                if(rs.getInt("DVpossible_values") != 0) {
                    value = new DiscreteValue();
                    ((DiscreteValue)value).setCurrentValue(rs.getString("DVcurrent_value"));
                    
                    JSONObject JSONpossibleValues = new JSONObject(rs.getString("DVpossible_values"));
                    JSONArray JSONarray = JSONpossibleValues.getJSONArray("possibleValues");
                    ArrayList<String> possibleValues = new ArrayList(JSONarray.toList());
                    ((DiscreteValue) value).setPossibleValues(possibleValues);
                }
                else {
                    value = new ContinuousValue();
                    ((ContinuousValue)value).setValueMax(rs.getDouble("CVvalue_max"));
                    ((ContinuousValue)value).setValueMin(rs.getDouble("CVvalue_min"));
                    ((ContinuousValue)value).setCurrentValue(rs.getDouble("CVcurrent_value"));
                    ((ContinuousValue)value).setPrecision(rs.getDouble("CVprecision"));
                }
                value.setId(rs.getInt("id"));
               
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getValue("+ ev.getId()+") :" + e.getMessage(), e);
        }
        return value;
    }

    /**
     * Returns the value of a block. If none, returns null.
     * @param block
     * @return
     * @throws DAOException
     */
    public Value getValue(Block block) throws DAOException {
        Value value = null;
        
        String sql = "SELECT V.id AS id, "
                + "CV.value_min AS CVvalue_min, CV.value_max AS CVvalue_max, "
                + "CV.current_value AS CVcurrent_value, CV.precision AS CVprecision, "
                + "DV.current_value AS DVcurrent_value, DV.possible_values AS DVpossible_values "
                + "FROM Blocks AS B "
                + "JOIN Vvalues AS V ON V.id = B.fk_vvalue_id "
                + "LEFT JOIN ContinuousVvalues AS CV ON CV.fk_vvalue_id = V.id "
                + "LEFT JOIN DiscreteVvalues AS DV ON DV.fk_vvalue_id = V.id "
                + "WHERE V.id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, block.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {                
                if(rs.getString("DVpossible_values") != null) {
                    value = new DiscreteValue();
                    ((DiscreteValue)value).setCurrentValue(rs.getString("DVcurrent_value"));
                    
                    JSONObject JSONpossibleValues = new JSONObject(rs.getString("DVpossible_values"));
                    JSONArray JSONarray = JSONpossibleValues.getJSONArray("possibleValues");
                    ArrayList<String> possibleValues = new ArrayList(JSONarray.toList());
                    ((DiscreteValue) value).setPossibleValues(possibleValues);
                }
                else {
                    value = new ContinuousValue();
                    ((ContinuousValue)value).setValueMax(rs.getDouble("CVvalue_max"));
                    ((ContinuousValue)value).setValueMin(rs.getDouble("CVvalue_min"));
                    ((ContinuousValue)value).setCurrentValue(rs.getDouble("CVcurrent_value"));
                    ((ContinuousValue)value).setPrecision(rs.getDouble("CVprecision"));
                }
                value.setId(rs.getInt("id"));
               
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getValue("+ block.getId()+") :" + e.getMessage(), e);
        }

        return value;
    }
    
    /**
     * Returns the list of atomic actions triggered by a behaviour. If none, returns an empty list.
     * @param behaviour
     * @return 
     * @throws DAOException
     */
    public ArrayList<AtomicAction> getAtomicActions(Behaviour behaviour) throws DAOException {
        ArrayList<AtomicAction> atomicActions = new ArrayList<AtomicAction>();
        String sql = "SELECT AA.id AS id, AA.name AS name, AA.executable AS executable "
                + "FROM Behaviours AS B "
                + "JOIN Launches AS L ON L.fk_behaviour_id = B.id "
                + "JOIN AtomicActions AS AA ON AA.id = L.fk_atomicAction_id "
                + "WHERE B.id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, behaviour.getId());
            ResultSet rs = prepStat.executeQuery();

            if(rs.next()) {
                do {
                    AtomicAction aa = new AtomicAction();
                    aa.setId(rs.getInt("id"));
                    aa.setName(rs.getString("name"));
                    aa.setExecutable(rs.getString("executable"));
                    atomicActions.add(aa);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getAtomicActions(" + behaviour.getId() + ") (owns):" + e.getMessage(), e);
        }
        
        return atomicActions;
    }

    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        BehaviourDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getBehaviourDAO();

        Behaviour b = new Behaviour();
        b = test.getById(1);
        
        Expression e = new Expression();
        e.setId(1);
        
        Block bl = new Block();
        bl.setId(1);
        
        EnvironmentVariable ev = new EnvironmentVariable();
        ev.setId(1);
        
        for(Behaviour beh : test.getAll()){
            System.out.println("\n"+beh);
        }
    }
}

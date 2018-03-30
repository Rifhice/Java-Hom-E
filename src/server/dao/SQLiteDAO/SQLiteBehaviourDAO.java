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
import server.dao.abstractDAO.EnvironmentVariableDAO;
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

        // Insert Behaviour
        String sql = "INSERT INTO Behaviours "
                + "(name, description, is_activated) VALUES "
                + "(?, ?, ?);";

        int created = 0;
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, obj.getName());
            prepStat.setString(2, obj.getDescription());
            prepStat.setBoolean(3, obj.isActivated());

            created = prepStat.executeUpdate();

            if(created > 0) {
                behaviour.setId(SQLiteDAOTools.getLastId(connect));
            }
            else {
                behaviour = null;
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO create(" + obj.getName() + ") :" + e.getMessage(), e); 
        }

        // Insert in others tables
        behaviour.setAtomicActions(createAtomicActions(behaviour.getAtomicActions()));
        behaviour.setExpression(createExpression(behaviour.getExpression()));   
        
        if(behaviour.getExpression().getId() != 0) {
            // Set behaviour id in Expressions
            sql = "UPDATE Expressions "
                    + "SET fk_behaviour_id = ?"
                    + "WHERE id = ? "
                    + ";";
            try {
                PreparedStatement prepStat = this.connect.prepareStatement(sql);
                prepStat.setInt(1, behaviour.getId());
                prepStat.setInt(2, behaviour.getExpression().getId());
                prepStat.executeUpdate();            
            } catch (SQLException e) {
                throw new DAOException("DAOException : BehaviourDAO create(" + obj.getName() + ") inserting behaviour id in expression:" + e.getMessage(), e); 
            }        
        }        

        // Launches 
        ArrayList<AtomicAction> aas = behaviour.getAtomicActions();
        if(aas.size() != 0) {
            sql = "INSERT INTO Launches "
                    + "(fk_behaviour_id, fk_atomicAction_id) VALUES "
                    + "(?,?)"
                    + ";";
            for (int i = 0; i < aas.size(); i++) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sql);
                    prepStat.setInt(1, aas.get(i).getId());
                    prepStat.setInt(2, behaviour.getId());
                    prepStat.executeUpdate();                    
                } catch (SQLException e) {
                    throw new DAOException("DAOException : BehaviourDAO create(" + obj.getId() + ") insert in Launches:" + e.getMessage(), e); 
                }   
            }
        }
        
        // Expression reference in Behaviours
        if(behaviour.getExpression().getId() != 0) {
            sql = "UPDATE Behaviours "
                    + "SET fk_expression_id = ?"
                    + "WHERE id = ? "
                    + ";";   
            try {
                PreparedStatement prepStat = this.connect.prepareStatement(sql);
                prepStat.setInt(1, behaviour.getExpression().getId());
                prepStat.setInt(2, behaviour.getId());
                prepStat.executeUpdate();                    
            } catch (SQLException e) {
                throw new DAOException("DAOException : BehaviourDAO create(" + obj.getId() + ") update fk_exp_id:" + e.getMessage(), e); 
            }   
        }
        return behaviour;
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
            throw new DAOException("DAOException : BehaviourDAO update(" + obj.getId() + ") :" + e.getMessage(), e); 
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
     * Returns the list of all the behaviours. If none, returns an empty list.
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


    // ================================ //
    // ======== HELPER METHODS ======== //
    // ================================ //
    /**
     * Create AtomicActions in DB from a list. If success, returns the list with the id of each
     * atomic action set to the one in DB, else returns the list passed.  
     * @param atomicActions
     * @return
     * @throws DAOException
     */
    public ArrayList<AtomicAction> createAtomicActions(ArrayList<AtomicAction> atomicActions) throws DAOException {
        ArrayList<AtomicAction> aa = atomicActions;

        String sql = "INSERT INTO AtomicActions "
                + "(executable) VALUES "
                + "(?) "
                + ";";

        for (int i = 0; i < aa.size(); i++) {
            try {
                PreparedStatement prepStat = this.connect.prepareStatement(sql);
                prepStat.setString(1, aa.get(i).getExecutable());

                int created = prepStat.executeUpdate();
                if(created > 0) {
                    aa.get(i).setId(SQLiteDAOTools.getLastId(connect));
                }

            } catch (SQLException e) {
                throw new DAOException("DAOException : BehaviourDAO createAtomicActions() :" + e.getMessage(), e);
            }
        }

        return aa;
    }



    /**
     * Create an expression in DB from an object. If success, returns the object with the id set 
     * to the one in DB, else returns the object passed. 
     * @param expression
     * @return
     * @throws DAOException
     */
    public Expression createExpression(Expression expression) throws DAOException {
        Expression exp = expression;

        // Insert Expression
        String sql = "INSERT INTO Expressions "
                + "(operators) VALUES "
                + "(?)"
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            if(expression.getOperators().size() != 0) {
                
                String operators = "{operators:[";
                for (String op : expression.getOperators()) {
                    operators += op + ",";
                }
                
                // Remove last ","
                operators = operators.substring(0, operators.length() - 1);
                operators += "]}";
                prepStat.setString(1, operators); 
            } else {
                prepStat.setString(1, "{operators:[]}");
            }

            int created = prepStat.executeUpdate();
            if(created > 0) {
                exp.setId(SQLiteDAOTools.getLastId(connect));                
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO createExpression(" + exp.getId() + ") :" + e.getMessage(), e); 
        }

        // Exp correctly created ? 
        if(exp.getId() != 0) {

            // Insert Evaluables (blocks only for now) 
            // TODO : Evaluables can be expression too
            exp.setEvaluables(createEvaluables(exp.getEvaluables()));

            // Insert isPartOf
            sql = "INSERT INTO isPartOf "
                    + "(fk_expression_id, fk_block_id) VALUES "
                    + "(?,?) "
                    + ";";
            ArrayList<Evaluable> evs = exp.getEvaluables();
            for (int i = 0; i < evs.size() ; i++ ) {
                try {
                    PreparedStatement prepStat = this.connect.prepareStatement(sql);
                    prepStat.setInt(1, exp.getId());
                    prepStat.setInt(2, ((Block) evs.get(i)).getId());  
                    int created = prepStat.executeUpdate();
                } catch (SQLException e) {
                    throw new DAOException("DAOException : BehaviourDAO create IsPartOf(" + exp.getId() + ") insert into isPartOf:" + e.getMessage(), e); 
                }
            }         

        } 

        return exp;
    }

    /**
     * Create evaluables in DB from a list . If success, returns the list of evaluables with 
     * the id correctly set else, id is not set. Currently, deals only with Blocks.
     * TODO : insert recursive Expressions too
     * @param evaluables
     * @return
     * @throws DAOException
     */
    public ArrayList<Evaluable> createEvaluables(ArrayList<Evaluable> evaluables) throws DAOException {
        ArrayList<Evaluable> evs = evaluables;

        // Create Blocks
        String sql = "INSERT INTO Blocks "
                + "(operator) VALUES "
                + "(?) "
                + ";";
        for (int i = 0; i < evaluables.size(); i++) {
            try {
                PreparedStatement prepStat = this.connect.prepareStatement(sql);
                prepStat.setString(1, ((Block)evs.get(i)).getOperator());
                int created = prepStat.executeUpdate();
                ((Block)evs.get(i)).setId(SQLiteDAOTools.getLastId(connect));
            } catch (SQLException e) {
                throw new DAOException("DAOException : BehaviourDAO createEvaluables() :" + e.getMessage(), e); 
            }
        }
       
        // Update them
        

        for (int i = 0; i < evs.size(); i++) {
            sql = "UPDATE Blocks "
                    + "SET operator = ?, fk_environmentVariable_id = ? "
                    + "WHERE id = ? "
                    + ";";

            try {
                Block block = ((Block)evs.get(i));
                PreparedStatement prepStat = this.connect.prepareStatement(sql);
                
                prepStat.setString(1, block.getOperator());  
                prepStat.setInt(2, block.getEnvironmentVariable().getId());
                prepStat.setInt(3, block.getId());
                
                int created = prepStat.executeUpdate();
                if(created > 0) {
                    ((Block)evs.get(i)).setValue(createValue(((Block)evs.get(i)).getValue())); 
                    // Insert fk_vvalue_id created into blocks
                    sql = "UPDATE Blocks "
                            + "SET fk_vvalue_id = ? "
                            + "WHERE id = ? "
                            + ";";
                    try {
                        PreparedStatement prepStatFkValue = this.connect.prepareStatement(sql);
                        prepStatFkValue.setInt(1, ((Block)evs.get(i)).getValue().getId());
                        prepStatFkValue.setInt(2, ((Block)evs.get(i)).getId());
                        prepStatFkValue.executeUpdate();

                    } catch (SQLException e) {
                        throw new DAOException("DAOException : BehaviourDAO createEvaluables() inserting fk_vvalue_id :" + e.getMessage(), e); 
                    }
                            
                }
            } catch (SQLException e) {
                throw new DAOException("DAOException : BehaviourDAO createEvaluables() :" + e.getMessage(), e); 
            }
        }

        return evs;
    }

    /**
     * Create an EnvironmentVariable in DB from an object. 
     * If success, returns the EnvironmentVariable with the id correctly set, else id is not set. 
     * Insert into Values too. 
     * @param environmentVariable
     * @return
     * @throws DAOException
     * @see {@link #createValue(Value)}
     */
    /*public EnvironmentVariable createEnvironmentVariable(EnvironmentVariable environmentVariable) throws DAOException {
        EnvironmentVariable ev = environmentVariable;
        String sql = "INSERT INTO EnvironmentVariables "
                + "(name, description, unit) VALUES "
                + "(?,?,?)"
                + ";";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setString(1, ev.getName());  
            prepStat.setString(2, ev.getDescription());  
            prepStat.setString(3, ev.getUnit());  
            int created = prepStat.executeUpdate();
            if(created > 0) {
                ev.setId(SQLiteDAOTools.getLastId(connect));
                // Value
                ev.setValue(createValue(ev.getValue()));
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO createEnvironmentVariable() :" + e.getMessage(), e); 
        }

        return ev;
    }
    */

    /**
     * Create a Value in DB from an object. 
     * If success, returns the value with the id correctly set, else id is not set. 
     * @param value
     * @return
     * @throws DAOException
     */
    public Value createValue(Value value) throws DAOException {
        Value v = value;

        String sql = "INSERT INTO VValues "
                + "default VALUES"
                + ";";

        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            int created = prepStat.executeUpdate();

            if(created > 0) {
                v.setId(SQLiteDAOTools.getLastId(connect));

                // Continuous Value
                if(value instanceof ContinuousValue) {
                    ContinuousValue cv = (ContinuousValue) v;
                    
                    // Test if there are details about the value (value from a sensor)
                    boolean isFromSensor = false;                  
                    if(cv.getValueMin() != 0) {
                        sql = "INSERT INTO ContinuousVValues "
                                + "(current_value, fk_vvalue_id, value_min, value_max, precision) VALUES "
                                + "(?,?,?,?,?)"
                                + ";";
                        isFromSensor = true;
                    }
                    else {
                        sql = "INSERT INTO ContinuousVValues "
                                + "(current_value, fk_vvalue_id) VALUES "
                                + "(?,?)"
                                + ";";
                    }                  
                    try {
                        PreparedStatement prepStatCV = this.connect.prepareStatement(sql);
                        prepStatCV.setDouble(1, cv.getCurrentValue());
                        prepStatCV.setInt(2, cv.getId());

                        if(isFromSensor) {
                            prepStatCV.setDouble(3, cv.getValueMin());
                            prepStatCV.setDouble(4, cv.getValueMax());
                            prepStatCV.setDouble(5, cv.getPrecision());
                        }
                        
                        created = prepStatCV.executeUpdate();
                        if(created > 0) {
                            v.setId(SQLiteDAOTools.getLastId(connect));
                        }

                    } catch (SQLException e) {
                        throw new DAOException("DAOException : BehaviourDAO createValue() :" + e.getMessage(), e); 
                    }
                }
                // Discrete Value
                else if(value instanceof DiscreteValue) {
                    
                    DiscreteValue dv = (DiscreteValue) v;
                    
                    // Test if there are possible values (value of a sensor) 
                    boolean isPossible = false;                    
                    if(dv.getPossibleValues() != null) {
                        sql = "INSERT INTO DiscreteVValues "
                                + "(current_value, fk_vvalue_id, possible_values) VALUES "
                                + "(?,?,?) "
                                + ";";
                        isPossible = true;
                    }
                    else {
                        sql = "INSERT INTO DiscreteVValues "
                                + "(current_value, fk_vvalue_id) VALUES "
                                + "(?,?) "
                                + ";";
                    }                    
                    try {
                        PreparedStatement prepStatDV = this.connect.prepareStatement(sql);

                        prepStatDV.setString(1, dv.getCurrentValue());
                        prepStatDV.setInt(2, dv.getId());

                        if(isPossible) {
                            JSONObject JSON = new JSONObject(dv.getPossibleValues());                        
                            prepStatDV.setString(3, JSON.toString());
                        }                       

                        created = prepStatDV.executeUpdate();
                        if(created > 0) {
                            v.setId(SQLiteDAOTools.getLastId(connect));
                        }

                    } catch (SQLException e) {
                        throw new DAOException("DAOException : BehaviourDAO createValue() :" + e.getMessage(), e); 
                    }

                }
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO createValue() :" + e.getMessage(), e); 
        }

        return v;
    }

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
                if(rs.getString("operators") != null) {
                	
                    JSONObject JSON = new JSONObject(rs.getString("operators"));
                    JSONArray array = JSON.getJSONArray("operators");
                    ArrayList<String> arrayl = new ArrayList(array.toList());
                    exp.setOperators(arrayl);
                } else {
                    exp.setOperators(new ArrayList<String>());
                }

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
                + "WHERE EV.id = ? "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            prepStat.setInt(1, ev.getId());
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
                + "WHERE B.id = ? "
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
        String sql = "SELECT AA.id AS id, AA.executable AS executable "
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
                    aa.setExecutable(rs.getString("executable"));
                    atomicActions.add(aa);
                } while (rs.next());
            }
        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getAtomicActions(" + behaviour.getId() + ") (owns):" + e.getMessage(), e);
        }

        return atomicActions;
    }

    /**
     * Returns the list of ComplexActions associated to a behaviour. If none, returns an empty list.
     * @param id
     * @return the list of ComplexActions
     * @throws DAOException
     */
    public ArrayList<ComplexAction> getComplexActions(Behaviour behaviour) throws DAOException {
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
            prepStat.setInt(1, behaviour.getId());
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
            throw new DAOException("DAOException : BehaviourDAO getComplexActions(" + behaviour.getId() + "):" + e.getMessage(), e);
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



    // ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        BehaviourDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getBehaviourDAO();
        EnvironmentVariableDAO evDAO = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getEnvironmentVariableDAO();

        Behaviour b = new Behaviour();
        b.setActivated(true);
        b.setDescription("descri");
        b.setName("test Name");
        
        ArrayList<AtomicAction> aas = new ArrayList<AtomicAction>();
        AtomicAction aa = new AtomicAction();
        aa.setExecutable("exec ok");
        aas.add(aa);
        
        b.setAtomicActions(aas);
        
        Expression e = new Expression();
        
        Block bl = new Block();
        bl.setOperator(">");
        DiscreteValue value = new DiscreteValue();
        value.setCurrentValue("TRUE");
        bl.setValue(value);
        
        Block bl2 = new Block();
        bl2.setOperator("<");
        DiscreteValue value2 = new DiscreteValue();
        value2.setCurrentValue("TRUE");
        bl2.setValue(value2);
        
        bl.setEnvironmentVariable(evDAO.getById(1));
        bl2.setEnvironmentVariable(evDAO.getById(2));
        
        ArrayList<Evaluable> eval = new ArrayList<Evaluable>();
        eval.add(bl);
        eval.add(bl2);
        e.setEvaluables(eval);
        
        ArrayList<String> operators = new ArrayList<String>();
        operators.add("&&");
        e.setOperators(operators);

        b.setExpression(e);
        
        System.out.println(test.create(b));
        //System.out.println(test.getAll());
    }
}

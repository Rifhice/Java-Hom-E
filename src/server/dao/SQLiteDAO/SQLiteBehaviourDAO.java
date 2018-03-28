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
import server.dao.abstractDAO.UserDAO;
import server.dao.abstractDAO.BehaviourDAO;
import server.factories.AbstractDAOFactory;
import server.models.AtomicAction;
import server.models.Behaviour;
import server.models.ComplexAction;
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
	        Behaviour Behaviour = null;
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

	@Override
	public ArrayList<Behaviour> getAll() throws DAOException {
        ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
        String sql = "SELECT B.id AS id, B.name AS name, B.description AS description, B.is_activated AS isActivated, "
                + "E.id AS Eid, E.operators AS Eoperators, Ca.name AS Caname, "
                + "Ca.id AS Caid, Ac.executable AS Acexecutable, Ac.name AS Acname, Ac.id AS Acid, "
                + "Bl.id AS Blid, Bl.operator AS Bloperator, "
                + "VV.id AS VVid, EV.name AS EVname, EV.description as EVdescription, EV.unit as EVunit, "
                + "EV.id AS EVid, CVV.value_min AS CVVvalue_min, CVV.value_max AS CVVvalue_max, CVV.current_value AS CVVcurrent_value, CVV.precision AS CVVprecision, "
                + "V.id AS Vid, CV.value_min AS CVvalue_min, CV.value_max AS CVvalue_max, CV.current_value AS CVcurrent_value, CV.precision AS CVprecision, "
                + "DV.current_value AS DVcurrent_value, DV.possible_values AS DVpossible_values, "
                + "DVV.current_value AS DVVcurrent_value, DVV.possible_values AS DVVpossible_values "
                + "FROM Behaviours AS B "
                + "JOIN Expressions AS E ON E.id = B.fk_expression_id "
                + "JOIN IsPartOf AS IPO ON IPO.fk_expression_id = E.id "
                + "JOIN Blocks AS Bl ON Bl.id = IPO.fk_block_id " 
                + "JOIN EnvironmentVariables AS EV ON EV.id = Bl.fk_environmentVariable_id "
                + "JOIN VValues AS VV ON VV.id = EV.fk_vvalue_id "
                + "JOIN VValues AS V ON V.id = Bl.fk_vvalue_id "
                + "LEFT JOIN DiscreteVValues AS DVV ON DVV.fk_vvalue_id = VV.id "
                + "LEFT JOIN ContinuousVValues AS CVV ON CVV.fk_vvalue_id = VV.id "
                + "LEFT JOIN DiscreteVValues AS DV ON DV.fk_vvalue_id = V.id "
                + "LEFT JOIN ContinuousVValues AS CV ON CV.fk_vvalue_id = V.id "
                + "JOIN Launches AS L ON L.fk_behaviour_id = B.id "
                + "JOIN AtomicActions AS Ac ON Ac.id = L.fk_atomicAction_id "
                + "LEFT JOIN Executes AS Ex ON Ex.fk_behaviour_id = B.id "
                + "LEFT JOIN ComplexActions AS Ca ON Ca.id = Ex.fk_complexAction_id "
                + ";";
        try {
            PreparedStatement prepStat = this.connect.prepareStatement(sql);
            ResultSet rs = prepStat.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
           
            // Print rs
            
            //while (rs.next()) {
            //    for (int i = 1; i <= columnsNumber; i++) {
            //        if (i > 1) System.out.print(" | ");
            //        System.out.print(rs.getString(i));
            //    }
            //    System.out.println("");
            //}
            
            if(rs.next()) {
                // Know if the behaviour manipulated changed.
                int previousId = 0;
                ArrayList<AtomicAction> atomics = new ArrayList<AtomicAction>();
                ArrayList<ComplexAction> complexs = new ArrayList<ComplexAction>();
                ArrayList<Evaluable> blocks = new ArrayList<Evaluable>();

                Behaviour behaviour = new Behaviour();
                do {
                    if(previousId != rs.getInt("id")) {
                        // Not first behaviour : push the previous behaviour
                        if(previousId != 0) {
                            behaviour.setAtomicActions(atomics);
                            behaviour.setComplexActions(complexs);
                            behaviours.add(behaviour);   
                            
                            complexs= new ArrayList<ComplexAction>();
                            atomics = new ArrayList<AtomicAction>();
                            blocks = new ArrayList<Evaluable>();
                            behaviour = new Behaviour();  
                        }   

                        
                        behaviour.setId(rs.getInt("id"));
                        behaviour.setName(rs.getString("name"));
                        behaviour.setDescription(rs.getString("description"));
                        behaviour.setActivated(rs.getBoolean("isActivated"));

                        try {
        					JSONObject JSON = new JSONObject(rs.getString("Eoperators"));
        					JSONArray array = JSON.getJSONArray("operators");
        					ArrayList<String> arrayl = new ArrayList(array.toList());
        					Expression E = new Expression(rs.getInt("Eid"), arrayl);
        					E.setEvaluables(blocks);
        					behaviour.setExpression(E);
        				} catch (Exception e) {
        					
        				}
                        previousId = rs.getInt("id");                      
                    }

                    // Same behaviour as previous one, we add the next atomicAction
                    int atomicId = rs.getInt("Acid");
                    String atomicExecutable = rs.getString("Acexecutable");
                    String atomicName = rs.getString("Acname");
                    AtomicAction atomic = new AtomicAction(atomicId, atomicName, atomicExecutable);
                    atomics.add(atomic);
                    
                    int complexId = rs.getInt("Caid");
                    String complexName = rs.getString("Caname");
                    ComplexAction complex = new ComplexAction(complexId, complexName);
                    complexs.add(complex);
                    
                    
                    EnvironmentVariable ev = new EnvironmentVariable(rs.getInt("EVid"), rs.getString("EVname"), rs.getString("EVdescription"), rs.getString("EVunit"));
                    
                    
                    int value1id = rs.getInt("VVid");
                    int value2id = rs.getInt("Vid"); 
                    
                    Value vv; 
                   
                    if (rs.getString("DVVpossible_values") != null) {
                    	DiscreteValue DVV = new DiscreteValue();
                    	try {
        					JSONObject JSON2 = new JSONObject(rs.getString("DVVpossible_values"));
        					JSONArray array = JSON2.getJSONArray("possible_values");
        					ArrayList<String> arrayl = new ArrayList(array.toList());
        					DVV.setPossibleValues(arrayl);

        				} catch (Exception e) {
        					
        				}
                    	DVV.setCurrentValue(rs.getString("DVcurrent_value"));
                    	DVV.setId(value1id);
    					vv = DVV; 
    					
                    } else {
                    	ContinuousValue CVV = new ContinuousValue(value1id, rs.getInt("CVVvalue_min"), rs.getInt("CVVvalue_max"), rs.getInt("CVVprecision"), rs.getInt("CVVcurrent_value"));
    					vv = CVV; 
    					
                    }
                    
                    ev.setValue(vv);
                    
                    int blockId = rs.getInt("Blid");
                    String blockOperator = rs.getString("Bloperator");
                    Block block= new Block(blockId, ev, blockOperator);
                    
                    Value v; 
                    
                    if (rs.getString("DVpossible_values") != null) {
                    	DiscreteValue DV = new DiscreteValue();
                    	try {
        					JSONObject JSON = new JSONObject(rs.getString("DVpossible_values"));
        					JSONArray array = JSON.getJSONArray("possible_values");
        					ArrayList<String> arrayl = new ArrayList(array.toList());
        					DV.setPossibleValues(arrayl);
        					
        					
        				} catch (Exception e) {
        					
        				}
                    	DV.setCurrentValue(rs.getString("DVcurrent_value"));
                    	DV.setId(value2id);
                    	v = DV; 
                    	
                    	
                    } else {
                    	ContinuousValue CV = new ContinuousValue(value2id, rs.getInt("CVvalue_min"), rs.getInt("CVvalue_max"), rs.getInt("CVprecision"), rs.getInt("CVcurrent_value"));
                    	v = CV;
                    }
                    
                    block.setValue(v);
                    blocks.add(block);
                            
                
                } while (rs.next());
                // Push the last behaviour
                behaviour.setAtomicActions(atomics);
                behaviour.setComplexActions(complexs);
                behaviours.add(behaviour); 
                
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : BehaviourDAO getAll() :" + e.getMessage(), e);
        }
        return behaviours;
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
            	System.out.println("uubb" + rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new DAOException("DAOException : Behaviours getById(" + id + ") :" + e.getMessage(), e);
        }
        return behaviour;
	}

	// ============== //
    // ==== MAIN ==== //
    // ============== // 
    public static void main (String args[]) {
        BehaviourDAO test = AbstractDAOFactory.getFactory(AbstractDAOFactory.SQLITE_DAO_FACTORY).getBehaviourDAO();
        System.out.println(test.getAll());
        //test.getAll();
    }
}

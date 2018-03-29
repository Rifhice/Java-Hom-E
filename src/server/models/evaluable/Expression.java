package server.models.evaluable;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import server.factories.AbstractDAOFactory;
import server.managers.SystemManager;
import server.models.Sensor;
import server.models.environmentVariable.ContinuousValue;
import server.models.environmentVariable.DiscreteValue;
import server.models.environmentVariable.EnvironmentVariable;

/**
 * An expression is composed by others expressions and / or blocks (i.e. evaluables).  
 * When an expression is evaluated, we construct as this : 
 * evaluables[0] operators[0] evaluables[1] operators[1] evaluables[2] and so on... 
 * 
 * Ex : evaluables = [3,5,6]
 *      operators  = [>, !=]
 * -->  3 > 5 != 6
 * 
 * There is always one more evaluable than operators.
 * 
 * @author Clm-Roig
 */
public class Expression implements Evaluable {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private ArrayList<Evaluable> evaluables;
    private ArrayList<String> operators;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Expression() {}

    public Expression(ArrayList<Evaluable> evaluables, ArrayList<String> operators) {
        this.evaluables = evaluables;
        this.operators = operators;
    }

    public Expression(int id, ArrayList<Evaluable> evaluables, ArrayList<String> operators) {
        this.id = id;
        this.evaluables = evaluables;
        this.operators = operators;
    }
    
    public Expression(int id, ArrayList<String> operators) {
        this.id = id;
        this.operators = operators;
    }
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Evaluable> getEvaluables() {
        return evaluables;
    }

    public void setEvaluables(ArrayList<Evaluable> evaluables) {
        this.evaluables = evaluables;
    }

    public ArrayList<String> getOperators() {
        return operators;
    }

    public void setOperators(ArrayList<String> operators) {
        this.operators = operators;
    }

    // ==========================================

    public boolean evaluate() {
        int operatorCpt = 0;
        boolean leftValue = evaluables.get(0).evaluate();
        if(evaluables.size() > 1) {
            for (int i = 1; i < evaluables.size(); i++) {
                if(operators.get(operatorCpt).equals("&&")) {
                    leftValue = leftValue && evaluables.get(i).evaluate();
                }
                else {
                    leftValue = leftValue || evaluables.get(i).evaluate();
                }		
                operatorCpt++;
            }
        }
        return leftValue;
    }

    public String toString() {
        String res = "EXP #"+id;
        res += "(";
        int operatorCpt = 0;
        if(evaluables != null) {
        	for (int i = 0; i < evaluables.size(); i++) {
                res += "\n[" + evaluables.get(i).toString() + "]\n";
                if(operatorCpt < operators.size()) {
                    res += " " + operators.get(operatorCpt) + " ";
                }
                operatorCpt++;
            }
        }        
        return res + ")";
    }

    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
        result.put("id", id);
        result.put("type", "expression");
        for (int i = 0; i < evaluables.size(); i++) {
			result.append("evaluable",evaluables.get(i).toJson());
		}
        for (int i = 0; i < operators.size(); i++) {
			result.append("operators", operators.get(i));
		}
        return result;
    }
    
    public ArrayList<EnvironmentVariable> getVariables(){
        ArrayList<EnvironmentVariable> result = new ArrayList<EnvironmentVariable>();
        for (int i = 0; i < evaluables.size(); i++) {
            if(evaluables.get(i) instanceof Expression) {
                result.addAll(((Expression) (evaluables.get(i))).getVariables());
            }
            else if(evaluables.get(i) instanceof Block) {
                result.add(((Block) evaluables.get(i)).getEnvironmentVariable());
            }
        }
        return result;
    }

    // TODO : To move in a Manager
    public static Expression createExpressionFromJson(JSONObject json) {
        ArrayList<Evaluable> evaluables = new ArrayList<Evaluable>();
        ArrayList<String> operators = new ArrayList<String>();
        JSONArray arr = json.getJSONArray("evaluables");
        for (int i = 0; i < arr.length(); i++){
            JSONObject object = arr.getJSONObject(i);
            if(object.getString("type").equals("block")) {
                evaluables.add(createBlockFromJson(object));
            }
            else {
                evaluables.add(createExpressionFromJson(object));
            }
        }
        arr = json.getJSONArray("operators");
        for (int i = 0; i < arr.length(); i++) {
            operators.add((String) arr.get(i));
        }
        return new Expression(evaluables, operators);
    }

	private static Block createBlockFromJson(JSONObject json) {
	    EnvironmentVariable variable = null;
	    Object value = null;
	    String operator = null;
	    
	    ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	    for (int i = 0; i < sensors.size(); i++) {
			if(sensors.get(i).getEnvironmentVariables().getId() == json.getInt("variable")) {
				variable = sensors.get(i).getEnvironmentVariables();
			}
		}
	    if(variable == null) {
	        variable = AbstractDAOFactory.getFactory(SystemManager.db).getEnvironmentVariableDAO().getById(json.getInt("variable"));
	    }
		
		if(variable != null) {
		   operator = json.getString("operator");
		   try {
			   	value = json.getDouble("value");
			   	return new Block(variable, new ContinuousValue((Double)value), operator);
			} catch (Exception e) {
				value = json.getString("value");
				return new Block(variable, new DiscreteValue((String)value), operator);
			}
		}
		else {
			System.out.println("ERROR VARIABLE NOT FOUND !");
			//SHOULD THROW EXCEPTION AS THE VARIABLE WASN'T FOUND
		}
		return null;
	}

}

package user.models.evaluable;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import user.models.environmentVariable.EnvironmentVariable;

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
    private ArrayList<Evaluable> evaluables;
    private String operator;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public Expression() {}

    public Expression(ArrayList<Evaluable> evaluables, String operator) {
        this.evaluables = evaluables;
        this.operator = operator;
    }
    // ================= //
    // ==== METHODS ==== //
    // ================= //

    public ArrayList<Evaluable> getEvaluables() {
        return evaluables;
    }

    public void setEvaluables(ArrayList<Evaluable> evaluables) {
        this.evaluables = evaluables;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperators(String operator) {
        this.operator = operator;
    }

    // ==========================================

    public String toString() {
        String res = "(";
        if(evaluables != null) {
            res += " [" + evaluables.get(0).toString() + "] ";
            res += " " + operator + " ";
            res += " [" + evaluables.get(1).toString() + "] ";
        }        
        return res + ")";
    }

    public JSONObject toJson() {
    	JSONObject result = new JSONObject();
        result.put("type", "expression");
        for (int i = 0; i < evaluables.size(); i++) {
			result.append("evaluables",evaluables.get(i).toJson());
		}
		result.append("operators", operator);
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
    /* TODO : to fix (value is a Value, not an object)
	private static Block createBlockFromJson(JSONObject json) {
	    EnvironmentVariable variable = null;
	    Object value = null;
	    String operator = null;
		ArrayList<EnvironmentVariable> variables = ExternalActorManager.getManager().getEnvironmentVariables();
		for (int j = 0; j < variables.size(); j++) {
			if(Integer.toString(variables.get(j).getId()).equals(json.getString("variable"))) {
				variable = variables.get(j);
			}
		}
		if(variable != null) {
		   operator = json.getString("operators");
		   if(json.getString("valueType").equals("Double")) {
			   value = json.getDouble("value");
		   }
		   else if(json.getString("valueType").equals("String")) {
			   value = json.getString("value");
		   }
		}
		else {
			System.out.println("ERROR VARIABLE NOT FOUND !");
			//SHOULD THROW EXCEPTION AS THE VARIABLE WASN'T FOUND
		}
		return new Block(variable, value, operator);
	}
     */

}

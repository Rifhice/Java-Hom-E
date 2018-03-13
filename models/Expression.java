package models;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import managers.ExternalActorManager;

public class Expression implements Evaluable{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private ArrayList<Evaluable> evaluable;
	private ArrayList<String> operators;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Expression(ArrayList<Evaluable> evaluable, ArrayList<String> operators) {
		this.evaluable = evaluable;
		this.operators = operators;
	}
	
    // ================= //
    // ==== METHODS ==== //
    // ================= //
	public boolean evaluate() {
		int operatorCpt = 0;
		boolean leftValue = evaluable.get(0).evaluate();
		if(evaluable.size() > 1) {
			for (int i = 1; i < evaluable.size(); i++) {
				if(operators.get(operatorCpt).equals("&&")) {
					leftValue = leftValue && evaluable.get(i).evaluate();
				}
				else {
					leftValue = leftValue || evaluable.get(i).evaluate();
				}		
				operatorCpt++;
			}
		}
		return leftValue;
	}
	
	public String toString() {
		String res = "(";
		int operatorCpt = 0;
		for (int i = 0; i < evaluable.size(); i++) {
			res += "[" + evaluable.get(i).toString() + "]";
			if(operatorCpt < operators.size()) {
				res += " " + operators.get(operatorCpt) + " ";
			}
			operatorCpt++;
		}
		return res + ")";
	}

	public ArrayList<EnvironmentVariable> getVariables(){
		ArrayList<EnvironmentVariable> result = new ArrayList<EnvironmentVariable>();
		for (int i = 0; i < evaluable.size(); i++) {
			if(evaluable.get(i) instanceof Expression) {
				result.addAll(((Expression) (evaluable.get(i))).getVariables());
			}
			else if(evaluable.get(i) instanceof Block) {
				result.add(((Block) evaluable.get(i)).getVariable());
			}
		}
		return result;
	}
	
	public static Expression createExpressionFromJson(JSONObject json) {
		ArrayList<Evaluable> evaluable = new ArrayList<Evaluable>();
		ArrayList<String> operators = new ArrayList<String>();
		JSONArray arr = json.getJSONArray("evaluable");
		for (int i = 0; i < arr.length(); i++){
			JSONObject object = arr.getJSONObject(i);
			if(object.getString("type").equals("block")) {
				evaluable.add(createBlockFromJson(object));
			}
			else {
				evaluable.add(createExpressionFromJson(object));
			}
		}
		arr = json.getJSONArray("operators");
		for (int i = 0; i < arr.length(); i++) {
			operators.add((String) arr.get(i));
		}
		return new Expression(evaluable, operators);
	}
	
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
	
}

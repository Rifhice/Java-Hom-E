package models;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

public class Behaviour implements Observer{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private Expression expression;
	private Command command;
	private ArrayList<EnvironmentVariable> variables;
	private boolean isActivated = true;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour(Expression expression, Command command) {
		this.expression = expression;
		this.command = command;
		this.variables = expression.getVariables();
		for (int i = 0; i < variables.size(); i++) {
			variables.get(i).addObserver(this);
		}
		update(null,null);
	}

    // ================= //
    // ==== METHODS ==== //
    // ================= //
	public void update(Observable arg0, Object arg1) {
		if(isActivated && expression.evaluate()) {
			System.out.println("It works !");
			//command.launch();
		}
	}
	
	public void activate() {
		isActivated = true;
	}
	
	public void deactivate() {
		isActivated = false;
	}
	
	public boolean isActivated() {
		return isActivated;
	}

	public String toString() {
		return expression.toString() + " => " ;//+ command.toString();
	}
	
	public static Behaviour createBehaviour(JSONObject json) {
	    Command command = null;
	    Expression expression = Expression.createExpressionFromJson(json.getJSONObject("expression"));
		return new Behaviour(expression, command);
	}
	
}

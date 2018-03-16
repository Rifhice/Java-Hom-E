package server.models;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

/**
 * A behaviour is a comportment of an actuator depending on environment variables.
 * @author Clm-Roig
 */
public class Behaviour implements Observer {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
	private Expression expression;
	private boolean isActivated;
	
	// Attributes from other tables
	// TODO : code Actions model 
	/*
	private ArrayList<AtomicAction> atomicActions;
	private ArrayList<ComplexAction> complexActions;
    */
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour() {}
	
	public Behaviour(Expression expression) {
	    this.expression = expression;
	}
	
	public Behaviour(Expression expression, boolean isActivated) {
		this.expression = expression;
		this.isActivated = isActivated;
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

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
    
    // ==================================
    
    public void update(Observable arg0, Object arg1) {
        if(isActivated && expression.evaluate()) {
            System.out.println("It works !");
            
            // TODO : execute actions
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
	
	// TODO : to move in a Manager
	public static Behaviour createBehaviour(JSONObject json) {
	    Expression expression = Expression.createExpressionFromJson(json.getJSONObject("expression"));
		return new Behaviour(expression);
	}
	
}

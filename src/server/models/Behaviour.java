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
	private ArrayList<AtomicAction> atomicActions;
    private ArrayList<ComplexAction> complexActions;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour() {}
	
	public Behaviour(Expression expression) {
        this.expression = expression;
    }
	
	public Behaviour(int id, Expression expression) {
	    this.id = id;
	    this.expression = expression;
	}
	
	public Behaviour(int id, Expression expression, boolean isActivated) {
	    this.id = id;
		this.expression = expression;
		this.isActivated = isActivated;
	}
	
	public Behaviour(int id, Expression expression, boolean isActivated, ArrayList<AtomicAction> atomicActions, ArrayList<ComplexAction> complexActions) {
        this.id = id;
        this.expression = expression;
        this.isActivated = isActivated;
        this.atomicActions = atomicActions;
        this.complexActions = complexActions;
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
    
    public boolean getActivated() {
        return this.isActivated;
    }
    
    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
    
    public ArrayList<AtomicAction> getAtomicActions() {
        return atomicActions;
    }

    public void setAtomicActions(ArrayList<AtomicAction> atomicActions) {
        this.atomicActions = atomicActions;
    }

    public ArrayList<ComplexAction> getComplexActions() {
        return complexActions;
    }

    public void setComplexActions(ArrayList<ComplexAction> complexActions) {
        this.complexActions = complexActions;
    }

    // ==================================
    
    public void update(Observable arg0, Object arg1) {
        if(isActivated && expression.evaluate()) {
            System.out.println("It works !");
            
            for (AtomicAction atomicAction : atomicActions) {
                atomicAction.execute();
            }
            for (ComplexAction complexAction : complexActions) {
                complexAction.execute();
            }
        }
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

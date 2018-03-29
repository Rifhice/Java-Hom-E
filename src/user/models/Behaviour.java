package user.models;

import java.util.ArrayList;

import user.models.AtomicAction;
import user.models.ComplexAction;
import user.models.evaluable.Expression;

public class Behaviour {
	// ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
	private String name;
	private String description;
	private boolean isActivated;
	
    // Attributes from others tables
	private Expression expression;
	private ArrayList<AtomicAction> atomicActions;
    private ArrayList<ComplexAction> complexActions;
    
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Behaviour(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
	
	public Behaviour(int id, String name, Boolean isActivated) {
        this.id = id;
        this.name = name;
        this.isActivated = isActivated;
    }
	
	public Behaviour(int id, String name, String description, Boolean isActivated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActivated = isActivated;
    }
	
	public Behaviour(int id, String name, String description, Boolean isActivated, Expression expression, ArrayList<AtomicAction> atomicActions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActivated = isActivated;
        this.expression = expression;
        this.atomicActions = atomicActions;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
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

}

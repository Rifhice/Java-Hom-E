package server.models;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;

import server.models.evaluable.Expression;

/**
 * A behaviour is a comportment of an actuator depending on environment variables.
 * @author Clm-Roig
 */
public class Behaviour implements Observer {
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private int id;
    private String name;
    private String description;
	private boolean isActivated;
	
	// Attributes from other tables
	private Expression expression;
	private ArrayList<AtomicAction> atomicActions;
    private ArrayList<ComplexAction> complexActions;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public Behaviour() {}
	
	public Behaviour(Expression expression) {
        this.expression = expression;
        this.isActivated = true;
    }
	
	public Behaviour(int id, Expression expression) {
	    this.id = id;
	    this.expression = expression;
	}
	
	public Behaviour(int id, String name, Expression expression) {
        this.id = id;
        this.name = name;
        this.expression = expression;
    }
	
	public Behaviour(int id, Expression expression, boolean isActivated) {
	    this.id = id;
		this.expression = expression;
		this.isActivated = isActivated;
	}
	
	public Behaviour(int id, String name, Expression expression, boolean isActivated, ArrayList<AtomicAction> atomicActions, ArrayList<ComplexAction> complexActions) {
        this.id = id;
        this.name = name;
        this.expression = expression;
        this.isActivated = isActivated;
        this.atomicActions = atomicActions;
        this.complexActions = complexActions;
    }
	
	public Behaviour(int id, String name, String description, Expression expression, boolean isActivated, ArrayList<AtomicAction> atomicActions, ArrayList<ComplexAction> complexActions) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }
    
    public boolean isActivated() {
        return isActivated;
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
		String res = "BEHAVIOUR #"+id+" "+name;
		if(isActivated) { res += " Activated"; }
        else { res+= " Deactivated"; }
		res += "\n"+expression;
		
		res += "\nAtomicActions:";
        if(atomicActions != null) {
            for (AtomicAction atomic : atomicActions) {
                res += "\n"+atomic;
            }
        }
        res += "\nComplexActions:";
        if(complexActions != null) {
            for (ComplexAction complex : complexActions) {
                res += "\n"+complex;
            }
        }
		return res;
	}
	
	// TODO : to move in a Manager
	public static Behaviour createBehaviour(JSONObject json) {
	    Expression expression = Expression.createExpressionFromJson(json.getJSONObject("expression"));
		return new Behaviour(expression);
	}

	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put("id", id);
		result.put("name", name);
		if(atomicActions != null) {
		    for (int i = 0; i < atomicActions.size(); i++) {
				result.append("atomicAction",atomicActions.get(i).toJson());
			}
		}
		if(complexActions != null) {
		    for (int i = 0; i < complexActions.size(); i++) {
				result.append("complexActions",complexActions.get(i).toJson());
			}
		}
		return result;
	}
	
}

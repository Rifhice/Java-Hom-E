

import java.util.ArrayList;

import org.json.JSONObject;

public class BehaviourManager {

	private static BehaviourManager manager = null;
	private ArrayList<Behaviour> behaviours;
	
	private BehaviourManager() {
		behaviours = new ArrayList<Behaviour>();
	}
	
	public static BehaviourManager getManager() {
		if(manager == null) 
			manager = new BehaviourManager();
		return manager;
	}
	
	public ArrayList<Behaviour> getAllBehaviours(){
		return behaviours;	
	}
	
	public void createBehaviour(JSONObject json) {
		behaviours.add(Behaviour.createBehaviour(json));
		System.out.println(behaviours.get(behaviours.size()-1));
	}
	
	public void deleteBehaviour(Behaviour behaviour) {
		behaviours.remove(behaviour);
	}
	
	public void activateBehaviour(Behaviour behaviour) {
		behaviour.activate();
	}
	
	public void deactivateBehaviour(Behaviour behaviour) {
		behaviour.deactivate();
	}
}

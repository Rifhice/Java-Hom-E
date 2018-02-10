package ExpressionInterpreter;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Behaviour implements Observer{

	private Expression expression;
	private Command command;
	private ArrayList<EnvironmentVariable> variables;
	
	public Behaviour(Expression expression, Command command) {
		this.expression = expression;
		this.command = command;
		this.variables = expression.getVariables();
		for (int i = 0; i < variables.size(); i++) {
			variables.get(i).addObserver(this);
		}
		update(null,null);
	}


	public void update(Observable arg0, Object arg1) {
		if(expression.evaluate()) {
			System.out.println("It works !");
			//command.launch();
		}
	}
	
}

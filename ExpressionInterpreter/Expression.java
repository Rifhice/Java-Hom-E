package ExpressionInterpreter;
import java.util.ArrayList;
import java.util.Arrays;

public class Expression implements Evaluable{
	
	private ArrayList<Evaluable> evaluable;
	private ArrayList<String> operators;
	
	public Expression(ArrayList<Evaluable> evaluable, ArrayList<String> operators) {
		this.evaluable = evaluable;
		this.operators = operators;
	}
	
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

	public static void main(String[] args) {
		ContinuousEnvironmentVariable time = new ContinuousEnvironmentVariable("Le temps", "C'est le super temps", "minutes", 0, 60, 1, 55);
		DiscreteEnvironmentVariable temperature = new DiscreteEnvironmentVariable("La temperature", "Savoir si il fait chaud ou pas", "", new ArrayList<String>(Arrays.asList("Froid","Normal","Chaud")), "Chaud");
		
		Block b1 = new Block(time,30,"==");
		Block b2 = new Block(temperature,"Froid","==");
		
		ArrayList<Evaluable> evaluable = new ArrayList<Evaluable>();		
		ArrayList<String> operators = new ArrayList<String>();
		
		evaluable.add(b1);
		operators.add("&&");
		evaluable.add(b2);
		
		Expression expression = new Expression(evaluable,operators);
		System.out.println(expression + " => " + expression.evaluate());
		
		
		ArrayList<Evaluable> evaluable2 = new ArrayList<Evaluable>();		
		ArrayList<String> operators2 = new ArrayList<String>();
		
		Block b3 = new Block(time,55,"==");
		
		evaluable2.add(expression);
		operators2.add("||");
		evaluable2.add(b3);
		
		Expression expression2 = new Expression(evaluable2,operators2);
		System.out.println(expression2 + " => " + expression2.evaluate());
		
	}
	
}

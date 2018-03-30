package user.tools;

import java.util.ArrayList;

public class ArithmeticOperator {
	
	public static enum OPERATORS { 
		EQUAL,
		DIFF,
		GT,
		GTE,
		LT,
		LTE;
		
		public String toString() {
			String operator = "";
			switch(this) {
			case EQUAL:
				operator = "==";
				break;
			case DIFF:
				operator = "!=";
				break;
			case GT:
				operator = ">";
				break;
			case GTE:
				operator = ">=";
				break;
			case LT:
				operator = "<";
				break;
			case LTE:
				operator = "<=";
				break;
			}
			return operator;
		}
	}
	
	public static ArrayList<String> getDiscreteOperators() {
		ArrayList<String> operators = new ArrayList<String>();
		operators.add(ArithmeticOperator.OPERATORS.EQUAL.toString());
		operators.add(ArithmeticOperator.OPERATORS.DIFF.toString());
		return operators;
	}
	
	public static  ArrayList<String> getContinuousOperators() {
		ArrayList<String> operators = new ArrayList<String>();
		for(int i = 0; i < ArithmeticOperator.OPERATORS.values().length; i++) {
			operators.add(ArithmeticOperator.OPERATORS.values()[i].toString());
		}
		return operators;
	}
}

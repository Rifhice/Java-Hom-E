package user.tools;

public enum BOOLEANOPERATOR {
	AND,
	OR;
	
	public String toString() {
		String operator = "";
		switch(this) {
		case AND:
			operator = "||";
			break;
		case OR:
			operator = "&&";
			break;
		}
		return operator;
	}
}

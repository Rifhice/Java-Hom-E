package user.tools;

public enum ARITHMETICOPERATOR {
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

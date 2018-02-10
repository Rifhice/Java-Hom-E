package ExpressionInterpreter;
import java.util.ArrayList;

public class DiscreteEnvironmentVariable extends EnvironmentVariable {

	private String name;
	private String unity;
	private ArrayList<String> values;
	private String currentValue;
	
	public DiscreteEnvironmentVariable(String name, String description, String unity, ArrayList<String> values, String currentValue) {
		super(name,description,unity);
		this.values = values;
		this.currentValue = currentValue;
	}
	
	public boolean isEqual(Object value) {
		if(value instanceof String) {
			return ((String)value).equals(currentValue);
		}
		return false;
	}

	 public String toString() {
		 return super.toString() + ":" + currentValue;
	 }
	
	public boolean isNotEqual(Object value) {
		if(value instanceof String) {
			return !((String)value).equals(currentValue);
		}
		return false;
	}

	public boolean isSuperior(Object value) {
		return false;
	}

	public boolean isInferior(Object value) {
		return false;
	}

	public boolean isSuperiorOrEqual(Object value) {
		return false;
	}
	public boolean isInferiorOrEqual(Object value) {
		return false;
	}

	public void setCurrentValue(Object value) {
		if(value instanceof String) {
			currentValue = (String) value;
			setChanged();
			notifyObservers();
		}
	}

	public Object getCurrentValue() {
		return currentValue;
	}

}

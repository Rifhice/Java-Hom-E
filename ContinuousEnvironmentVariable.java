
import java.util.ArrayList;

public class ContinuousEnvironmentVariable extends EnvironmentVariable{
	private double valueMin;
	private double valueMax;
	private double currentValue;
	private double precision;
	
	public ContinuousEnvironmentVariable(String name, String description, String unity, double valueMin,double valueMax, double precision, double currentValue) {
		super(name, description, unity);
		this.valueMax = valueMax;
		this.valueMin = valueMin;
		this.currentValue = currentValue;
	}

	public boolean isEqual(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() == currentValue;
		}
		return false;
	}

	 public String toString() {
		 return super.toString() + ":" + currentValue;
	 }
	
	public boolean isNotEqual(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() != currentValue;
		}
		return false;
	}

	public boolean isSuperior(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() > currentValue;
		}
		return false;
	}

	public boolean isInferior(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() < currentValue;
		}
		return false;
	}

	public boolean isSuperiorOrEqual(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() >= currentValue;
		}
		return false;
	}

	public boolean isInferiorOrEqual(Object value) {
		if (value instanceof Number) {
		    return ((Number) value).doubleValue() <= currentValue;
		}
		return false;
	}
	
	public void setCurrentValue(Object value) {
		if(value instanceof Number) {
			currentValue =  ((Number) value).doubleValue();
			setChanged();
			notifyObservers();
		}
	}
	
	public Object getCurrentValue() {
		return currentValue;
	}

}

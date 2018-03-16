package server.models;

/**
 * A continous environment variable is a variable which current value is a decimal number.
 * @author Clm-Roig
 */
public class ContinuousEnvironmentVariable extends EnvironmentVariable{
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private double valueMin;
    private double valueMax;
    private double currentValue;
    private double precision;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    public ContinuousEnvironmentVariable(String name, String description, String unity, double valueMin,double valueMax, double precision, double currentValue) {
        super(name, description, unity);
        this.valueMax = valueMax;
        this.valueMin = valueMin;
        this.currentValue = currentValue;
    }

    // ================= //
    // ==== METHODS ==== //
    // ================= //
    public double getValueMin() {
        return valueMin;
    }

    public void setValueMin(double valueMin) {
        this.valueMin = valueMin;
    }

    public double getValueMax() {
        return valueMax;
    }

    public void setValueMax(double valueMax) {
        this.valueMax = valueMax;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
        setChanged();
        notifyObservers();
    }


    // ==================================

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
}

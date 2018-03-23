package user.ui.componentJavaFX;
import javafx.scene.control.Slider;
public class MySlider extends Slider{

	public MySlider(double min,double max, double precision) {
		setMin(min);
		setMax(max);
		setValue(min + (max - min / 2) );
		setShowTickLabels(true);
		setShowTickMarks(true);
		setMajorTickUnit(max - min / 5);
		setBlockIncrement(precision);
	}
	
}

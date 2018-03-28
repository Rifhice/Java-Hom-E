package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;

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
	
	public MySlider(Rectangle2D.Float bounds, double min,double max, double precision) {
		this.setPrefHeight(bounds.height);
		this.setPrefWidth(bounds.getWidth());
		this.setLayoutX(bounds.getX());
		this.setLayoutY(bounds.getY());
		setMin(min);
		setMax(max);
		setValue(min + (max - min / 2) );
		setShowTickLabels(true);
		setShowTickMarks(true);
		setMajorTickUnit(max - min / 5);
		setBlockIncrement(precision);
	}
	
}

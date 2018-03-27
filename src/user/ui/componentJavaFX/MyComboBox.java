package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javafx.scene.control.ComboBox;
public class MyComboBox extends ComboBox<String>{

	public MyComboBox(ArrayList<String> options) {
		getItems().addAll(
			    options
			);
	}
	
	public MyComboBox(Rectangle2D.Float bounds,ArrayList<String> options) {
		getItems().addAll(
			    options
			);
		setLayoutX(bounds.x);
		setLayoutY(bounds.y);
		setPrefWidth(bounds.width);
		setPrefHeight(bounds.height);
	}
	
}

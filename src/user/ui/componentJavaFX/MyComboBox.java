package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
public class MyComboBox<T> extends ComboBox<T>{
	
	public MyComboBox(ArrayList<T> elements) {
		super();
		this.getItems().addAll(elements);
	}
	
	public MyComboBox(Rectangle2D.Float bounds, ArrayList<T> options) {
		if(options != null) {
			this.getItems().addAll(options);
		}
		setLayoutX(bounds.x);
		setLayoutY(bounds.y);
		setPrefWidth(bounds.width);
		setPrefHeight(bounds.height);
	}
	
}

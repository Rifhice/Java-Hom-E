package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MyButtonFX extends Button{

	public MyButtonFX(String label,Rectangle2D.Float bounds,int widht, int height, EventHandler<ActionEvent> event) {
		setText(label);
		setLayoutX((int)(bounds.getX() * widht));
		setLayoutY((int)(bounds.getY() * height));
		setWidth((int)(bounds.getWidth() * widht));
		setHeight((int)(bounds.getHeight() * height));
		setOnAction(event);
	}
	
	public MyButtonFX(String label,double width, double height,EventHandler<ActionEvent> event) {
		setText(label);
		setOnAction(event);
		setPrefWidth(width);
		setPrefHeight(height);
	}
}

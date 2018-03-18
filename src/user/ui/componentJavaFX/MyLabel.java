package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.scene.control.Label;

public class MyLabel extends Label{

	public MyLabel(String text,Rectangle2D.Float bounds) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
	}
	
}

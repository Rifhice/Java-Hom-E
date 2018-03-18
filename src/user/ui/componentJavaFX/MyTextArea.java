package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;

import javafx.scene.control.TextArea;

public class MyTextArea extends TextArea{
	
	public MyTextArea(String text,Rectangle2D.Float bounds) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
	}
	
	public MyTextArea lockText() {
		setEditable(false);
		return this;
	}
	
}

package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

public class MyTextArea extends TextArea{
	
	public MyTextArea(String text,Rectangle2D.Float bounds) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	public MyTextArea(String text,Rectangle2D.Float bounds,float fontMultiplier) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
	}
	
	public MyTextArea lockText() {
		setEditable(false);
		return this;
	}
	
}

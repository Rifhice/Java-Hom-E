package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

public class MyLabel extends Label{

	public MyLabel(String text,Rectangle2D.Float bounds) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	public MyLabel(String text) {
		setText(text);
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	public MyLabel(String text,Rectangle2D.Float bounds,float fontMultiplier) {
		setText(text);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
	}
	 
	/** 
	 * Center-X the label in his father 
	 * @param fatherWidth, the width of the father
	 * @return the label centered
	 */
	public MyLabel centerX(double fatherWidth) {
        setLayoutX(fatherWidth * 0.5f - getPrefWidth() / 2.0f); 
        return this;
    }
	
}

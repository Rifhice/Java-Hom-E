package user.ui.componentJavaFX;

import javafx.scene.control.*;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

import java.awt.geom.Rectangle2D;

import javafx.event.Event;
import javafx.event.EventHandler;

public class MyTextFieldFX extends TextField{
	
	public MyTextFieldFX(String label,Rectangle2D.Float bounds) {
		new TextField();
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setWidth((int)(bounds.getWidth()));
		setHeight((int)(bounds.getHeight()));
		this.setPromptText(label);
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setText("");
				setStyle("-fx-background-color: white;");
			}
		});
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}

	public MyTextFieldFX(String label,Rectangle2D.Float bounds,float fontMultiplier) {
		new TextField();
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setWidth((int)(bounds.getWidth()));
		setHeight((int)(bounds.getHeight()));
		this.setPromptText(label);
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setText("");
				setStyle("-fx-background-color: white;");
			}
		});
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
	}
	
}

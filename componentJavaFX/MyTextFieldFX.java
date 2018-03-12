package componentJavaFX;

import javafx.scene.control.*;

import java.awt.geom.Rectangle2D;

import javafx.event.Event;
import javafx.event.EventHandler;

public class MyTextFieldFX extends TextField{
	
	public MyTextFieldFX(String label,Rectangle2D.Float bounds, int widht, int height) {
		new TextField();
		setLayoutX((int)(bounds.getX() * widht));
		setLayoutY((int)(bounds.getY() * height));
		setWidth((int)(bounds.getWidth() * widht));
		setHeight((int)(bounds.getHeight() * height));
		this.setPromptText(label);
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setText("");
				setStyle("-fx-background-color: white;");
			}
		});
	
	}

	
}

package componentJavaFX;

import javafx.scene.control.*;

import java.awt.geom.Rectangle2D;

import javafx.event.Event;
import javafx.event.EventHandler;

public class MyTextFieldFX extends TextField{

	boolean initial = true;
	
	public MyTextFieldFX(String label,Rectangle2D.Float bounds, int widht, int height) {
		new TextField();
		setText(label);
		setLayoutX((int)(bounds.getX() * widht));
		setLayoutY((int)(bounds.getY() * height));
		setWidth((int)(bounds.getWidth() * widht));
		setHeight((int)(bounds.getHeight() * height));
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setText("");
				initial = false;
			}
		});
	
	}
	
	public boolean isInitialMessage() {
		return initial;
	}
	
}

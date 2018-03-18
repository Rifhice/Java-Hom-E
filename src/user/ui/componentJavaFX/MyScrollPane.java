package user.ui.componentJavaFX;

import javafx.scene.control.ScrollPane;
import java.awt.geom.Rectangle2D;

public class MyScrollPane extends ScrollPane{

	public MyScrollPane(Rectangle2D.Float bounds) {
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
	}
	
}

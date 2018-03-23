package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;

import javafx.scene.layout.GridPane;
public class MyGridPane extends GridPane{

	
	public MyGridPane(Rectangle2D.Float bounds) {
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		this.setPrefWidth(bounds.getWidth());
		this.setPrefHeight(bounds.getHeight());
	}
	
	public MyGridPane(double width, double height) {
		setPrefWidth(width);
		setPrefHeight(height);
	}
}

package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;

import javafx.scene.layout.GridPane;
public class MyTilePane extends GridPane{

	
	public MyTilePane(Rectangle2D.Float bounds) {
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		this.setPrefWidth(bounds.getWidth());
		this.setPrefHeight(bounds.getHeight());
	}
	
	public MyTilePane(double width, double height) {
		setPrefWidth(width);
		setPrefHeight(height);
	}
}

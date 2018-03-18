package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.scene.layout.Pane;
import user.ClientFX;
import user.ui.scene.ContentScene;

public class MyPane extends Pane{
	
	public MyPane(Rectangle2D.Float bounds) {
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		this.setPrefWidth(bounds.getWidth());
		this.setPrefHeight(bounds.getHeight());
	}
	
}

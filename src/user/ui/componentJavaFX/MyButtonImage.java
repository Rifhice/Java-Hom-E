package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyButtonImage extends Button {

	public MyButtonImage(Image image, Rectangle2D.Float bounds, EventHandler<ActionEvent> event) {
		// TODO Auto-generated constructor stub
		double ratio = bounds.getWidth()/image.getWidth();
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(image.getWidth()*ratio);
		imageView.setFitHeight(image.getHeight()*ratio);
		setGraphic(imageView);
		setOnAction(event);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		
	}
	
	
	
	public MyButtonImage(Image image, Rectangle2D.Float bounds, EventHandler<ActionEvent> event, int id) {
		// TODO Auto-generated constructor stub
		double ratioWidth = bounds.getWidth()/image.getWidth();
		double ratioHeight = bounds.getHeight()/image.getHeight();
		setId(id +"");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(image.getWidth()*ratioWidth);
		imageView.setFitHeight(image.getHeight()*ratioHeight / 1.2);
		setGraphic(imageView);
		setOnAction(event);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setPrefHeight((int)bounds.getHeight());
		setPrefWidth((int)bounds.getWidth());
	}
}

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
		setMinWidth(image.getWidth()*ratio);
		setMinHeight(image.getHeight()*ratio);
		setMaxWidth(image.getWidth()*ratio);
		setMaxHeight(image.getHeight()*ratio);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		
	}
	
	
	
	public MyButtonImage(Image image, Rectangle2D.Float bounds, EventHandler<ActionEvent> event, int id) {
		// TODO Auto-generated constructor stub
		double ratioWidth = bounds.getWidth()/image.getWidth();
		double ratioHeight = bounds.getHeight()/image.getHeight();
		setId(id +"");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitWidth(image.getWidth()*ratioWidth);
		imageView.setFitHeight(image.getHeight()*ratioHeight);
		setGraphic(imageView);
		setOnAction(event);
		setLayoutX(bounds.getX());
		setLayoutY(bounds.getY());
		setMinWidth(image.getWidth()*ratioWidth);
		setMinHeight(image.getHeight()*ratioHeight);
		setMaxWidth(image.getWidth()*ratioWidth);
		setMaxHeight(image.getHeight()*ratioHeight);
		setPrefHeight((int)bounds.getHeight());
		setPrefWidth((int)bounds.getWidth());
	}
}

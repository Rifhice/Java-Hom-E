package user.ui.componentJavaFX;


import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.geom.Rectangle2D;



import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

public class MyButtonFX extends Button{

	public MyButtonFX(String label,Rectangle2D.Float bounds,EventHandler<ActionEvent> event) {
		setText(label);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setPrefWidth((int)(bounds.getWidth()));
		setPrefHeight((int)(bounds.getHeight()));
		setOnAction(event);
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	public MyButtonFX(String label,EventHandler<ActionEvent> event) {
		setText(label);
		setOnAction(event);
	}
	
	public MyButtonFX(String label,double width, double height,EventHandler<ActionEvent> event) {
		setText(label);
		setOnAction(event);
		setPrefWidth(width);
		setPrefHeight(height);
	}
	
	public MyButtonFX(String label,int id,double width, double height,EventHandler<ActionEvent> event) {
		setText(label);
		setId(id +"");
		setOnAction(event);
		setPrefWidth(width);
		setPrefHeight(height);
	}
	
	public MyButtonFX(Image image, Rectangle2D.Float bounds, EventHandler<ActionEvent> event) {
		// TODO Auto-generated constructor stub
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		setGraphic(imageView);
		setOnAction(event);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setPrefWidth(bounds.getWidth());
		setPrefHeight(bounds.getHeight());
	}

	public MyButtonFX centerX(double width) {
		setLayoutX(width * 0.5f - getPrefWidth() / 2);
		return this;
	}
	
	public MyButtonFX setFontMultiplier(float fontMultiplier) {
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
		return this;
	}
}

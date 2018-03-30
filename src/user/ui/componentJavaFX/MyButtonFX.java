package user.ui.componentJavaFX;


import java.awt.geom.Rectangle2D;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

public class MyButtonFX extends Button{

	private MyButtonFX() {
		this.setStyle(
				"-fx-background-color: " + GraphicalCharter.LIGHT_BLUE +";"
				+ "-fx-border-color: " + GraphicalCharter.DARK_GRAY + ";"
				+ "-fx-text-fill: " + GraphicalCharter.WHITE + ";"
				+ "-fx-font-size: " + 16
		);
		this.setOnMouseEntered(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				setStyle(
						"-fx-background-color: " + GraphicalCharter.DEEP_BLUE +";"
						+ "-fx-border-color: " + GraphicalCharter.DARK_GRAY + ";"
						+ "-fx-text-fill: " + GraphicalCharter.DEEP_BLUE + ";"
						+ "-fx-text-fill: " + GraphicalCharter.WHITE + ";"
						+ "-fx-font-size: " + 16
				);
			}
		});
		this.setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				// TODO Auto-generated method stub
				setStyle(
						"-fx-background-color: " + GraphicalCharter.LIGHT_BLUE +";"
						+ "-fx-border-color: " + GraphicalCharter.DARK_GRAY + ";"
						+ "-fx-text-fill: " + GraphicalCharter.WHITE + ";"
						+ "-fx-font-size: " + 16
				);
			}
		});
	}
	
	public MyButtonFX(String label,Rectangle2D.Float bounds,EventHandler<ActionEvent> event) {
		this();
		setText(label);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setPrefWidth((int)(bounds.getWidth()));
		setPrefHeight((int)(bounds.getHeight()));
		setOnAction(event);
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	public MyButtonFX(String label,EventHandler<ActionEvent> event) {
		this();
		setText(label);
		setOnAction(event);
	}
	
	public MyButtonFX(String label,double width, double height,EventHandler<ActionEvent> event) {
		this();
		setText(label);
		setOnAction(event);
		setPrefWidth(width);
		setPrefHeight(height);
	}
	
	public MyButtonFX(String label,int id,double width, double height,EventHandler<ActionEvent> event) {
		this();
		setText(label);
		setId(id +"");
		setOnAction(event);
		setPrefWidth(width);
		setPrefHeight(height);
	}
	
	public MyButtonFX(String label, int id, Rectangle2D.Float bounds, EventHandler<ActionEvent> event) { 
		this();
		setText(label);
		setId(id +"");
		setOnAction(event);
		setOnAction(event);
		setLayoutX((int)(bounds.getX()));
		setLayoutY((int)(bounds.getY()));
		setPrefWidth((int)bounds.getWidth());
		setPrefHeight((int)bounds.getHeight());
	}

	public MyButtonFX centerX(double width) {
		setLayoutX(width * 0.5f - getPrefWidth() / 2.0f);
		return this;
	}
	
	public MyButtonFX centerY(double height) {
        setLayoutY(height * 0.5f - getPrefHeight() / 2.0f);
        return this;
    }
	
	public MyButtonFX setFontMultiplier(float fontMultiplier) {
		setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
		return this;
	}
}

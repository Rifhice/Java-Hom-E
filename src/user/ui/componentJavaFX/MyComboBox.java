package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
public class MyComboBox<T> extends ComboBox<T>{
	
	public MyComboBox(ArrayList<T> elements) {
		super();
		this.getItems().addAll(elements);
		resetStyle();
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setStyle("-fx-background-color: white;");
			}
		});
	}
	
	public MyComboBox(Rectangle2D.Float bounds, ArrayList<T> options) {
		if(options != null) {
			this.getItems().addAll(options);
		}
		setLayoutX(bounds.x);
		setLayoutY(bounds.y);
		setPrefWidth(bounds.width);
		setPrefHeight(bounds.height);
		resetStyle();
		setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				setStyle("-fx-background-color: white;");
			}
		});
	}
	
	public void resetStyle() {
		setStyle("-fx-background-color: white");
	}

	public void setValues(ArrayList<T> operators) {
		this.getItems().clear();
		this.getItems().addAll(operators);
	}
	
}

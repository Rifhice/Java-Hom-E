package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import user.ClientFX;
import user.tools.GraphicalCharter;
import javafx.scene.control.Label;
import user.models.Actuator;
import user.models.ActuatorCategory;
import user.models.ContinuousValue;
import user.models.DiscreteValue;
import user.models.EnvironmentVariable;
import user.models.Sensor;
import user.models.SensorCategory;
import user.models.Value;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class ActuatorContent extends Content {

	private static ActuatorContent content = null;
	
	ArrayList<Actuator> actuators = new ArrayList<Actuator>();
	ArrayList<ActuatorCategory> categories = new ArrayList<ActuatorCategory>();
	
	private MyRectangle actuatorsListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
	private MyRectangle selectedActuatorBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
    private MyRectangle actuatorBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f); 
	private MyRectangle nomBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionBounds = new MyRectangle(0.1f, 0.24f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionBounds = new MyRectangle(0.1f, 0.245f, 0.8f, 0.2f); 


    private MyGridPane actuatorsListGrid;
    
    private MyLabel actuatorLabel;
    private MyLabel nomLabel;
    private MyLabel description;
    private MyLabel variableDescriptionLabel;
    
    private int currentActuatorIndex = -1;
    private final int NB_ACTUATOR_DISPLAYED = 3;
    
    
	private ActuatorContent() {
		actuatorsListGrid = new MyGridPane(actuatorsListBounds.computeBounds(width, height));
        MyScrollPane actuatorList = new MyScrollPane(actuatorsListBounds.computeBounds(width,height));
        actuatorList.setContent(actuatorsListGrid);
		
		
	    MyPane actuatorsListPane = new MyPane(actuatorsListBounds.computeBounds(width, height));
	    actuatorsListPane.setStyle("-fx-background-color: rgb(250, 250, 100)");
	    
	    MyPane selectedActuatorPane = new MyPane(selectedActuatorBounds.computeBounds(width, height));
	    selectedActuatorPane.setStyle("-fx-background-color: rgb(100,100,250)");
	    actuatorLabel = new MyLabel("Name",actuatorBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()),1.5f);
	    actuatorLabel.centerX(selectedActuatorPane.getPrefWidth());
	    actuatorLabel.setAlignment(Pos.CENTER);
	    actuatorLabel.setStyle("-fx-font-weight: bold;");
	    
	    MyPane latestActionsPane = new MyPane(latestActionsBounds.computeBounds(width, height));
	    latestActionsPane.setStyle("-fx-background-color: rgb(100,250,100)");

	    this.getChildren().add(actuatorsListPane);
	    this.getChildren().add(selectedActuatorPane);
	    this.getChildren().add(latestActionsPane);
	    actuatorsListPane.getChildren().add(new Label("Actuators List"));
	    selectedActuatorPane.getChildren().add(new Label("Actuator selected"));
	    latestActionsPane.getChildren().add(new Label("Latest Actions"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new ActuatorContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {

	}
}

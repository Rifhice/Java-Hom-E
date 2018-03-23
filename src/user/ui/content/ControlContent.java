package user.ui.content;

import java.io.IOException;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import user.ClientFX;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class ControlContent extends Content {

	private static ControlContent content = null;
	private MyGridPane topGridPane;
	private MyGridPane bottomGridPane;
	
	private MyRectangle bottomPaneBounds = new MyRectangle(0f,0.3f,1f,0.7f);
	private MyRectangle topPaneBounds = new MyRectangle(0f,0f,1f,0.3f);
	
	private MyRectangle composedLabelCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);
	private MyRectangle atomicLabelCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);

	private ControlContent() {
		topGridPane  = new MyGridPane(topPaneBounds.computeBounds(width, height));
		MyLabel label = new MyLabel("Composed Commands", composedLabelCommandBounds.computeBounds(topGridPane.getPrefWidth(), topGridPane.getPrefHeight()),2f);
		topGridPane.add(label, 0, 0);

        MyScrollPane composedList = new MyScrollPane(topPaneBounds.computeBounds(width,height));
        composedList.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));

        composedList.setContent(topGridPane);
        
        
        bottomGridPane  = new MyGridPane(bottomPaneBounds.computeBounds(width, height));
		MyLabel label2 = new MyLabel("Atomic Commands", atomicLabelCommandBounds.computeBounds(topGridPane.getPrefWidth(), topGridPane.getPrefHeight()),2f);
		bottomGridPane.add(label2, 0, 0);

        MyScrollPane atomicList = new MyScrollPane(bottomPaneBounds.computeBounds(width,height));
        atomicList.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));

        atomicList.setContent(bottomGridPane);
        
		this.getChildren().add(composedList);
		this.getChildren().add(atomicList);
		
		
	}

	public static Content getInstance() {
		if(content == null) {
			content = new ControlContent();
		}
		return content;
	}
	
    public void updateData() {
        JSONObject getSensor = new JSONObject();
        getSensor.put("recipient", "sensorCategories");
        getSensor.put("action", "getAll");

        try {
            ClientFX.client.sendToServer(getSensor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI() {
    	/*
        if(actuator.size() != 0) {
            if(currentActuatorCategoryIndex == -1) {
                currentActuatorCategoryIndex = 0;
            }
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    actuatorListGrid.getChildren().clear();
                    for (int i = 0; i < actuator.size(); i++) {
                        actuatorListGrid.add(new MyButtonFX(actuator.get(i).getName(),i,actuatorListGrid.getPrefWidth(),actuatorListGrid.getPrefHeight() / NB_OF_CAT_DISPLAYED, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
                                currentActuatorCategoryIndex = pressedButton;
                                currentActuatorCategoryName.setText(actuator.get(currentActuatorCategoryIndex).getName());
                                currentActuatorCategoryDescription.setText(actuator.get(currentActuatorCategoryIndex).getDescription());
                            }
                        }),0,i);
                    }
                    currentActuatorCategoryName.setText(actuator.get(currentActuatorCategoryIndex).getName());
                    currentActuatorCategoryDescription.setText(actuator.get(currentActuatorCategoryIndex).getDescription());
                }
            });
        }
        */
    }
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

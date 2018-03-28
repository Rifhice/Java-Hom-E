package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import user.ClientFX;
import user.models.Sensor;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class SensorContent extends Content {

	private static SensorContent content = null;
	
	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	
	private MyRectangle sensorsListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
	private MyRectangle selectedSensorBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
	private MyRectangle nomBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f); 
	private MyRectangle descriptionBounds = new MyRectangle(0.1f, 0.2f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionBounds = new MyRectangle(0.1f, 0.3f, 0.5f, 0.2f); 
    
    private MyGridPane sensorsListGrid;
    
    private MyLabel nomLabel;
    private MyLabel description;
    private MyLabel variableDescriptionLabel;
    
    private int currentSensorIndex = -1;
    private final int NB_SENSOR_DISPLAYED = 7;
    
	private SensorContent() {
	    sensorsListGrid = new MyGridPane(sensorsListBounds.computeBounds(width, height));
        MyScrollPane sensorList = new MyScrollPane(sensorsListBounds.computeBounds(width,height));
        sensorList.setContent(sensorsListGrid);
        this.getChildren().add(sensorList);
        
        
	    MyPane selectedSensorPane = new MyPane(selectedSensorBounds.computeBounds(width, height));
	    nomLabel = new MyLabel("nom",nomBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));
	    nomLabel.centerX(selectedSensorPane.getPrefWidth());
	    nomLabel.setAlignment(Pos.CENTER);
	    description = new MyLabel("Description",descriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));
	    variableDescriptionLabel = new MyLabel("descr",variableDescriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));	    
	    selectedSensorPane.getChildren().add(nomLabel);
	    selectedSensorPane.getChildren().add(description);
	    selectedSensorPane.getChildren().add(variableDescriptionLabel);
	    
	    MyPane variableValuePane = new MyPane(latestActionsBounds.computeBounds(width, height));

	    this.getChildren().add(selectedSensorPane);
	    this.getChildren().add(variableValuePane);
	    variableValuePane.getChildren().add(new Label("Sensors Actions"));
	    updateData();
	}
	
    public void updateData() {
        JSONObject getSensors = new JSONObject();
        getSensors.put("recipient", "sensor");
        getSensors.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getSensors.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateUI() {
    	if(sensors.size() != 0) {
            if(currentSensorIndex == -1) {
            	currentSensorIndex = 0;
            }
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	sensorsListGrid.getChildren().clear();
                    for (int i = 0; i < sensors.size(); i++) {
                    	sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
                                currentSensorIndex = pressedButton;
                                nomLabel.setText(sensors.get(currentSensorIndex).getName());
                                variableDescriptionLabel.setText(sensors.get(currentSensorIndex).getDescription());
                            }
                        }),0,i);
                    }
                    nomLabel.setText(sensors.get(currentSensorIndex).getName());
                    variableDescriptionLabel.setText(sensors.get(currentSensorIndex).getDescription());
                }
            });
        }
    }

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new SensorContent();
		}
		return content;
	}

	@Override
	public void handleMessage(Object message) {
		System.out.println(message);
		if(message instanceof String) {
			JSONObject json = new JSONObject((String)message);
			if(json.getString("recipient").equals("sensor")) {
				switch (json.getString("action")) {
				case "getAll":
					JSONArray array = json.getJSONArray("sensors");
					for (int i = 0; i < array.length(); i++) {
						JSONObject current = array.getJSONObject(i);
						sensors.add(new Sensor(current.getInt("id"),current.getString("name"),current.getString("description"),null));
					}
					updateUI();
					break;
				default:
					break;
				}
			}
		}
	}
}

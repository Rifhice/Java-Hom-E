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
import user.ClientFX;
import user.models.ContinuousValue;
import user.models.DiscreteValue;
import user.models.EnvironmentVariable;
import user.models.Sensor;
import user.models.Value;
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
    
    private MyRectangle sensorBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f); 
	private MyRectangle nomBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionBounds = new MyRectangle(0.1f, 0.2f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionBounds = new MyRectangle(0.1f, 0.25f, 0.5f, 0.2f); 
	
	private MyRectangle variableBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f);
	private MyRectangle nomVariableBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionVariableBounds = new MyRectangle(0.1f, 0.2f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionVariableBounds = new MyRectangle(0.1f, 0.25f, 0.5f, 0.2f); 
	private MyRectangle currentValueBounds = new MyRectangle(0.1f, 0.35f, 0.5f, 0.2f);
	private MyRectangle currentValueVariableBounds = new MyRectangle(0.1f, 0.4f, 0.5f, 0.2f);
	
    private MyGridPane sensorsListGrid;
    
    private MyLabel sensorLabel;
    private MyLabel nomLabel;
    private MyLabel description;
    private MyLabel variableDescriptionLabel;
    
    private MyLabel variableLabel;
    private MyLabel nomVariableLabel;
    private MyLabel descriptionVariable;
    private MyLabel variableDescriptionVariableLabel;
    private MyLabel currentValueLabel;
    private MyLabel currentValueVariableLabel;
    
    private int currentSensorIndex = -1;
    private final int NB_SENSOR_DISPLAYED = 7;
    
	private SensorContent() {
	    sensorsListGrid = new MyGridPane(sensorsListBounds.computeBounds(width, height));
        MyScrollPane sensorList = new MyScrollPane(sensorsListBounds.computeBounds(width,height));
        sensorList.setContent(sensorsListGrid);
        
        
	    MyPane selectedSensorPane = new MyPane(selectedSensorBounds.computeBounds(width, height));
	    sensorLabel = new MyLabel("Nom",sensorBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()),1.5f);
	    sensorLabel.centerX(selectedSensorPane.getPrefWidth());
	    sensorLabel.setAlignment(Pos.CENTER);
	    
	    nomLabel = new MyLabel("nom",nomBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));
	    nomLabel.centerX(selectedSensorPane.getPrefWidth());
	    nomLabel.setAlignment(Pos.CENTER);
	    description = new MyLabel("Description",descriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));
	    variableDescriptionLabel = new MyLabel("descr",variableDescriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));	    
	    selectedSensorPane.getChildren().add(sensorLabel);
	    selectedSensorPane.getChildren().add(nomLabel);
	    selectedSensorPane.getChildren().add(description);
	    selectedSensorPane.getChildren().add(variableDescriptionLabel);
	    
	    
	    MyPane variableValuePane = new MyPane(latestActionsBounds.computeBounds(width, height));
	    variableLabel = new MyLabel("Variable",variableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()),1.5f);
	    variableLabel.centerX(selectedSensorPane.getPrefWidth());
	    variableLabel.setAlignment(Pos.CENTER);
	    
	    nomVariableLabel = new MyLabel("nom",nomVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    nomVariableLabel.centerX(selectedSensorPane.getPrefWidth());
	    nomVariableLabel.setAlignment(Pos.CENTER);
	    
	    descriptionVariable = new MyLabel("Description",descriptionVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    variableDescriptionVariableLabel = new MyLabel("descr",variableDescriptionVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    
	    currentValueLabel = new MyLabel("Value",currentValueBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()),1.5f);
	    currentValueLabel.centerX(selectedSensorPane.getPrefWidth());
	    currentValueLabel.setAlignment(Pos.CENTER);
	    
	    currentValueVariableLabel = new MyLabel("value",currentValueVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    currentValueVariableLabel.centerX(selectedSensorPane.getPrefWidth());
	    currentValueVariableLabel.setAlignment(Pos.CENTER);
	    variableValuePane.getChildren().add(variableLabel);
	    variableValuePane.getChildren().add(nomVariableLabel);
	    variableValuePane.getChildren().add(descriptionVariable);
	    variableValuePane.getChildren().add(variableDescriptionVariableLabel);
	    variableValuePane.getChildren().add(currentValueLabel);
	    variableValuePane.getChildren().add(currentValueVariableLabel);
	    
	    
        this.getChildren().add(sensorList);
	    this.getChildren().add(selectedSensorPane);
	    this.getChildren().add(variableValuePane);
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
                                nomVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getName());;
                                variableDescriptionVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getDescription());
                                currentValueVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getValue().getCurrentValue().toString());
                            }
                        }),0,i);
                    }
                    nomLabel.setText(sensors.get(currentSensorIndex).getName());
                    variableDescriptionLabel.setText(sensors.get(currentSensorIndex).getDescription());
                    nomVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getName());;
                    variableDescriptionVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getDescription());
                    currentValueVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getValue().getCurrentValue().toString());
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
						Sensor sensor = new Sensor(current.getInt("id"),current.getString("name"),current.getString("description"),null);
						JSONObject variable = current.getJSONObject("environmentVariable");
						EnvironmentVariable var = new EnvironmentVariable(variable.getInt("id"), variable.getString("name"), variable.getString("description"), variable.getString("unit"));
						JSONObject value = variable.getJSONObject("value");
						Value val = null;
						if(value.getString("type").equals("discrete")) {
							ArrayList<String> possibleValues = new ArrayList<String>();
							JSONArray array2 = value.getJSONArray("possibleValues");
							for (int j = 0; j < array2.length(); j++) {
								possibleValues.add(array2.getString(j));
							}
							val = new DiscreteValue(possibleValues, value.getString("currentValue")); 
						}
						else if(value.getString("type").equals("continuous")) {
							val = new ContinuousValue(value.getDouble("valueMin"),value.getDouble("valueMax"),value.getDouble("precision"),value.getDouble("currentValue"));
						}
						var.setValue(val);
						sensor.setEnvironmentVariable(var);
						sensors.add(sensor);
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

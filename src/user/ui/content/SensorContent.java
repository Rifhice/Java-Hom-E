package user.ui.content;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.StringConverter;
import user.ClientFX;
import user.models.Category;
import user.models.Sensor;
import user.models.SensorCategory;
import user.models.environmentVariable.Value;
import user.models.environmentVariable.DiscreteValue;
import user.models.environmentVariable.EnvironmentVariable;
import user.models.environmentVariable.ContinuousValue;

import user.tools.ArithmeticOperator;
import user.tools.GraphicalCharter;
import user.tools.Triplet;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyCategoryDialog;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MyTextDialog;
import user.ui.componentJavaFX.MyVariableDialog;

public class SensorContent extends Content {

	private static SensorContent content = null;
	
	ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	ArrayList<Category> categories = new ArrayList<Category>();
	
	private MyRectangle sensorsListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
	private MyRectangle selectedSensorBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
    private MyRectangle activateBounds = new MyRectangle(0.35f, 0.7f, 0.3f, 0.05f);
    private MyRectangle sensorBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f); 
	private MyRectangle nomBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionBounds = new MyRectangle(0.1f, 0.24f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionBounds = new MyRectangle(0.1f, 0.245f, 0.8f, 0.2f); 
	private MyRectangle changeDescriptionBounds = new MyRectangle(0.35f, 0.05f, 0.3f, 0.05f);

	
	private MyRectangle variableBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f);
	private MyRectangle nomVariableBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionVariableBounds = new MyRectangle(0.1f, 0.24f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionVariableBounds = new MyRectangle(0.1f, 0.245f, 0.5f, 0.2f); 
	private MyRectangle currentValueBounds = new MyRectangle(0.5f, 0.2f, 1f, 0.2f);
	private MyRectangle currentValueVariableBounds = new MyRectangle(0.5f, 0.6f, 0.5f, 0.2f);
	private MyRectangle boxValueBounds = new MyRectangle(0.05f, 0.5f, 0.9f, 0.4f);
	private MyRectangle updateEnvVarBounds = new MyRectangle(0.35f, 0.05f, 0.3f, 0.05f);
	
	
    private MyGridPane sensorsListGrid;
    private MyComboBox<Category> categoriesComboBox;
    
    private MyButtonFX activateButton;
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
    private MyButtonFX changeDescriptionButton;
    
    private int currentSensorIndex = -1;
    private final int NB_SENSOR_DISPLAYED = 7;
    
    private EventHandler<ActionEvent> sensorButtonEvent;
    
	private SensorContent() {
		sensorButtonEvent = new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
	            currentSensorIndex = pressedButton;
	            updateTexts(currentSensorIndex);
	        }
	    };
	    sensorsListGrid = new MyGridPane(sensorsListBounds.computeBounds(width, height));
        MyScrollPane sensorList = new MyScrollPane(sensorsListBounds.computeBounds(width,height));
        sensorList.setContent(sensorsListGrid);
        categoriesComboBox = new MyComboBox<Category>(categories,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED);
        categoriesComboBox.setConverter(new StringConverter<Category>() {

			@Override
			public Category fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Category arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getName();
			}
        });
        categoriesComboBox.valueProperty().addListener(new ChangeListener<Category>() {
			@Override
			public void changed(ObservableValue<? extends Category> arg0, Category arg1, Category arg2) {
	            Platform.runLater(new Runnable() {
	                @Override public void run() {
	                	sensorsListGrid.getChildren().remove(1, sensorsListGrid.getChildren().size());
	                	if(arg2 != null) {
		                	if(arg2.getId() == -2) { //NO CATEGORIES
			                	for (int i = 0; i < sensors.size(); i++) {
			                		if(sensors.get(i).getSensorCategory() == null) {
										sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED,sensorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else if(arg2.getId() == -1) { // ALL CATEGORIES
			                	for (int i = 0; i < sensors.size(); i++) {
									sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED, sensorButtonEvent),0,i + 1);
								}
		                	}
		                	else if(arg2.getId() == -3) { // Activated
			                	for (int i = 0; i < sensors.size(); i++) {
			                		if(sensors.get(i).isEnabled()) {
										sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED,sensorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else if(arg2.getId() == -4) { // Deactivated
			                	for (int i = 0; i < sensors.size(); i++) {
			                		if(!sensors.get(i).isEnabled()) {
										sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED,sensorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else{ // SELECTED CATEGORY
		                		for (int i = 0; i < sensors.size(); i++) {
		                			if(sensors.get(i).getSensorCategory() != null) {
										if(sensors.get(i).getSensorCategory().getId() == arg2.getId()) {
											sensorsListGrid.add(new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED,sensorButtonEvent),0,i + 1);
										}
		                			}
								}
		                	}
	                	}
	                }
	            });
			}
		});
    	
	    MyPane selectedSensorPane = new MyPane(selectedSensorBounds.computeBounds(width, height));
	    
	    changeDescriptionButton = new MyButtonFX("Update",changeDescriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()) ,new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Triplet<String,String,Category> result = new MyCategoryDialog("Modify a sensor", "Modify a sensor", sensors.get(currentSensorIndex).getName(), sensors.get(currentSensorIndex).getDescription(), (Category)sensors.get(currentSensorIndex).getSensorCategory(), new ArrayList<Category>(categories)).showAndReturn();
                if(result != null){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "sensor");
                    json.put("action", "update");
                    json.put("idSensor", sensors.get(currentSensorIndex).getId());
                    json.put("name", result.getFirst());
                    json.put("description", result.getSecond());
                    json.put("idCategory", result.getThird().getId());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
			}
		});
	    activateButton = new MyButtonFX("Activate",activateBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	JSONObject json = new JSONObject();
                if(sensors.get(currentSensorIndex).isEnabled()) {
        	        json.put("recipient", "sensor");
        	        json.put("action", "deactivate");
        	        json.put("idSensor", sensors.get(currentSensorIndex).getId());
        	        try {
        	            ClientFX.client.sendToServer(json.toString());
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
                }
                else {
        	        json.put("recipient", "sensor");
        	        json.put("action", "activate");
        	        json.put("idSensor", sensors.get(currentSensorIndex).getId());
        	        try {
        	            ClientFX.client.sendToServer(json.toString());
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
                }
            }
        });
	    
	    sensorLabel = new MyLabel("Name",sensorBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()),1.5f);
	    sensorLabel.centerX(selectedSensorPane.getPrefWidth());
	    sensorLabel.setAlignment(Pos.CENTER);
	    sensorLabel.setStyle("-fx-font-weight: bold;");
	    
	    nomLabel = new MyLabel("Name",nomBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));
	    nomLabel.centerX(selectedSensorPane.getPrefWidth());
	    nomLabel.setAlignment(Pos.CENTER);
	    nomLabel.setStyle("-fx-font-weight: bold;");
	    description = new MyLabel("Description",descriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()), 1.3f);
	    variableDescriptionLabel = new MyLabel("descr",variableDescriptionBounds.computeBounds(selectedSensorPane.getPrefWidth(), selectedSensorPane.getPrefHeight()));	    
	    
	    selectedSensorPane.getChildren().add(changeDescriptionButton);
	    selectedSensorPane.getChildren().add(activateButton);
	    selectedSensorPane.getChildren().add(sensorLabel);
	    selectedSensorPane.setStyle(""	+ 
    		"-fx-background-color: " + GraphicalCharter.LIGHT_GRAY + ";" +
    		"-fx-border-color: " + GraphicalCharter.DARK_GRAY
	    );
	    selectedSensorPane.getChildren().add(nomLabel);
	    selectedSensorPane.getChildren().add(description);
	    selectedSensorPane.getChildren().add(variableDescriptionLabel);
	    
	    
	    MyPane variableValuePane = new MyPane(latestActionsBounds.computeBounds(width, height));
	    variableValuePane.setStyle(""	+ 
    		"-fx-background-color: " + GraphicalCharter.LIGHT_GRAY + ";" +
    		"-fx-border-color: " + GraphicalCharter.DARK_GRAY
	    );
	    variableLabel = new MyLabel("Variable",variableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()),1.5f);
	    variableLabel.centerX(selectedSensorPane.getPrefWidth());
	    variableLabel.setAlignment(Pos.CENTER);
	    variableLabel.setStyle("-fx-font-weight: bold;");
	    
	    nomVariableLabel = new MyLabel("nom",nomVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    nomVariableLabel.centerX(selectedSensorPane.getPrefWidth());
	    nomVariableLabel.setAlignment(Pos.CENTER);
	    nomVariableLabel.setStyle("-fx-font-weight: bold;");
	    
	    descriptionVariable = new MyLabel("Description",descriptionVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()), 1.3f);
	    variableDescriptionVariableLabel = new MyLabel("descr",variableDescriptionVariableBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    
	    MyPane currentValuePane = new MyPane(boxValueBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()));
	    currentValuePane.setStyle(""
	    		+ "-fx-background-color: " + GraphicalCharter.WHITE + ";" 
	    		+ "-fx-background-radius: 8 8 8 8;"
	    );
	    currentValueLabel = new MyLabel("Value",currentValueBounds.computeBounds(currentValuePane.getPrefWidth(), currentValuePane.getPrefHeight()),true, 1.5f);
	    currentValueLabel.centerX(currentValuePane.getPrefWidth());
	    currentValueLabel.setAlignment(Pos.CENTER);
	    currentValueLabel.setStyle("-fx-font-weight: bold;");
	    
	    currentValueVariableLabel = new MyLabel("value",currentValueVariableBounds.computeBounds(currentValuePane.getPrefWidth(), currentValuePane.getPrefHeight()), true, 1.3f);
	    MyButtonFX updateEnvVarButton = new MyButtonFX("Update",updateEnvVarBounds.computeBounds(variableValuePane.getPrefWidth(), variableValuePane.getPrefHeight()) ,new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Triplet<String,String,String> result = new MyVariableDialog("Modify a variable", "Modify a variable", sensors.get(currentSensorIndex).getEnvironmentVariables().getName(), sensors.get(currentSensorIndex).getEnvironmentVariables().getDescription(), sensors.get(currentSensorIndex).getEnvironmentVariables().getUnit()).showAndReturn();
                if(result != null){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "sensor");
                    json.put("action", "updateVariable");
                    json.put("idVariable", sensors.get(currentSensorIndex).getEnvironmentVariables().getId());
                    json.put("name", result.getFirst());
                    json.put("description", result.getSecond());
                    json.put("unit", result.getThird());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
			}
		});
	    variableValuePane.getChildren().add(updateEnvVarButton);
	    variableValuePane.getChildren().add(variableLabel);
	    variableValuePane.getChildren().add(nomVariableLabel);
	    variableValuePane.getChildren().add(descriptionVariable);
	    variableValuePane.getChildren().add(variableDescriptionVariableLabel);
	    
	    currentValuePane.getChildren().add(currentValueLabel);
	    currentValuePane.getChildren().add(currentValueVariableLabel);
	    variableValuePane.getChildren().add(currentValuePane);
	    
	    
        this.getChildren().add(sensorList);
	    this.getChildren().add(selectedSensorPane);
	    this.getChildren().add(variableValuePane);
	    updateData();
	}
	
    public void updateData() {
    	updateSensorData();
    	updateCategoryData();
    }
    
    public void updateSensorData() {
        JSONObject getSensors = new JSONObject();
        getSensors.put("recipient", "sensor");
        getSensors.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getSensors.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCategoryData() {
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
    	if(sensors.size() != 0) {
            if(currentSensorIndex == -1) {
            	currentSensorIndex = 0;
            }
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	sensorsListGrid.getChildren().clear();
                	categoriesComboBox.setValues(categories);
                	sensorsListGrid.add(categoriesComboBox,0,0);
                	categoriesComboBox.getSelectionModel().selectFirst();
                	for (int i = 0; i < sensors.size(); i++) {
                		MyButtonFX button = new MyButtonFX(sensors.get(i).getName(),i,sensorsListGrid.getPrefWidth(),sensorsListGrid.getPrefHeight() / NB_SENSOR_DISPLAYED,sensorButtonEvent);
                    	sensorsListGrid.add(button,0,i + 1);
                    }
                    nomLabel.setText(sensors.get(currentSensorIndex).getName());
                    variableDescriptionLabel.setText(sensors.get(currentSensorIndex).getDescription());
                    nomVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getName());;
                    variableDescriptionVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getDescription());
                    currentValueVariableLabel.setText(sensors.get(currentSensorIndex).getEnvironmentVariables().getValue().getCurrentValue().toString());
                    if(sensors.get(currentSensorIndex).isEnabled()) {
                        activateButton.setText("Deactivate");
                    }
                    else {
                    	activateButton.setText("Activated");
                    }
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
		if(message instanceof String) {
			JSONObject json = new JSONObject((String)message);
			if(json.getString("recipient").equals("sensor")) {
			    
				switch (json.getString("action")) {
				
				case "getAll":
					JSONArray array = json.getJSONArray("sensors");
					for (int i = 0; i < array.length(); i++) {
						JSONObject current = array.getJSONObject(i);
						System.out.println(current.toString());
						Sensor sensor = new Sensor(current.getInt("id"),current.getString("name"),current.getString("description"),null);
						if(current.getBoolean("activated")) {
							sensor.enable();
						}
						else {
							sensor.disable();
						}
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
						try {
							JSONObject currentCategory = current.getJSONObject("category");
							sensor.setSensorCategory(new SensorCategory(currentCategory.getInt("id"), currentCategory.getString("name"), currentCategory.getString("description")));
						}
						catch (Exception e) {
							sensor.setSensorCategory(null);
						}
						sensors.add(sensor);
					}
					updateUI();
					break;
					
				case "changeValue":
					for (int i = 0; i < sensors.size(); i++) {
						if(sensors.get(i).getId() == json.getInt("idSensor")) {
							sensors.get(i).changeValue(json.getString("value"));
						}
						updateUI();
					}
					break;
				case "activate":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < sensors.size(); i++) {
							if(sensors.get(i).getId() == json.getInt("idSensor")) {
								sensors.get(i).enable();
							}
						}
					}
					updateUI();
					break;
				case "deactivate":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < sensors.size(); i++) {
							if(sensors.get(i).getId() == json.getInt("idSensor")) {
								sensors.get(i).disable();
							}
						}
					}
					updateUI();
					break;
				case "update":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < sensors.size(); i++) {
							if(sensors.get(i).getId() == json.getInt("idSensor")) {
								for (int j = 0; j < categories.size(); j++) {
									if(categories.get(j).getId() == json.getInt("idCategory")) {
										sensors.get(i).setSensorCategory((SensorCategory)categories.get(j));
									}
								}
								sensors.get(i).setName(json.getString("name"));
								sensors.get(i).setDescription(json.getString("description"));
							}
						}
					}
					updateUI();
					break;
				case "updateVariable":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < sensors.size(); i++) {
							if(sensors.get(i).getEnvironmentVariables().getId() == json.getInt("idVariable")) {
								sensors.get(i).getEnvironmentVariables().setName(json.getString("name"));
								sensors.get(i).getEnvironmentVariables().setDescription(json.getString("description"));
								sensors.get(i).getEnvironmentVariables().setUnit(json.getString("unit"));
							}
						}
					}
					updateUI();
					break;
				default:
					break;
				}
			}
			else if(json.getString("recipient").equals("sensorCategories")){
				switch (json.getString("action")) {
				case "getAll":
					categories.clear();
					categories.add(new SensorCategory(-1,"All Sensors"));
                    JSONArray arrArg = json.getJSONArray("categories");
                    for (int j = 0; j < arrArg.length(); j++){
                        JSONObject current = arrArg.getJSONObject(j);
                        categories.add(new SensorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
                    }
					categories.add(new SensorCategory(-2,"No category"));
					categories.add(new SensorCategory(-3,"Activated"));
					categories.add(new SensorCategory(-4,"Deactivated"));
                    updateUI();
					break;

				default:
					break;
				}
			}
		}
	}
	
	public void updateTexts(int index) {
		nomLabel.setText(sensors.get(index).getName());
        variableDescriptionLabel.setText(sensors.get(index).getDescription());
        nomVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getName());;
        variableDescriptionVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getDescription());
        currentValueVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getValue().getCurrentValue().toString());
        if(sensors.get(index).isEnabled()) {
            activateButton.setText("Deactivate");
        }
        else {
        	activateButton.setText("Activated");
        }
	}
}

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
import javafx.util.StringConverter;
import user.ClientFX;
import user.tools.GraphicalCharter;
import user.tools.Triplet;
import javafx.scene.control.Label;
import user.models.Actuator;
import user.models.ActuatorCategory;
import user.models.Category;
import user.models.Command;
import user.models.CommandValue;
import user.models.ContinuousCommandValue;
import user.models.DiscreteCommandValue;
import user.models.Sensor;
import user.models.SensorCategory;
import user.models.environmentVariable.ContinuousValue;
import user.models.environmentVariable.DiscreteValue;
import user.models.environmentVariable.EnvironmentVariable;
import user.models.environmentVariable.Value;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyCategoryDialog;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class ActuatorContent extends Content {

	private static ActuatorContent content = null;
	
	ArrayList<Actuator> actuators = new ArrayList<Actuator>();
	ArrayList<Category> categories = new ArrayList<Category>();
	
	private MyRectangle actuatorsListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
	private MyRectangle selectedActuatorBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);

    private MyRectangle activateBounds = new MyRectangle(0.35f, 0.7f, 0.3f, 0.05f);
    private MyRectangle actuatorBounds = new MyRectangle(0f, 0.1f, 1f, 0.05f); 
	private MyRectangle nomBounds = new MyRectangle(0f, 0.15f, 1f, 0.05f); 
	private MyRectangle descriptionBounds = new MyRectangle(0.1f, 0.24f, 0.375f, 0.05f); 
	private MyRectangle variableDescriptionBounds = new MyRectangle(0.1f, 0.245f, 0.8f, 0.2f); 
	private MyRectangle changeDescriptionBounds = new MyRectangle(0.35f, 0.05f, 0.3f, 0.05f);

    private MyGridPane actuatorsListGrid;
    private MyComboBox<Category> categoriesComboBox;
    
    private MyButtonFX activateButton;
    private MyLabel actuatorLabel;
    private MyLabel nomLabel;
    private MyLabel description;
    private MyLabel variableDescriptionLabel;
    private MyButtonFX updateButton;
    
    private int currentActuatorIndex = -1;
    private final int NB_ACTUATOR_DISPLAYED = 8;
    
    private EventHandler<ActionEvent> actuatorButtonEvent;
    
	private ActuatorContent() {
		actuatorButtonEvent = new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	            int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
	            currentActuatorIndex = pressedButton;
	            updateTexts(currentActuatorIndex);
	        }
	    };
	    actuatorsListGrid = new MyGridPane(actuatorsListBounds.computeBounds(width, height));
        MyScrollPane acutatorList = new MyScrollPane(actuatorsListBounds.computeBounds(width,height));
        acutatorList.setContent(actuatorsListGrid);
        categoriesComboBox = new MyComboBox<Category>(categories,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED);
        categoriesComboBox.setConverter(new StringConverter<Category>() {

			@Override
			public Category fromString(String arg0) {
				return null;
			}

			@Override
			public String toString(Category arg0) {
				return arg0 == null ? "" : arg0.getName();
			}
        });
        categoriesComboBox.valueProperty().addListener(new ChangeListener<Category>() {
			@Override
			public void changed(ObservableValue<? extends Category> arg0, Category arg1, Category arg2) {
	            Platform.runLater(new Runnable() {
	                @Override public void run() {
	                	actuatorsListGrid.getChildren().remove(1, actuatorsListGrid.getChildren().size());
	                	if(arg2 != null) {
		                	if(arg2.getId() == -2) { //NO CATEGORIES
			                	for (int i = 0; i < actuators.size(); i++) {
			                		if(actuators.get(i).getActuatorCategory() == null) {
			                			actuatorsListGrid.add(new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED,actuatorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else if(arg2.getId() == -1) { // ALL CATEGORIES
			                	for (int i = 0; i < actuators.size(); i++) {
			                		actuatorsListGrid.add(new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED, actuatorButtonEvent),0,i + 1);
								}
		                	}
		                	else if(arg2.getId() == -3) { // Activated
			                	for (int i = 0; i < actuators.size(); i++) {
			                		if(actuators.get(i).isEnabled()) {
			                			actuatorsListGrid.add(new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED,actuatorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else if(arg2.getId() == -4) { // Deactivated
			                	for (int i = 0; i < actuators.size(); i++) {
			                		if(!actuators.get(i).isEnabled()) {
			                			actuatorsListGrid.add(new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED,actuatorButtonEvent),0,i + 1);
			                		}
								}
		                	}
		                	else{ // SELECTED CATEGORY
		                		for (int i = 0; i < actuators.size(); i++) {
		                			if(actuators.get(i).getActuatorCategory() != null) {
										if(actuators.get(i).getActuatorCategory().getId() == arg2.getId()) {
											actuatorsListGrid.add(new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED,actuatorButtonEvent),0,i + 1);
										}
		                			}
								}
		                	}
	                	}
	                }
	            });
			}
		});
		
        MyPane selectedActuatorPane = new MyPane(selectedActuatorBounds.computeBounds(width, height));
	    
	    updateButton = new MyButtonFX("Update",changeDescriptionBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()) ,new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Triplet<String,String,Category> result = new MyCategoryDialog("Modify an actuator", "Modify an actuator", actuators.get(currentActuatorIndex).getName(), actuators.get(currentActuatorIndex).getDescription(), (Category)actuators.get(currentActuatorIndex).getActuatorCategory(), new ArrayList<Category>(categories)).showAndReturn();
                if(result != null){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "actuator");
                    json.put("action", "update");
                    json.put("idActuator", actuators.get(currentActuatorIndex).getId());
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
	    activateButton = new MyButtonFX("Activate",activateBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	JSONObject json = new JSONObject();
                if(actuators.get(currentActuatorIndex).isEnabled()) {
        	        json.put("recipient", "actuator");
        	        json.put("action", "deactivate");
        	        json.put("idActuator", actuators.get(currentActuatorIndex).getId());
        	        try {
        	            ClientFX.client.sendToServer(json.toString());
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
                }
                else {
        	        json.put("recipient", "actuator");
        	        json.put("action", "activate");
        	        json.put("idActuator", actuators.get(currentActuatorIndex).getId());
        	        try {
        	            ClientFX.client.sendToServer(json.toString());
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
                }
            }
        });
	    
	    actuatorLabel = new MyLabel("Name",actuatorBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()),1.5f);
	    actuatorLabel.centerX(selectedActuatorPane.getPrefWidth());
	    actuatorLabel.setAlignment(Pos.CENTER);
	    actuatorLabel.setStyle("-fx-font-weight: bold;");
	    
	    nomLabel = new MyLabel("Name",nomBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()));
	    nomLabel.centerX(selectedActuatorPane.getPrefWidth());
	    nomLabel.setAlignment(Pos.CENTER);
	    nomLabel.setStyle("-fx-font-weight: bold;");
	    description = new MyLabel("Description",descriptionBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()), 1.3f);
	    variableDescriptionLabel = new MyLabel("descr",variableDescriptionBounds.computeBounds(selectedActuatorPane.getPrefWidth(), selectedActuatorPane.getPrefHeight()));	    
	    
	    selectedActuatorPane.getChildren().add(updateButton);
	    selectedActuatorPane.getChildren().add(activateButton);
	    selectedActuatorPane.getChildren().add(actuatorLabel);
	    selectedActuatorPane.setStyle(""	+ 
    		"-fx-background-color: " + GraphicalCharter.LIGHT_GRAY + ";" +
    		"-fx-border-color: " + GraphicalCharter.DARK_GRAY
	    );
	    selectedActuatorPane.getChildren().add(nomLabel);
	    selectedActuatorPane.getChildren().add(description);
	    selectedActuatorPane.getChildren().add(variableDescriptionLabel);
	    
	    
	    MyPane latestActionsPane = new MyPane(latestActionsBounds.computeBounds(width, height));
	    latestActionsPane.setStyle("-fx-background-color: rgb(100,250,100)");

	    this.getChildren().add(acutatorList);
	    this.getChildren().add(selectedActuatorPane);
	    this.getChildren().add(latestActionsPane);
	    latestActionsPane.getChildren().add(new Label("Latest Actions"));
	    updateData();
	}

    public void updateData() {
    	updateActuatorData();
    	updateCategoryData();
    }
    
    public void updateActuatorData() {
        JSONObject getActuators = new JSONObject();
        getActuators.put("recipient", "actuator");
        getActuators.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getActuators.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateCategoryData() {
        JSONObject getSensor = new JSONObject();
        getSensor.put("recipient", "actuatorCategories");
        getSensor.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getSensor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateUI() {
    	if(actuators.size() != 0) {
            if(currentActuatorIndex == -1) {
            	currentActuatorIndex = 0;
            }
            Platform.runLater(new Runnable() {
                @Override public void run() {
                	actuatorsListGrid.getChildren().clear();
                	categoriesComboBox.setValues(categories);
                	actuatorsListGrid.add(categoriesComboBox,0,0);
                	categoriesComboBox.getSelectionModel().selectFirst();
                	for (int i = 0; i < actuators.size(); i++) {
                		MyButtonFX button = new MyButtonFX(actuators.get(i).getName(),i,actuatorsListGrid.getPrefWidth(),actuatorsListGrid.getPrefHeight() / NB_ACTUATOR_DISPLAYED,actuatorButtonEvent);
                		actuatorsListGrid.add(button,0,i + 1);
                    }
                    nomLabel.setText(actuators.get(currentActuatorIndex).getName());
                    variableDescriptionLabel.setText(actuators.get(currentActuatorIndex).getDescription());
                    /*nomVariableLabel.setText(actuators.get(currentActuatorIndex).getEnvironmentVariables().getName());;
                    variableDescriptionVariableLabel.setText(actuators.get(currentActuatorIndex).getEnvironmentVariables().getDescription());
                    currentValueVariableLabel.setText(actuators.get(currentActuatorIndex).getEnvironmentVariables().getValue().getCurrentValue().toString());
                    */
                    if(actuators.get(currentActuatorIndex).isEnabled()) {
                        activateButton.setText("Deactivate");
                    }
                    else {
                    	activateButton.setText("Activate");
                    }
                }
            });
        }
    }
	
	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new ActuatorContent();
		}
		return content;
	}
	
	public void updateTexts(int index) {
		nomLabel.setText(actuators.get(index).getName());
        variableDescriptionLabel.setText(actuators.get(index).getDescription());
        /*nomVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getName());;
        variableDescriptionVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getDescription());
        currentValueVariableLabel.setText(sensors.get(index).getEnvironmentVariables().getValue().getCurrentValue().toString());
        */
        if(actuators.get(index).isEnabled()) {
            activateButton.setText("Deactivate");
        }
        else {
        	activateButton.setText("Activate");
        }
	}
	
	@Override
	public void handleMessage(Object message) {
		if(message instanceof String) {
			JSONObject json = new JSONObject((String)message);
			System.out.println(json.toString());
			if(json.getString("recipient").equals("actuator")) {
			    
				switch (json.getString("action")) {
				
				case "getAll":
					actuators.clear();
					JSONArray array = json.getJSONArray("actuators");
					for (int i = 0; i < array.length(); i++) {
						JSONObject current = array.getJSONObject(i);
						System.out.println(current.toString());
						Actuator actuator = new Actuator(current.getInt("id"),current.getString("name"),current.getString("description"),null);
						if(current.getBoolean("activated")) {
							actuator.enable();
						}
						else {
							actuator.disable();
						}
						JSONArray commands = current.getJSONArray("commands");
						ArrayList<Command> actuatorCommands = new ArrayList<Command>();
						for (int j = 0; j < commands.length(); j++) {
							JSONObject currentCommand = commands.getJSONObject(j);
							Command command = new Command(currentCommand.getInt("id"),currentCommand.getString("name"),currentCommand.getString("description"),currentCommand.getString("key"));
							try {
								JSONArray commandValues = currentCommand.getJSONArray("commandValue");
								ArrayList<CommandValue> commandValuesList = new ArrayList<CommandValue>();
								for (int z = 0; z < commandValues.length(); z++) {
									JSONObject currentCommandValue = commandValues.getJSONObject(z);
									if(currentCommandValue.getString("type").equals("discrete")) {
										JSONArray possibleValues = currentCommandValue.getJSONArray("possibleValues");
										ArrayList<String> possibleValue = new ArrayList<String>();
										for (int h = 0; h < possibleValues.length(); h++) {
											possibleValue.add(possibleValues.getString(h));
										}
										commandValuesList.add(new DiscreteCommandValue(possibleValue));
									}
									else {
										commandValuesList.add(new ContinuousCommandValue(currentCommandValue.getDouble("valueMin"),currentCommandValue.getDouble("valueMax"),currentCommandValue.getDouble("precision")));
									}
								}
								command.setCommandValues(commandValuesList);
							}
							catch(Exception e) {
								System.out.println("No command value for this command !");
							}
						}
						try {
							JSONObject currentCategory = current.getJSONObject("actuatorCategory");
							actuator.setActuatorCategory(new ActuatorCategory(currentCategory.getInt("id"), currentCategory.getString("name"), currentCategory.getString("description")));
						}
						catch (Exception e) {
							actuator.setActuatorCategory(null);
						}
						actuators.add(actuator);
					}
					updateUI();
					break;
				case "activate":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < actuators.size(); i++) {
							if(actuators.get(i).getId() == json.getInt("idActuator")) {
								actuators.get(i).enable();
							}
						}
					}
					updateUI();
					break;
				case "deactivate":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < actuators.size(); i++) {
							if(actuators.get(i).getId() == json.getInt("idActuator")) {
								actuators.get(i).disable();
							}
						}
					}
					updateUI();
					break;
				case "update":
					if(json.getString("result").equals("success")) {
						for (int i = 0; i < actuators.size(); i++) {
							if(actuators.get(i).getId() == json.getInt("idActuator")) {
								for (int j = 0; j < categories.size(); j++) {
									if(categories.get(j).getId() == json.getInt("idCategory")) {
										actuators.get(i).setActuatorCategory((ActuatorCategory)categories.get(j));
									}
								}
								actuators.get(i).setName(json.getString("name"));
								actuators.get(i).setDescription(json.getString("description"));
							}
						}
					}
					updateUI();
					break;
				default:
					break;
				}
			}
			else if(json.getString("recipient").equals("actuatorCategories")){
				switch (json.getString("action")) {
				case "getAll":
					categories.clear();
					categories.add(new ActuatorCategory(-1,"All Actuator"));
                    JSONArray arrArg = json.getJSONArray("categories");
                    for (int j = 0; j < arrArg.length(); j++){
                        JSONObject current = arrArg.getJSONObject(j);
                        categories.add(new ActuatorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
                    }
					categories.add(new ActuatorCategory(-2,"No category"));
					categories.add(new ActuatorCategory(-3,"Activated"));
					categories.add(new ActuatorCategory(-4,"Deactivated"));
                    updateUI();
					break;

				default:
					break;
				}
			}
		}
	}
}

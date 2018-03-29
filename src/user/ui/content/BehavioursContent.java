package user.ui.content;

import java.awt.GradientPaint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.StringConverter;
import user.ClientFX;
import user.models.Actuator;
import user.models.Command;
import user.models.CommandValue;
import user.models.ContinuousCommandValue;
import user.models.DiscreteCommandValue;
import user.models.environmentVariable.ContinuousValue;
import user.models.environmentVariable.DiscreteValue;
import user.models.environmentVariable.EnvironmentVariable;
import user.models.environmentVariable.Value;
import user.models.evaluable.Block;
import user.models.evaluable.Evaluable;
import user.models.evaluable.Expression;
import user.tools.ARITHMETICOPERATOR;
import user.tools.BOOLEANOPERATOR;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MySlider;
import user.ui.scene.ContentScene;

public class BehavioursContent extends Content {

    // =========== UI =========== //
	private static BehavioursContent content = null;
	
	MyRectangle variableBounds = new MyRectangle(0.05f, 0.1f, 0.1f, 0.05f);
	MyRectangle operatorBoundsTop = new MyRectangle(0.2f, 0.1f, 0.1f, 0.05f);
	MyRectangle valueBounds = new MyRectangle(0.35f, 0.1f, 0.1f, 0.05f);
	MyRectangle newBlockBounds = new MyRectangle(0.5f, 0.1f, 0.1f, 0.05f);
	
	MyRectangle leftEvaluableBounds = new MyRectangle(0.05f, 0.2f, 0.1f, 0.05f);
	MyRectangle operatorBoundsBottom = new MyRectangle(0.2f, 0.2f, 0.1f, 0.05f);
	MyRectangle rightEvaluableBounds = new MyRectangle(0.35f, 0.2f, 0.1f, 0.05f);
	MyRectangle expressionBounds = new MyRectangle(0.5f, 0.2f, 0.1f, 0.05f);

	MyRectangle finalExpressionLabelBounds = new MyRectangle(0.05f, 0.4f, 0.1f, 0.05f);
	MyRectangle finalExpressionComboBounds = new MyRectangle(0.17f, 0.4f, 0.15f, 0.05f);
	MyRectangle commandsLabelBounds = new MyRectangle(0.05f, 0.5f, 0.15f, 0.05f);
	MyRectangle commandComboBounds = new MyRectangle(0.17f, 0.5f, 0.1f, 0.05f);
	MyRectangle argsLabelBounds = new MyRectangle(0.3f, 0.5f, 0.1f, 0.05f);
	MyRectangle argsGridBounds = new MyRectangle(0.35f, 0.5f, 0.32f, 0.2f);
	
	MyRectangle validateButtonBounds = new MyRectangle(0.2f, 0.85f, 0.1f, 0.05f);
	MyRectangle cancelButtonBounds = new MyRectangle(0.4f, 0.85f, 0.1f, 0.05f);
	
	MyRectangle blocksBounds = new MyRectangle(0.7f, 0.1f, 0.25f, 0.35f);
	MyRectangle expressionsBounds = new MyRectangle(0.7f, 0.55f, 0.25f, 0.35f);
	
	MyComboBox<EnvironmentVariable> variablesComboBox;
	MyComboBox<String> operatorTopComboBox;
	MyComboBox<String> valueComboBox;
	MySlider valueSlider;
	MyButtonFX validateBlockButton;
	
	boolean type = false;
	
	MyComboBox<Evaluable> evaluableRightComboBox;
	MyComboBox<String> operatorBottomComboBox;
	MyComboBox<Evaluable> evaluableLeftComboBox;
	MyButtonFX validateExpressionButton;
	
	MyLabel finalExpressionLabel;
	MyComboBox<Evaluable> finalExpressionComboBox;
	
	MyLabel commandLabel;
	MyComboBox<Command> commandKeyComboBox;
	MyScrollPane argsScrollPane;
	MyGridPane argsGridPane;
	
	MyScrollPane blocksScrollPane;
	MyScrollPane expressionsScrollPane;
	MyGridPane blocksGridPane;
	MyGridPane expressionsGridPane;
	
	MyButtonFX valideButton;
	MyButtonFX cancelButton;
	
	ChangeListener<Object> changeListener = new ChangeListener<Object>() {

		@Override
		public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
			
		}
		
	};
	
	// ========= ATTRIBUTES ========= //
	private ArrayList<EnvironmentVariable> environmentVariables = new ArrayList<EnvironmentVariable>();
	private ArrayList<Evaluable> evaluables = new ArrayList<Evaluable>();
	private ArrayList<Command> commands = new ArrayList<Command>();
	private int nbBlocks = 0, nbExpressions = 0;
	
	private BehavioursContent() {		
		int nbArithOperator = ARITHMETICOPERATOR.values().length;
		ArrayList<String> arithOperators = new ArrayList<String>();
		for(int i = 0; i < nbArithOperator; i++) {
			arithOperators.add(ARITHMETICOPERATOR.values()[i].toString());
		}
		
		int nbBoolOperator = BOOLEANOPERATOR.values().length;
		ArrayList<String> boolOperators = new ArrayList<String>();
		for(int i = 0; i < nbBoolOperator; i++) {
			boolOperators.add(BOOLEANOPERATOR.values()[i].toString());
		}
		
		variablesComboBox = new MyComboBox<EnvironmentVariable>(variableBounds.computeBounds(width, height), environmentVariables);
		variablesComboBox.setConverter(new StringConverter<EnvironmentVariable>() {

			@Override
			public EnvironmentVariable fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(EnvironmentVariable arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getName();
			}
        });
		variablesComboBox.valueProperty().addListener(new ChangeListener<EnvironmentVariable>() {

			@Override
			public void changed(ObservableValue<? extends EnvironmentVariable> arg0, EnvironmentVariable arg1, EnvironmentVariable arg2) {
				if(arg2.getValue() instanceof ContinuousValue) {
					ContinuousValue cv = (ContinuousValue) arg2.getValue();
					type = true;
					getChildren().remove(valueComboBox);
					if(!getChildren().contains(valueSlider)) {
						getChildren().add(valueSlider);
					}
					valueSlider.setMin(cv.getValueMin());
					valueSlider.setMax(cv.getValueMax());
					valueSlider.setBlockIncrement(cv.getPrecision());
					valueSlider.setValue((valueSlider.getMin() + valueSlider.getMax()) / 2);
				} else if (arg2.getValue() instanceof DiscreteValue) {
					DiscreteValue dv = (DiscreteValue) arg2.getValue();
					getChildren().remove(valueSlider);
					if(!getChildren().contains(valueComboBox)) {
						getChildren().add(valueComboBox);
					}
					valueComboBox.getItems().clear();
					valueComboBox.getItems().addAll(dv.getPossibleValues());
					type = false;
				}
				
			}
			
		});
		operatorTopComboBox = new MyComboBox<String>(operatorBoundsTop.computeBounds(width, height),arithOperators);
		operatorTopComboBox.getSelectionModel().selectFirst();
		valueComboBox = new MyComboBox<String>(valueBounds.computeBounds(width, height), null);
		valueSlider = new MySlider(valueBounds.computeBounds(width, height), 0, 1, 0.1);
		
		
		validateBlockButton = new MyButtonFX("Validate", newBlockBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String value = "";
				if(type) {
					value = ((Double)(Math.floor(valueSlider.getValue() * 100) / 100)).toString();
				} else {
					value = valueComboBox.getValue();
				}
				EnvironmentVariable ev = ((EnvironmentVariable)(variablesComboBox.getValue()));
				Block block = new Block(ev, value, operatorTopComboBox.getValue());
				evaluables.add(block);
				MyLabel label = new MyLabel(block.toString());
				label.setPrefWidth(blocksScrollPane.getPrefWidth());
				nbBlocks++;
				if(nbBlocks % 2 == 0) {
					label.setStyle("-fx-background-color: #FFFFFF;");
				}
				blocksGridPane.add(label, 0, nbBlocks);
				finalExpressionComboBox.getItems().add(block);
				evaluableRightComboBox.getItems().add(block);
				evaluableLeftComboBox.getItems().add(block);
			}
		});

		
		evaluableRightComboBox = new MyComboBox<Evaluable>(leftEvaluableBounds.computeBounds(width, height), evaluables);
		
		evaluableRightComboBox.setConverter(new StringConverter<Evaluable>() {

			@Override
			public Evaluable fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Evaluable arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.toString();
			}
        });
		
		operatorBottomComboBox = new MyComboBox<String>(operatorBoundsBottom.computeBounds(width, height), boolOperators);
		operatorBottomComboBox.getSelectionModel().selectFirst();
		evaluableLeftComboBox = new MyComboBox<Evaluable>(rightEvaluableBounds.computeBounds(width, height), evaluables);
		
		evaluableLeftComboBox.setConverter(new StringConverter<Evaluable>() {

			@Override
			public Evaluable fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Evaluable arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.toString();
			}
        });
		
		validateExpressionButton = new MyButtonFX("Validate", expressionBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Evaluable> eval = new ArrayList<Evaluable>();
				eval.add(evaluableLeftComboBox.getValue());
				eval.add(evaluableRightComboBox.getValue());
				Expression expression = new Expression(eval, operatorBottomComboBox.getValue());
				MyLabel label = new MyLabel(expression.toString());
				label.setPrefWidth(blocksScrollPane.getPrefWidth());
				nbExpressions++;
				if(nbExpressions % 2 == 0) {
					label.setStyle("-fx-background-color: #FFFFFF;");
				}
				expressionsGridPane.add(label, 0, nbExpressions);
				evaluables.add(expression);
				finalExpressionComboBox.getItems().add(expression);
				evaluableRightComboBox.getItems().add(expression);
				evaluableLeftComboBox.getItems().add(expression);
			}
		});
		
		blocksGridPane = new MyGridPane(blocksBounds.computeBounds(width, height));
		expressionsGridPane = new MyGridPane(expressionsBounds.computeBounds(width, height));
		blocksScrollPane = new MyScrollPane(blocksBounds.computeBounds(width, height));
		blocksScrollPane.setContent(blocksGridPane);
		expressionsScrollPane = new MyScrollPane(expressionsBounds.computeBounds(width, height));
		expressionsScrollPane.setContent(expressionsGridPane);
		
		finalExpressionLabel = new MyLabel("Final Expression", finalExpressionLabelBounds.computeBounds(width, height));
		finalExpressionComboBox = new MyComboBox<Evaluable>(finalExpressionComboBounds.computeBounds(width, height),new ArrayList<Evaluable>());
		finalExpressionComboBox.setStyle("-fx-background-color: white");
		finalExpressionComboBox.setConverter(new StringConverter<Evaluable>() {

			@Override
			public Evaluable fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Evaluable arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.toString();
			}
        });
		commandLabel = new MyLabel("Output command", commandsLabelBounds.computeBounds(width, height));
		commandKeyComboBox = new MyComboBox<Command>(commandComboBounds.computeBounds(width, height),new ArrayList<Command>());
		commandKeyComboBox.setConverter(new StringConverter<Command>() {

			@Override
			public Command fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Command arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getKey();
			}
        });
		commandKeyComboBox.valueProperty().addListener(new ChangeListener<Command>() {

			@Override
			public void changed(ObservableValue<? extends Command> observable, Command oldValue, Command newValue) {
				argsGridPane.getChildren().clear();
				Command currentCommand = newValue;
            	int j;
            	for (j= 0; j < currentCommand.getCommandValues().size(); j++) {
					CommandValue argument = currentCommand.getCommandValues().get(j);
                    if(argument instanceof DiscreteCommandValue) {
                    	DiscreteCommandValue argumentCasted = (DiscreteCommandValue)argument;    
                    	argsGridPane.add(new MyComboBox<String>(argumentCasted.getPossibleValues()), 0, j + 1);
                    }
                    else {
                    	ContinuousCommandValue argumentCasted = (ContinuousCommandValue)argument;
                    	argsGridPane.add(new MySlider(argumentCasted.getValueMin(), argumentCasted.getValueMax(), argumentCasted.getPrecision()), 0, j+1);
                    }
				}
			}
		});
		commandKeyComboBox.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		argsGridPane = new MyGridPane(argsGridBounds.computeBounds(width, height));
		argsScrollPane = new MyScrollPane(argsGridBounds.computeBounds(width, height));
		argsScrollPane.setContent(argsGridPane);
		
		valideButton = new MyButtonFX("Validate", validateButtonBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(ActionEvent arg0) {
				Evaluable finalEvaluable = finalExpressionComboBox.getValue();
				Command command = commandKeyComboBox.getValue();
				boolean error = false;
				if(finalEvaluable == null) {
					finalExpressionComboBox.setStyle("-fx-background-color: "+GraphicalCharter.RED);
					error = true;
				}
				if (command == null) {
					commandKeyComboBox.setStyle("-fx-background-color: "+GraphicalCharter.RED);
					error = true;
				}
				if (command != null) {
					for(int i = 0; i < command.getCommandValues().size(); i++) {
						Node node = argsGridPane.getChildren().get(i);
						if(node instanceof MyComboBox) {
							if(((MyComboBox<String>) node).getValue() == null) {
								error = true;
								((MyComboBox<String>) node).setStyle("-fx-background-background: " + GraphicalCharter.RED);
							}
						}
					}
				}
				if(error) {
					return;
				}
				finalExpressionComboBox.setStyle("-fx-background-color: white");
				JSONObject request = new JSONObject();
				request.put("recipient", "behaviour");
				request.put("action", "create");
				request.put("evaluable", finalEvaluable.toJson());
				String action = command.getName();
				for(int i = 0; i < argsGridPane.getChildren().size(); i++){
					if(argsGridPane.getChildren().get(i) instanceof MySlider) {
						MySlider slider = (MySlider) argsGridPane.getChildren().get(i);
						action += " " + ((Double)(Math.floor(slider.getValue() * 100) / 100)).toString();
					} else if (argsGridPane.getChildren().get(i) instanceof MyComboBox) {
						MyComboBox<String> comboBox = (MyComboBox<String>) argsGridPane.getChildren().get(i);
						action += " " + comboBox.getValue();
					}
				}
				JSONObject commandJSON = new JSONObject();
				commandJSON.put("id", command.getActuator().getId());
				commandJSON.put("action", action);
				request.put("command", commandJSON);
				System.out.println(request.toString());
				try {
                    ClientFX.client.sendToServer(command.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
			}
		});
		
		cancelButton = new MyButtonFX("Cancel", cancelButtonBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ContentScene.getInstance().changeContent(BehavioursCommandsContent.getInstance());
			}
		});
		
        this.getChildren().add(variablesComboBox);
        this.getChildren().add(operatorTopComboBox);
        this.getChildren().add(valueComboBox);
        this.getChildren().add(validateBlockButton);
        
        this.getChildren().add(evaluableRightComboBox);
        this.getChildren().add(operatorBottomComboBox);
        this.getChildren().add(evaluableLeftComboBox);
        this.getChildren().add(validateExpressionButton);
        
        this.getChildren().add(blocksScrollPane);
        this.getChildren().add(expressionsScrollPane);
        
        this.getChildren().add(finalExpressionLabel);
        this.getChildren().add(finalExpressionComboBox);
        
        this.getChildren().add(commandLabel);
        this.getChildren().add(commandKeyComboBox);
        this.getChildren().add(argsScrollPane);
        
        this.getChildren().add(valideButton);
        this.getChildren().add(cancelButton);
        
		updateData();
	}
	
	public void updateData() {
	    updateEnvironmentVariables();
	    updateCommands();
	}
	
	/**
	 * Ask the server to get all the environment variables.
     * TODO : not sure of the action syntax... (to implement in server.SensorManager)
	 */
	public void updateEnvironmentVariables() {
	    JSONObject getEnvironmentVariables = new JSONObject();
	    getEnvironmentVariables.put("recipient", "sensor");
	    getEnvironmentVariables.put("action", "getEnvironmentVariables");
        try {
            ClientFX.client.sendToServer(getEnvironmentVariables.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void updateCommands() {
		JSONObject getCommands = new JSONObject();
        getCommands.put("recipient", "command");
        getCommands.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getCommands.toString());
        } catch (IOException e) {
            e.printStackTrace();
	    }
	}
	
	public static Content getInstance() {
		if(content == null) {
			content = new BehavioursContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) { 
		if(message instanceof String) {
			System.out.println("Salue " + message);
			try {
				System.out.println(message.toString());
				JSONObject json = new JSONObject((String)message);
				String recipient = json.getString("recipient");
				String action = json.getString("action");
				switch(recipient) {
				case "sensor":
					switch(action) {
					case "getEnvironmentVariables":
                        JSONArray arrArg = json.getJSONArray("environmentVariables");
                        for (int j = 0; j < arrArg.length(); j++){
                            JSONObject current = arrArg.getJSONObject(j);
                            String name = current.getString("name");
                            String description = current.getString("description");
                            int id = current.getInt("id");
                            JSONObject value = current.getJSONObject("value");
                            String type = value.getString("type");
                            if(type.equals(Value.VALUE_TYPE.CONTINUOUS.toString())) {
                            	ContinuousValue cv = new ContinuousValue(value.getDouble("valueMin"), value.getDouble("valueMax"), value.getDouble("precision"));
                            	environmentVariables.add(new EnvironmentVariable(id, name, description, null, cv));
                            } else if (type.equals(Value.VALUE_TYPE.DISCRETE.toString())) {
                            	JSONArray valuesJSON = value.getJSONArray("possibleValues");
                            	ArrayList<String> values = new ArrayList<String>();
                            	for(int i = 0; i < valuesJSON.length(); i++) {
                            		values.add(valuesJSON.getString(i));
                            	}
                            	DiscreteValue dv = new DiscreteValue(1, values);
                            	environmentVariables.add(new EnvironmentVariable(id, name, description, null, dv));
                            }
                            // TODO : decomment 1rst line and delete 2nd one when getAll() functional
                            // behaviours.add(new Behaviour(current.getInt("id"), current.getString("name"), current.getBoolean("isActivated")));
                        }
                        updateEnvironmentVariablesUI();
						break;
					}
					break;
				case "command":
	                    switch (action) {
	                    case "getAll":
	                    	//Parses the commands
	                    	JSONArray commands = json.getJSONArray("commands");
	                    	for (int i = 0; i < commands.length(); i++) {
	                    		JSONObject currentCommand = commands.getJSONObject(i);
								ArrayList<CommandValue> arguments = new ArrayList<CommandValue>();
								try {
		                    		for (int j = 0; j < currentCommand.getJSONArray("commandValue").length(); j++) {
										JSONObject currentArgument = currentCommand.getJSONArray("commandValue").getJSONObject(j);
										if(currentArgument.getString("type").equals("discrete")) {
											ArrayList<String> possibleValues = new ArrayList<String>();
											for (int k = 0; k < currentArgument.getJSONArray("possibleValues").length(); k++) {
												possibleValues.add(currentArgument.getJSONArray("possibleValues").getString(k));
											}
											arguments.add(new DiscreteCommandValue(possibleValues));
										}
										else {
											arguments.add(new ContinuousCommandValue(currentArgument.getFloat("valueMin"),currentArgument.getFloat("valueMax"),currentArgument.getFloat("precision")));
										}
									}
								}
								catch(Exception e) {
									System.out.println("Pas d'arguments pour cette commande !");
								}
	                    		this.commands.add(new Command(currentCommand.getString("name"),currentCommand.getString("description"),currentCommand.getString("key"),arguments,new Actuator(currentCommand.getInt("actuator"))));
							}
	                    	updateCommandUI();
	                        break;
	                    case "create":
	                        
	                        break;
	                    case "update":
	                        
	                        break;
	                    case "delete":
	                       
	                        break;
	                    default:
	                        break;
	                    }
					break;
				default:
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void updateEnvironmentVariablesUI() {
		variablesComboBox.getItems().clear();
		for(int i = 0; i < environmentVariables.size(); i++) {
			variablesComboBox.getItems().add(environmentVariables.get(i));
		}		
	}
	
	private void updateCommandUI() {
		commandKeyComboBox.getItems().clear();
		for(int i = 0; i < commands.size(); i++) {
			commandKeyComboBox.getItems().add(commands.get(i));
		}
	}
	

}
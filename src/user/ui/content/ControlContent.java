package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
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
import user.models.ActuatorCategory;
import user.models.AtomicAction;
import user.models.Command;
import user.models.CommandValue;
import user.models.ComplexAction;
import user.models.ContinuousCommandValue;
import user.models.DiscreteCommandValue;
import user.models.SensorCategory;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MySlider;
import javafx.scene.layout.ColumnConstraints;

public class ControlContent extends Content {

	private static ControlContent content = null;
	
	private ArrayList<Command> commands = new ArrayList<>();
	private ArrayList<ComplexAction> complexAction = new ArrayList<>();
	
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
		
		updateData();
	}

	public static Content getInstance() {
		if(content == null) {
			content = new ControlContent();
		}
		return content;
	}
	
    public void updateData() {
        JSONObject getCommands = new JSONObject();
        getCommands.put("recipient", "command");
        getCommands.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getCommands.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUI() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
            	//Create commmands UI
                for (int i = 0; i < commands.size(); i++) {
                    ColumnConstraints col1 = new ColumnConstraints();
                    col1.setPercentWidth(0.1f * bottomGridPane.getPrefWidth());
                    bottomGridPane.getColumnConstraints().addAll(col1);
                	bottomGridPane.add(new MyLabel(commands.get(i).getName()), 0, i + 1);
                	int j;
                	for (j= 0; j < commands.get(i).getCommandValues().size(); j++) {
						CommandValue argument = commands.get(i).getCommandValues().get(j);
	                    if(argument instanceof DiscreteCommandValue) {
	                    	DiscreteCommandValue argumentCasted = (DiscreteCommandValue)argument;
	                    	/*if(argumentCasted.getPossibleValues().size() < 6) {
	                    		for (int k = 0; k < argumentCasted.getPossibleValues().size(); k++) {
	                    			String currentValue = argumentCasted.getPossibleValues().get(k);
	                    			bottomGridPane.add(new MyButtonFX(commands.get(i).getKey() + " " currentValue, new EventHandler<ActionEvent>() {
	            						
	            						@Override
	            						public void handle(ActionEvent arg0) {
	            							//SEND ACTION
	            						}
	            					}),k + 1,i + 1);
								}
	                    	}
	                    	else {
	                    		
	                    	}	*/     
	                    	bottomGridPane.add(new MyComboBox(argumentCasted.getPossibleValues()),j + 1, i + 1);
	                    }
	                    else {
	                    	ContinuousCommandValue argumentCasted = (ContinuousCommandValue)argument;
	                    	bottomGridPane.add(new MySlider(argumentCasted.getValueMin(), argumentCasted.getValueMax(), argumentCasted.getPrecision()), j + 1, i + 1);
	                    }
					}
                	bottomGridPane.add(new MyButtonFX("Validate", new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent arg0) {
							
						}
					}),j + 1,i + 1);
                }
                //Create complex action UI
                int x = 1;
                int y = 0;
                for (int i = 0; i < complexAction.size(); i++) {
                    ColumnConstraints col1 = new ColumnConstraints();
                    col1.setPercentWidth(0.1f * topGridPane.getPrefWidth());
                    topGridPane.getColumnConstraints().addAll(col1);
					if(y == 5) {
						x++;
						y = 0;
					}
					topGridPane.add(new MyButtonFX(complexAction.get(i).getName(), new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event) {
							
						}
					}),y,x);
					y++;
				}
            }          
        });
    }
	
	@Override
	public void handleMessage(Object message) {
        System.out.println("Received: " + message);
        if(message instanceof String) {
            try {
                System.out.println(message.toString());
                JSONObject json = new JSONObject((String)message);
                if(json.getString("recipient").equals("command")) {
                    String action = json.getString("action");
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
                    		this.commands.add(new Command(currentCommand.getString("name"),currentCommand.getString("description"),currentCommand.getString("key"),arguments));
						}
                    	
                    	//Parses the complex action
                    	JSONArray complexCommands = json.getJSONArray("complexAction");
                    	for (int i = 0; i < complexCommands.length(); i++) {
                    		JSONObject currentComplexCommands = complexCommands.getJSONObject(i);
							ArrayList<AtomicAction> atomicActions = new ArrayList<AtomicAction>();
                    		try {
								for (int j = 0; j < currentComplexCommands.getJSONArray("atomicAction").length(); j++) {
									JSONObject currentAtomicAction = currentComplexCommands.getJSONArray("atomicAction").getJSONObject(j);
									atomicActions.add(new AtomicAction(currentAtomicAction.getString("name"),currentAtomicAction.getString("executable")));
								}
                    		}
                    		catch(Exception e) {
                    			System.out.println("Pas d'atomic action pour cette action complexe !");
                    		}
                    		this.complexAction.add(new ComplexAction(currentComplexCommands.getString("name"),atomicActions));
						}
                    	System.out.println(this.commands);
                    	System.out.println(this.complexAction);
                    	updateUI();
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
}

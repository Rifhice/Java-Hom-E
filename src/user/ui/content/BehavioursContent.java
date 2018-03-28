package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import server.models.environmentVariable.EnvironmentVariable;
import user.ClientFX;
import user.tools.ARITHMETICOPERATOR;
import user.tools.BOOLEANOPERATOR;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
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
	
	MyComboBox variablesComboBox;
	MyComboBox operatorTopComboBox;
	MyComboBox valueComboBox;
	MyButtonFX validateBlockButton;
	
	MyComboBox evaluableRightComboBox;
	MyComboBox operatorBottomComboBox;
	MyComboBox evaluableLeftComboBox;
	MyButtonFX validateExpressionButton;
	
	MyLabel finalExpressionLabel;
	MyComboBox finalExpressionComboBox;
	
	MyLabel commandLabel;
	MyComboBox commandKeyComboBox;
	MyLabel argsLabel;
	MyScrollPane argsScrollPane;
	MyGridPane argsGridPane;
	
	MyScrollPane blocksScrollPane;
	MyScrollPane expressionsScrollPane;
	MyGridPane blocksGridPane;
	MyGridPane expressionsGridPane;
	
	MyButtonFX valideButton;
	MyButtonFX cancelButton;
	
	// ========= ATTRIBUTES ========= //
	private ArrayList<EnvironmentVariable> environmentVariables = new ArrayList<EnvironmentVariable>();
	private ArrayList<String> evaluables = new ArrayList<String>();
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
		
		variablesComboBox = new MyComboBox(variableBounds.computeBounds(width, height),new ArrayList<String>());
		operatorTopComboBox = new MyComboBox(operatorBoundsTop.computeBounds(width, height),arithOperators);
		operatorTopComboBox.getSelectionModel().selectFirst();
		valueComboBox = new MyComboBox(valueBounds.computeBounds(width, height),new ArrayList<String>());
		validateBlockButton = new MyButtonFX("Validate", newBlockBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String evaluable = variablesComboBox.getValue() + " " + operatorTopComboBox.getValue() + " " + valueComboBox.getValue();
				MyLabel label = new MyLabel(evaluable);
				label.setPrefWidth(blocksScrollPane.getPrefWidth());
				nbBlocks++;
				if(nbBlocks % 2 == 0) {
					label.setStyle("-fx-background-color: #FFFFFF;");
				}
				blocksGridPane.add(label, 0, nbBlocks);
				evaluables.add(evaluable);
				evaluableRightComboBox.getItems().add(evaluable);
				evaluableLeftComboBox.getItems().add(evaluable);
			}
		});

		evaluableRightComboBox = new MyComboBox(leftEvaluableBounds.computeBounds(width, height), evaluables);
		operatorBottomComboBox = new MyComboBox(operatorBoundsBottom.computeBounds(width, height), boolOperators);
		operatorBottomComboBox.getSelectionModel().selectFirst();
		evaluableLeftComboBox = new MyComboBox(rightEvaluableBounds.computeBounds(width, height), evaluables);
		validateExpressionButton = new MyButtonFX("Validate", expressionBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String evaluable = evaluableLeftComboBox.getValue() + " " + operatorBottomComboBox.getValue() + " " + evaluableRightComboBox.getValue();
				MyLabel label = new MyLabel(evaluable);
				label.setPrefWidth(blocksScrollPane.getPrefWidth());
				nbExpressions++;
				if(nbExpressions % 2 == 0) {
					label.setStyle("-fx-background-color: #FFFFFF;");
				}
				expressionsGridPane.add(label, 0, nbExpressions);
				evaluables.add(evaluable);
				finalExpressionComboBox.getItems().add(evaluable);
			}
		});
		
		blocksGridPane = new MyGridPane(blocksBounds.computeBounds(width, height));
		expressionsGridPane = new MyGridPane(expressionsBounds.computeBounds(width, height));
		blocksScrollPane = new MyScrollPane(blocksBounds.computeBounds(width, height));
		blocksScrollPane.setContent(blocksGridPane);
		expressionsScrollPane = new MyScrollPane(expressionsBounds.computeBounds(width, height));
		expressionsScrollPane.setContent(expressionsGridPane);
		
		finalExpressionLabel = new MyLabel("Final Expression", finalExpressionLabelBounds.computeBounds(width, height));
		finalExpressionComboBox = new MyComboBox(finalExpressionComboBounds.computeBounds(width, height),new ArrayList<String>());
		
		commandLabel = new MyLabel("Output command", commandsLabelBounds.computeBounds(width, height));
		commandKeyComboBox = new MyComboBox(commandComboBounds.computeBounds(width, height),new ArrayList<String>());
		argsLabel = new MyLabel("Args",argsLabelBounds.computeBounds(width, height));
		
		argsGridPane = new MyGridPane(argsGridBounds.computeBounds(width, height));
		argsGridPane.add(new MyLabel("args test"), 0, 0);
		argsScrollPane = new MyScrollPane(argsGridBounds.computeBounds(width, height));
		argsScrollPane.setContent(argsGridPane);
		
		valideButton = new MyButtonFX("Validate", validateButtonBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

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
        this.getChildren().add(argsLabel);
        this.getChildren().add(argsScrollPane);
        
        this.getChildren().add(valideButton);
        this.getChildren().add(cancelButton);
        
		updateData();
	}
	
	public void updateData() {
	    updateEnvironmentVariables();
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
	
	public static Content getInstance() {
		if(content == null) {
			content = new BehavioursContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		if(message instanceof String) {
			try {
				System.out.println(message.toString());
				JSONObject json = new JSONObject((String)message);
				String recipient = json.getString("recipient");
				String action = json.getString("action");
				switch(recipient) {
				case "sensor":
					switch(action) {
					case "get":
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

	public void updateUI() {
	}
	

}

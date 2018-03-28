package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import server.models.environmentVariable.EnvironmentVariable;
import user.ClientFX;
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
	
	MyRectangle evaluablesBounds = new MyRectangle(0.7f, 0.1f, 0.25f, 0.8f);
	
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
	
	MyScrollPane evaluableScrollPane;
	MyGridPane evaluableGridPane;
	
	MyButtonFX valideButton;
	MyButtonFX cancelButton;
	
	// ========= ATTRIBUTES ========= //
	private ArrayList<EnvironmentVariable> environmentVariables = new ArrayList<EnvironmentVariable>();
	
	private BehavioursContent() {
		variablesComboBox = new MyComboBox(variableBounds.computeBounds(width, height),new ArrayList<String>());
		operatorTopComboBox = new MyComboBox(operatorBoundsTop.computeBounds(width, height),new ArrayList<String>());;
		valueComboBox = new MyComboBox(valueBounds.computeBounds(width, height),new ArrayList<String>());;
		validateBlockButton = new MyButtonFX("Validate", newBlockBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});

		evaluableRightComboBox = new MyComboBox(leftEvaluableBounds.computeBounds(width, height),new ArrayList<String>());
		operatorBottomComboBox = new MyComboBox(operatorBoundsBottom.computeBounds(width, height),new ArrayList<String>());
		evaluableLeftComboBox = new MyComboBox(rightEvaluableBounds.computeBounds(width, height),new ArrayList<String>());
		validateExpressionButton = new MyButtonFX("Validate", expressionBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		evaluableGridPane = new MyGridPane(evaluablesBounds.computeBounds(width, height));
		evaluableGridPane.add(new MyLabel("Evaluable"), 0, 0);
		evaluableScrollPane = new MyScrollPane(evaluablesBounds.computeBounds(width, height));
		evaluableScrollPane.setContent(evaluableGridPane);
		
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
        
        this.getChildren().add(evaluableScrollPane);
        
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
		
	}
	

}

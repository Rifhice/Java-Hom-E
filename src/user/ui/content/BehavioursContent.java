package user.ui.content;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class BehavioursContent extends Content {

	private static BehavioursContent content = null;
	
	MyRectangle variableBounds = new MyRectangle(0.05f, 0.1f, 0.1f, 0.05f);
	MyRectangle operatorBoundsTop = new MyRectangle(0.2f, 0.1f, 0.1f, 0.05f);
	MyRectangle valueBounds = new MyRectangle(0.35f, 0.1f, 0.1f, 0.05f);
	MyRectangle newBlockBounds = new MyRectangle(0.5f, 0.1f, 0.1f, 0.05f);
	
	MyRectangle leftEvaluableBounds = new MyRectangle(0.05f, 0.2f, 0.1f, 0.05f);
	MyRectangle operatorBoundsBottom = new MyRectangle(0.2f, 0.2f, 0.1f, 0.05f);
	MyRectangle rightEvaluableBounds = new MyRectangle(0.35f, 0.2f, 0.1f, 0.05f);
	MyRectangle expressionBounds = new MyRectangle(0.5f, 0.2f, 0.1f, 0.05f);

	MyRectangle finalExpressionLabelBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle finalExpressionComboBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle commandsLabelBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle commandComboBounds = new MyRectangle(0f, 0f, 0f, 0f);
	
	MyRectangle validateButtonBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle cancelButtonBounds = new MyRectangle(0f, 0f, 0f, 0f);
	
	MyComboBox variablesComboBox;
	MyComboBox operatorTopComboBox;
	MyComboBox valueComboBox;
	MyButtonFX validateBlockButton;
	
	MyComboBox evaluableRightComboBox;
	MyComboBox operatorBottomComboBox;
	MyComboBox evaluableLeftComboBox;
	MyButtonFX validateExpressionButton;
	
	MyComboBox finalExpressionComboBox;
	
	MyComboBox commandKeyComboBox;
	MyGridPane commandGridPane;
	
	MyScrollPane evaluableScrollPane;
	MyGridPane evaluableGridPane;
	
	private BehavioursContent() {
		variablesComboBox = new MyComboBox(variableBounds.computeBounds(width, height),new ArrayList<String>());
		operatorTopComboBox = new MyComboBox(operatorBoundsTop.computeBounds(width, height),new ArrayList<String>());;
		valueComboBox = new MyComboBox(valueBounds.computeBounds(width, height),new ArrayList<String>());;
		validateBlockButton = new MyButtonFX("Validate", newBlockBounds.computeBounds(width, height), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		

        this.getChildren().add(variablesComboBox);
        this.getChildren().add(operatorTopComboBox);
        this.getChildren().add(valueComboBox);
        this.getChildren().add(validateBlockButton);
		UpdateData();
	}
	
	public void UpdateData() {
		
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

package user.ui.content;

import user.ui.componentJavaFX.MyComboBox;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class BehavioursContent extends Content {

	private static BehavioursContent content = null;
	
	MyRectangle variableBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle operatorBoundsTop = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle valueBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle newBlockBounds = new MyRectangle(0f, 0f, 0f, 0f);
	
	MyRectangle leftEvaluableBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle rightEvaluableBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle operatorBoundsBottom = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle expressionBounds = new MyRectangle(0f, 0f, 0f, 0f);

	MyRectangle finalExpressionLabelBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle finalExpressionComboBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle commandsLabelBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle commandComboBounds = new MyRectangle(0f, 0f, 0f, 0f);
	
	MyRectangle validateButtonBounds = new MyRectangle(0f, 0f, 0f, 0f);
	MyRectangle cancelButtonBounds = new MyRectangle(0f, 0f, 0f, 0f);
	
	MyComboBox variablesComboBox;
	MyComboBox valueComboBox;
	MyComboBox operatorComboBox;
	MyComboBox evaluableComboBox;
	
	MyScrollPane argumentsScrollPaneBounds;
	MyGridPane argumentsGridPane;
	
	private BehavioursContent() {
		
		UpdateData();
	}
	
	public void UpdateData() {
		
	}
	
	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new BehavioursContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		// TODO Auto-generated method stub
		
	}
	

}

package user.ui.content;

import javafx.scene.control.Label;
import user.ui.componentJavaFX.MyRectangle;

public class BehavioursCommandsContent extends Content {

    private MyRectangle behavioursListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
    private MyRectangle selectedBehaviourBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
	private static BehavioursCommandsContent content = null;

	private BehavioursCommandsContent() {
		this.getChildren().add(new Label("Behaviours"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new BehavioursCommandsContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

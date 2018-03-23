package user.ui.content;

import javafx.scene.control.Label;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;

public class ActuatorContent extends Content {

	private static ActuatorContent content = null;
	
	private MyRectangle actuatorsListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
	private MyRectangle selectedActuatorBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle latestActionsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);


	private ActuatorContent() {
	    MyPane actuatorsListPane = new MyPane(actuatorsListBounds.computeBounds(width, height));
	    actuatorsListPane.setStyle("-fx-background-color: rgb(255,0,0)");
	    MyPane selectedActuatorPane = new MyPane(selectedActuatorBounds.computeBounds(width, height));
	    selectedActuatorPane.setStyle("-fx-background-color: rgb(0,0,255)");
	    MyPane latestActionsPane = new MyPane(latestActionsBounds.computeBounds(width, height));
	    latestActionsPane.setStyle("-fx-background-color: rgb(0,255,0)");

	    this.getChildren().add(actuatorsListPane);
	    this.getChildren().add(selectedActuatorPane);
	    this.getChildren().add(latestActionsPane);
	    actuatorsListPane.getChildren().add(new Label("Actuators List"));
	    selectedActuatorPane.getChildren().add(new Label("Actuator selected"));
	    latestActionsPane.getChildren().add(new Label("Latest Actions"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new ActuatorContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

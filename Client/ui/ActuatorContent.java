package ui;

import javafx.scene.control.Label;

public class ActuatorContent extends Content {

	private static ActuatorContent content = null;

	private ActuatorContent() {
		this.getChildren().add(new Label("Actuator"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new ActuatorContent();
		}
		return content;
	}
}

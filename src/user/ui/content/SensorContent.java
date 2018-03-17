package user.ui.content;

import javafx.scene.control.Label;

public class SensorContent extends Content {

	private static SensorContent content = null;

	private SensorContent() {
		this.getChildren().add(new Label("Sensors"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new SensorContent();
		}
		return content;
	}
}

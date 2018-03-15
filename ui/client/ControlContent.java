package ui.client;

import javafx.scene.control.Label;

public class ControlContent extends Content {

	private static ControlContent content = null;

	private ControlContent() {
		this.getChildren().add(new Label("Control"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new ControlContent();
		}
		return content;
	}
}

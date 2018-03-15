package ui.client;

import javafx.scene.control.Label;

public class BehavioursContent extends Content {

	private static BehavioursContent content = null;

	private BehavioursContent() {
		this.getChildren().add(new Label("Behaviours"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new BehavioursContent();
		}
		return content;
	}
}

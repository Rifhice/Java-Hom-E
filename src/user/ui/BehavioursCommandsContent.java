package user.ui;

import javafx.scene.control.Label;

public class BehavioursCommandsContent extends Content {

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
}

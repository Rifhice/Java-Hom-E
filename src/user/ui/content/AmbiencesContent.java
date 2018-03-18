package user.ui.content;

import javafx.scene.control.Label;

public class AmbiencesContent extends Content {

	private static AmbiencesContent content = null;

	private AmbiencesContent() {
		this.getChildren().add(new Label("Ambience"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new AmbiencesContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

package user.ui.content;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import user.ui.componentJavaFX.MyTextDialog;

public class HomeContent extends Content {

	private static HomeContent content = null;

	private HomeContent() {
		this.getChildren().add(new Label("Home"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new HomeContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

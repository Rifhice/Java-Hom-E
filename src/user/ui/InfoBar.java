package user.ui;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class InfoBar extends Pane{

	public InfoBar() {
		getChildren().add(new Button("Lol"));
		setMinWidth(ClientFX.width * ContentScene.infoBarWidthRatio);
		setPrefHeight(ClientFX.height * ContentScene.infoBarHeightRatio);
	}
	
}

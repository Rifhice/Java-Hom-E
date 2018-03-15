package ui.client;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class InfoBar extends Pane{

	public InfoBar() {
		getChildren().add(new Button("Lol"));
		setMinWidth(ClientFX.width * ContentScene.infoBarWidthRatio);
		setPrefHeight(ClientFX.height * ContentScene.infoBarHeightRatio);
	}
	
}

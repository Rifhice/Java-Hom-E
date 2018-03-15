package ui.client;

import javafx.scene.layout.Pane;

public abstract class Content extends Pane {

	public Content() {
		this.setPrefWidth(ContentScene.contentWidthRatio * ClientFX.width);
		this.setPrefHeight(ContentScene.contentHeightRatio * ClientFX.height);
	}
	

}

package ui.client;

import javafx.scene.layout.Pane;

public abstract class Content extends Pane {

	public Content() {
		this.setWidth(ContentScene.contentWidthRatio * ClientFX.width);
		this.setHeight(ContentScene.contentHeightRatio * ClientFX.height);
	}
	

}

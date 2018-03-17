package user.ui.content;

import javafx.scene.layout.Pane;
import user.ui.ClientFX;
import user.ui.scene.ContentScene;

public abstract class Content extends Pane {

	public Content() {
		this.setPrefWidth(ContentScene.contentWidthRatio * ClientFX.width);
		this.setPrefHeight(ContentScene.contentHeightRatio * ClientFX.height);
	}
	

}

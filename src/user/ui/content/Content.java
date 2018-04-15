package user.ui.content;

import javafx.scene.layout.Pane;
import user.ClientFX;
import user.ui.scene.ContentScene;

public abstract class Content extends Pane {

	protected double width;
	protected double height;
	
	public Content() {
		this.width = ContentScene.contentWidthRatio * ClientFX.width;
		this.height = ContentScene.contentHeightRatio * ClientFX.height;
		this.setPrefWidth(width);
		this.setPrefHeight(height);
	}
	
	public abstract void handleMessage(Object message);
	public abstract void updateData();
	
}

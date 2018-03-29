package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.scene.image.ImageView;

import javafx.scene.image.Image;

public class MyImage extends ImageView {

	public MyImage(String path, Rectangle2D.Float bounds, boolean keepRatio) {
		this.setImage(new Image("file:"+path));
		this.setLayoutX(bounds.getX());
		this.setLayoutY(bounds.getY());
		this.setPreserveRatio(keepRatio);
		this.setFitHeight(bounds.getHeight());
		if(!this.isPreserveRatio()) {
			this.setFitWidth(bounds.getWidth());
		}
	}
}

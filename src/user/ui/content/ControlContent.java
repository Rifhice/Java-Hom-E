package user.ui.content;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class ControlContent extends Content {

	private static ControlContent content = null;
	private MyRectangle topPaneBounds = new MyRectangle(0f,0f,1f,0.3f);
	private MyRectangle composedCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);
	private MyRectangle atomicCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);

	private ControlContent() {
		MyPane topPane = new MyPane(topPaneBounds.computeBounds(width, height));
		topPane.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));
		MyLabel label = new MyLabel("Composed Commands", composedCommandBounds.computeBounds(topPane.getPrefWidth(), topPane.getPrefHeight()),2f);
		topPane.getChildren().add(label);
		this.getChildren().add(topPane);
		
	}

	public static Content getInstance() {
		if(content == null) {
			content = new ControlContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

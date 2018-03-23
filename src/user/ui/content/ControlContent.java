package user.ui.content;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class ControlContent extends Content {

	private static ControlContent content = null;
	private MyGridPane topGridPane;
	private MyGridPane bottomGridPane;
	
	private MyRectangle bottomPaneBounds = new MyRectangle(0f,0.3f,1f,0.7f);
	private MyRectangle topPaneBounds = new MyRectangle(0f,0f,1f,0.3f);
	
	private MyRectangle composedCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);
	private MyRectangle atomicCommandBounds = new MyRectangle(0.1f,0.1f,1f,0.1f);

	private ControlContent() {
		topGridPane  = new MyGridPane(topPaneBounds.computeBounds(width, height));
		MyLabel label = new MyLabel("Composed Commands", composedCommandBounds.computeBounds(topGridPane.getPrefWidth(), topGridPane.getPrefHeight()),2f);
		topGridPane.add(label, 0, 0);

        MyScrollPane composedList = new MyScrollPane(topPaneBounds.computeBounds(width,height));
        composedList.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));

        composedList.setContent(topGridPane);
        
        
        bottomGridPane  = new MyGridPane(bottomPaneBounds.computeBounds(width, height));
		MyLabel label2 = new MyLabel("Atomic Commands", bottomPaneBounds.computeBounds(width, height),2f);
		bottomGridPane.add(label, 0, 0);

        MyScrollPane atomicList = new MyScrollPane(atomicCommandBounds.computeBounds(width,height));
        atomicList.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));

        atomicList.setContent(bottomGridPane);
        
		this.getChildren().add(composedList);
		this.getChildren().add(atomicList);
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

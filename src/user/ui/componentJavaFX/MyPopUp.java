package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import user.tools.GraphicalCharter;
import javafx.scene.Node;

public class MyPopUp extends Popup{

	public MyPopUp(Rectangle2D.Float bounds) {
	    setX(bounds.x); 
	    setY(bounds.y);
	    getContent().addAll(new Rectangle(bounds.getWidth(), bounds.getHeight(), Color.web(GraphicalCharter.LIGHT_GRAY)));
	}
	
	public void addNode(Node node) {
		getContent().add(node);
	}
	
}

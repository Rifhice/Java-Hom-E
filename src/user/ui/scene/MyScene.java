package user.ui.scene;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public abstract class MyScene extends Scene{

	protected String title;
	
	protected Group root;
	
	protected int width;
	protected int height;
	
	public MyScene(Group root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		this.root = root;
		this.width = (int) width;
		this.height = (int) height;
	}
	public String getTitle() {
		return title;
	}
	public abstract void handleMessage(Object msg);
	public abstract void display(String message);
	
}

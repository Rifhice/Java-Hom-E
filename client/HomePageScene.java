package client;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class HomePageScene extends MyScene{
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public HomePageScene(Group root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		this.title = "Hom-E";
		
		root.getChildren().add(new Label("Wow you'r connected !"));
		System.out.println(ClientFX.token);
	}

	// ================= //
    // ==== METHODS ==== //
    // ================= //
	@Override
	public void handleMessage(Object msg) {
		
	}

	@Override
	public void display(String message) {
		System.out.println(message);
	}

}

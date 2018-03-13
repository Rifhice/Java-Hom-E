package ui.client;

import java.awt.event.ActionEvent;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import ui.delegate.MenuDelegate;

public class ContentScene extends MyScene implements MenuDelegate {
	
	private Menu menu;
	private Content content;
	private FlowPane mainPane;

	public static double menuWidthRatio = 0.2;
	public static double menuHeightRatio = 1;
	public static double contentWidthRatio = 0.8;
	public static double contentHeightRatio = 1;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public ContentScene(Group root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		mainPane = new FlowPane();
		this.menu = new Menu(this);
		this.content = HomeContent.getInstance();
		mainPane.setMaxWidth(ClientFX.screenSize.getWidth());
		mainPane.setMaxHeight(ClientFX.screenSize.getWidth());
		mainPane.getChildren().add(this.menu);
		mainPane.getChildren().add(this.content);
		root.getChildren().add(mainPane);
		this.title = "Hom-E";
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

	@Override
	public void chanteCurrentContent(CONTENT content) {
		// TODO Auto-generated method stub
		switch(content) {
			case HOME:
				this.changeContent(HomeContent.getInstance());
				break;
			case BEHAVIOURS:
				this.changeContent(BehavioursContent.getInstance());
				break;
			default:
				break;
		}
	}
	
	private void changeContent(Content content) {
		mainPane.getChildren().remove(this.content);
		this.content = content;
		mainPane.getChildren().add(this.content);
	}
	
}

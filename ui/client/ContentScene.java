package ui.client;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ui.delegate.MenuDelegate;

public class ContentScene extends MyScene implements MenuDelegate {
	
	private Menu menu;
	private Content content;
	private Pane infoBar;
	private BorderPane scenePane;

	public final static double menuWidthRatio = 0.2;
	public final static double menuHeightRatio = 0.9;
	public final static double contentWidthRatio = 0.8;
	public final static double contentHeightRatio = 0.9;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public ContentScene(Group root, double width, double height, Paint fill) {
		super(root, width, height, fill);
		scenePane = new BorderPane();
		scenePane.setMinSize(ClientFX.width, ClientFX.height);
		this.infoBar = new Pane();
		this.infoBar.setPrefHeight(ClientFX.height * 0.1);
		this.infoBar.setMinWidth(ClientFX.width);
		this.infoBar.getChildren().add(new Button("Lol"));
		this.infoBar.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.infoBar.setMaxWidth(ClientFX.width);
		this.infoBar.setMaxWidth(ClientFX.height * (1 - ContentScene.contentHeightRatio));
		this.menu = new Menu(this);
		this.menu.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.content = HomeContent.getInstance();
		this.content.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		scenePane.setLeft(this.menu);
		scenePane.setCenter(this.content);
		scenePane.setTop(infoBar);
		root.getChildren().add(scenePane);
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
		this.content = content;
		scenePane.setCenter(this.content);
	}
	
}

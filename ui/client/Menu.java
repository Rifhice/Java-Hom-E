package ui.client;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import ui.delegate.MenuDelegate;

public class Menu extends GridPane {

	private MenuDelegate menuDelegate;
	
	
	public Menu(MenuDelegate menuDelegate) {
		this.setPrefWidth(ContentScene.menuWidthRatio * ClientFX.width);
		this.setPrefHeight(ContentScene.menuHeightRatio * ClientFX.height);
		this.menuDelegate = menuDelegate;
		Button home = new Button("Home");
		home.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				menuDelegate.chanteCurrentContent(MenuDelegate.CONTENT.HOME);
			}
			
		});
		Button behaviours = new Button("Behaviours");
		behaviours.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				menuDelegate.chanteCurrentContent(MenuDelegate.CONTENT.BEHAVIOURS);
			}
			
		});
		this.add(home, 0, 0);
		this.add(behaviours, 0, 1);
		this.add(new Button("Button"), 0, 2);
		this.add(new Button("Button"), 0, 3);
	}
	
}

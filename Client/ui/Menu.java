package ui;


import ui.componentJavaFX.MyButtonFX;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import ui.delegate.MenuDelegate;
import ui.delegate.MenuDelegate.CONTENT;

public class Menu extends GridPane {
	
	private MenuDelegate menuDelegate;
	
	private double width;
	private double height;
	
	public Menu(MenuDelegate menuDelegate) {
		this.width = ContentScene.menuWidthRatio * ClientFX.width;
		this.height = ContentScene.menuHeightRatio * ClientFX.height;
		
		this.setPrefWidth(width);
		this.setPrefHeight(height);
		
		this.menuDelegate = menuDelegate;
		
		int nbContent = MenuDelegate.CONTENT.values().length;
		
		for (int i = 0; i < nbContent; i++) {
			CONTENT current = MenuDelegate.CONTENT.values()[i];
			this.add(new MyButtonFX(current.toString(),width,height / nbContent,new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					menuDelegate.chanteCurrentContent(current);
				}
				
			}), 0, i);
		}
	}
	
}

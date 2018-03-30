package user.ui;


import user.ClientFX;
import user.ui.componentJavaFX.MyButtonFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import user.ui.delegate.MenuDelegate;
import user.ui.delegate.MenuDelegate.CONTENT;
import user.ui.scene.ContentScene;

public class Menu extends GridPane {
	
	private static Menu instance = null;
	
	private MenuDelegate menuDelegate;
	
	private double width;
	private double height;
	
	private Menu(MenuDelegate menuDelegate) {
		this.width = ContentScene.menuWidthRatio * ClientFX.width;
		this.height = ContentScene.menuHeightRatio * ClientFX.height;
		
		this.setPrefWidth(width);
		this.setPrefHeight(height);
		
		this.menuDelegate = menuDelegate;
		
		int nbContent = MenuDelegate.CONTENT.values().length;
		
		for (int i = 0; i < nbContent; i++) {
			CONTENT current = MenuDelegate.CONTENT.values()[i];
			MyButtonFX button = new MyButtonFX(current.toString(),width,height / nbContent,new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					menuDelegate.chanteCurrentContent(current);
				}
			});
			this.add(button, 0, i);
		}
	}
	
	public void changeCurrentContent(CONTENT current) {
		menuDelegate.chanteCurrentContent(current);
	}
	
	public static Menu getInstance(MenuDelegate delegate) {
		if(instance == null) {
			if(delegate != null) {
				instance = new Menu(delegate);
			}
		}
		return instance;
	}
	
}

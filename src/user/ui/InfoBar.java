package user.ui;

import java.text.SimpleDateFormat;
import java.util.Date;import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import user.ClientFX;
import user.ui.componentJavaFX.MyButtonImage;
import user.ui.componentJavaFX.MyImage;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.scene.ContentScene;

public class InfoBar extends Pane {


	private double width = ClientFX.width * ContentScene.infoBarWidthRatio;
	private double height = ClientFX.height * ContentScene.infoBarHeightRatio;
	
	private MyRectangle logoBounds = new MyRectangle(0.01f, 0.1f, 0.2f, 0.8f);
	private MyRectangle dateBounds = new MyRectangle(0.5f, 0.5f, 0.3f, 0.5f);
	private MyRectangle connectedBounds = new MyRectangle(0.8f, 0.5f, 0.15f, 0.5f);
	private MyRectangle deconnectionBounds = new MyRectangle(0.95f, 0.2f, 0.03f, 0.5f);
	
	public InfoBar() {
		this.setMaxWidth(width);
		this.setMaxHeight(height);
		this.setWidth(width);
		this.setHeight(height);
		this.setMinHeight(height);
		this.setMinWidth(width);
		
		MyImage logo = new MyImage("asset/images/logo.png", logoBounds.computeBounds(width, height), true);
		getChildren().add(logo);
		
		
		String date = new SimpleDateFormat("dd-MM-yyyy : hh:mm").format(new Date());
		MyLabel label = new MyLabel(date, dateBounds.computeBounds(width, height), true);
		getChildren().add(label); 
		
		String owner = "Personne connectée: Owner";
		MyLabel labelConnected = new MyLabel(owner, connectedBounds.computeBounds(width, height), true);
		labelConnected.setAlignment(Pos.CENTER_RIGHT);
		getChildren().add(labelConnected);
		
		MyButtonImage deconnectionButton = new MyButtonImage(new Image("file:asset/images/deconnection.png"), deconnectionBounds.computeBounds(width, height), null);
		getChildren().add(deconnectionButton);
		
	}
}

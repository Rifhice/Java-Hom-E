package ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import user.ui.ClientFX;
import user.ui.ContentScene;

public class InfoBar extends GridPane{

	public InfoBar() {
		double width = ClientFX.width * ContentScene.infoBarWidthRatio;
		setMinWidth(width);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(width/3);
		getColumnConstraints().add(column1);
		getColumnConstraints().add(column1);
		getColumnConstraints().add(column1);
		setPrefHeight(ClientFX.height * ContentScene.infoBarHeightRatio);
		ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("..\\logo.png")));
		logo.setFitHeight(ClientFX.height * ContentScene.infoBarHeightRatio);
		logo.setFitWidth(ClientFX.height * ContentScene.infoBarHeightRatio);
		add(logo, 0, 0, 1, 2); 
		
		String date = new SimpleDateFormat("dd-MM-yyyy : hh:mm").format(new Date());
		Label label = new Label(date);
		add(label, 1, 0, 1, 2); 
		Label nom = new Label("Owner");
		BorderPane bp = new BorderPane();
		bp.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
		bp.setLeft(nom);
		BorderPane.setAlignment(nom, Pos.CENTER);
		Button disconnect = new Button("Disconnect");
		bp.setRight(disconnect);
		BorderPane.setAlignment(disconnect, Pos.CENTER);
		add(bp, 2, 0, 1, 2);
		
		
	}
}

package user.ui;

import javafx.geometry.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import user.ui.scene.ContentScene;

public class InfoBar extends GridPane{


	private double width = ClientFX.width * ContentScene.infoBarWidthRatio;
	private double height = ClientFX.height * ContentScene.infoBarHeightRatio;
	
	public InfoBar() {
		
		ColumnConstraints column = new ColumnConstraints();
		RowConstraints row = new RowConstraints();
		row.setPercentHeight(2 * height);
		column.setPercentWidth(width/3);
		getColumnConstraints().add(column);
		getColumnConstraints().add(column);
		getColumnConstraints().add(column);
		getRowConstraints().add(row);
		
		
		setMinWidth(width);
		setPrefHeight(ClientFX.height * ContentScene.infoBarHeightRatio);
		
        final ImageView imv = new ImageView();
		try {
	        imv.setImage(
	        		new Image(
	        				new FileInputStream(
	        						new File("asset/images/logo.png"))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		imv.setFitWidth(width * 0.05);
		imv.setFitHeight(height * 0.95);
		add(imv,0,0,1,2);
		
		
		String date = new SimpleDateFormat("dd-MM-yyyy : hh:mm").format(new Date());
		Label label = new Label(date);
		add(label, 1, 0, 1, 2); 
		GridPane.setHalignment(label, HPos.CENTER);
		GridPane.setValignment(label, VPos.CENTER);
		
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

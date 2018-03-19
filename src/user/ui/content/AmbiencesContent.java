package user.ui.content;

import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MyTextFieldFX;

public class AmbiencesContent extends Content {

	private static AmbiencesContent content = null;

	private MyRectangle newAmbienceBounds = new MyRectangle(0f, 0F, 0.6f, 0.15f);
	private MyRectangle ambiencesBounds = new MyRectangle(0.6f, 0F, 0.4f, 1f);
	private MyRectangle newAmbienceLabelBounds = new MyRectangle(0.17f, 0.35f, 0.3f, 0.3f);
	private MyRectangle newAmbienceTextFieldBounds = new MyRectangle(0.55f, 0.4f, 0.3f, 0.2f);
	private MyRectangle newAmbienceButtonBounds = new MyRectangle(0.9f, 0.45f, 0.1f, 0.1f);
	

	private MyRectangle behavioursNotChosenBounds = new MyRectangle(0f, 0.15F, 0.3f, 0.85f);
	private MyRectangle behavioursChosenBounds = new MyRectangle(0.3f, 0.15F, 0.3f, 0.85f);
	
	private AmbiencesContent() {
		MyPane newAmbiencePane = new MyPane(newAmbienceBounds.computeBounds(width, height));
		
		MyLabel newAmbienceLabel = new MyLabel("Nouvelle ambiance: ", newAmbienceLabelBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()));
		newAmbienceLabel.setFont(Font.font(null, FontWeight.BOLD, 20));
		MyTextFieldFX newAmbienceName = new MyTextFieldFX("Nom de l'ambiance", newAmbienceTextFieldBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()));
		MyButtonFX newAmbienceButton = new MyButtonFX(new Image("file:asset/images/check.png"), newAmbienceButtonBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		MyScrollPane ambiencesScrollPane = new MyScrollPane(ambiencesBounds.computeBounds(width, height));
		ambiencesScrollPane.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_GRAY), CornerRadii.EMPTY, Insets.EMPTY)));
		
		MyScrollPane behavioursNotChosenScrollPane = new MyScrollPane(behavioursNotChosenBounds.computeBounds(width, height));
		behavioursNotChosenScrollPane.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.RED), CornerRadii.EMPTY, Insets.EMPTY)));

		MyScrollPane behavioursChosenScrollPane = new MyScrollPane(behavioursChosenBounds.computeBounds(width, height));
		behavioursChosenScrollPane.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.DEEP_BLUE), CornerRadii.EMPTY, Insets.EMPTY)));
		
		GridPane notChosenBehavioursList = new GridPane();
		GridPane chosenBehavioursList = new GridPane();
		
		this.getChildren().add(newAmbiencePane);
		newAmbiencePane.getChildren().add(newAmbienceLabel);
		newAmbiencePane.getChildren().add(newAmbienceName);
		newAmbiencePane.getChildren().add(newAmbienceButton);
		this.getChildren().add(ambiencesScrollPane);
		this.getChildren().add(behavioursNotChosenScrollPane);
		this.getChildren().add(behavioursChosenScrollPane);
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new AmbiencesContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

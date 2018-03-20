package user.ui.content;

import javafx.scene.image.Image;
import javafx.event.EventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import user.ClientFX;
import user.models.ActuatorCategory;
import user.models.Ambience;
import user.models.Behaviour;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.BehaviourCell;
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
	private MyRectangle newAmbienceButtonBounds = new MyRectangle(0.9f, 0.35f, 0.05f, 0.05f);
	

	private MyRectangle behavioursNotChosenBounds = new MyRectangle(0f, 0.15F, 0.3f, 0.85f);
	private MyRectangle behavioursChosenBounds = new MyRectangle(0.3f, 0.15F, 0.3f, 0.85f);

	GridPane notChosenBehavioursList = new GridPane();
	GridPane chosenBehavioursList = new GridPane();
	GridPane ambiencesList = new GridPane();
	
	private List<Behaviour> notSelectedBehaviours;
	private List<Behaviour> selectedBehaviours;
	private List<Ambience> ambiences;
	
	private int NB_OF_BEHAVIOURS_DISPLAYED = 10;
	private int NB_OF_AMBIENCES_DISPLAYED = 8;
	
	private int currentAmbiencesSelected = -1;
	
	private AmbiencesContent() {
		MyPane newAmbiencePane = new MyPane(newAmbienceBounds.computeBounds(width, height));
		
		MyLabel newAmbienceLabel = new MyLabel("Nouvelle ambiance: ", newAmbienceLabelBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()), 1f);
		MyTextFieldFX newAmbienceName = new MyTextFieldFX("Nom de l'ambiance", newAmbienceTextFieldBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()));
		Image image = new Image("file:asset/images/check.png");
		MyButtonFX newAmbienceButton = new MyButtonFX(image, newAmbienceButtonBounds.computeBounds(newAmbiencePane.getPrefWidth(), newAmbiencePane.getPrefHeight()), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		MyScrollPane ambiencesScrollPane = new MyScrollPane(ambiencesBounds.computeBounds(width, height));
		
		MyScrollPane behavioursNotChosenScrollPane = new MyScrollPane(behavioursNotChosenBounds.computeBounds(width, height));

		MyScrollPane behavioursChosenScrollPane = new MyScrollPane(behavioursChosenBounds.computeBounds(width, height));
		
		this.getChildren().add(newAmbiencePane);
		newAmbiencePane.getChildren().add(newAmbienceLabel);
		newAmbiencePane.getChildren().add(newAmbienceName);
		newAmbiencePane.getChildren().add(newAmbienceButton);
		this.getChildren().add(ambiencesScrollPane);
		this.getChildren().add(behavioursNotChosenScrollPane);
		this.getChildren().add(behavioursChosenScrollPane);
		notSelectedBehaviours = new ArrayList<Behaviour>();
		notChosenBehavioursList.setPrefWidth(behavioursNotChosenScrollPane.getPrefWidth());
		notChosenBehavioursList.setPrefHeight(behavioursNotChosenScrollPane.getPrefHeight());
		behavioursNotChosenScrollPane.setContent(notChosenBehavioursList);
		chosenBehavioursList.setPrefWidth(behavioursChosenScrollPane.getPrefWidth());
		chosenBehavioursList.setPrefHeight(behavioursChosenScrollPane.getPrefHeight());
		behavioursChosenScrollPane.setContent(chosenBehavioursList);
		ambiencesList.setPrefWidth(behavioursChosenScrollPane.getPrefWidth());
		ambiencesList.setPrefHeight(behavioursChosenScrollPane.getPrefHeight());
		ambiencesScrollPane.setContent(ambiencesList);
		this.updateAmbiences();
		this.updateBehaviours();
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new AmbiencesContent();
		}
		return content;
	}
	
	public void updateBehaviours() {
		JSONObject getActuator = new JSONObject();
		getActuator.put("recipient", "behaviour");
		getActuator.put("action", "getAll");
		
		try {
			ClientFX.client.sendToServer(getActuator.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateAmbiences() {
		JSONObject getActuator = new JSONObject();
		getActuator.put("recipient", "ambience");
		getActuator.put("action", "getAll");
		
		try {
			ClientFX.client.sendToServer(getActuator.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleMessage(Object message) {
		if(message instanceof String) {
			try {
				System.out.println(message.toString());
				JSONObject json = new JSONObject((String)message);
				String recipient = json.getString("recipient");
				String action;
				switch(recipient) {
				case "behaviour":
					action = json.getString("action");
					switch (action) {
					case "getAll":
						this.notSelectedBehaviours = new ArrayList<Behaviour>();
						JSONArray arrArg = json.getJSONArray("behaviours");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							this.notSelectedBehaviours.add(new Behaviour(current.getInt("id"), current.getString("name")));
						}
						updateBehavioursUI();
						break;
					}
					break;
				case "ambience":
					action = json.getString("action");
					switch (action) {
					case "getAll":
						this.ambiences = new ArrayList<Ambience>();
						JSONArray arrArg = json.getJSONArray("ambiences");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							JSONArray arrBehav = current.getJSONArray("behaviours");
							List<Integer> behav = new ArrayList<Integer>();
							for(int k = 0; k < arrBehav.length(); k++) {
								behav.add(arrBehav.getJSONObject(k).getInt("id"));
							}
							this.ambiences.add(new Ambience(current.getInt("id"), current.getString("name"), behav));
						}
						updateAmbienceUI();
						break;
					}
					break;
				}
			} catch(Exception e) {
				
			}
		}
	}


	private void changeBehaviourState(int pressedButton) {
		// TODO Auto-generated method stub
		BehaviourCell behaviourCell = null;
		for (int i = 0; i < notChosenBehavioursList.getChildren().size(); i++) {
			BehaviourCell temp = (BehaviourCell)notChosenBehavioursList.getChildren().get(i);
			if(temp.getCellId() == pressedButton) {
				behaviourCell = temp;
				notChosenBehavioursList.getChildren().remove(behaviourCell);
				chosenBehavioursList.getChildren().add(behaviourCell);
			}
		}
		if(behaviourCell == null) {
			for (int i = 0; i < chosenBehavioursList.getChildren().size(); i++) {
				BehaviourCell temp = (BehaviourCell)chosenBehavioursList.getChildren().get(i);
				if(temp.getCellId() == pressedButton) {
					behaviourCell = temp;
					chosenBehavioursList.getChildren().remove(behaviourCell);
					notChosenBehavioursList.getChildren().add(behaviourCell);
				}
			}
		}
		behaviourCell.changeState();
	}

	private void updateBehavioursUI() {
		if(this.notSelectedBehaviours.size() > 0) {
			
             Platform.runLater(new Runnable() {
                 @Override public void run() {
                	notChosenBehavioursList.getChildren().clear();
         			for (int i = 0; i < notSelectedBehaviours.size(); i++) {
         				notChosenBehavioursList.add(new BehaviourCell(notSelectedBehaviours.get(i) ,notChosenBehavioursList.getPrefWidth(),notChosenBehavioursList.getPrefHeight() / NB_OF_BEHAVIOURS_DISPLAYED, 
         						new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
										changeBehaviourState(pressedButton);
									}
								},
         						new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
									}
								}, 
         						false
         				),0,i);
         			}
                 }
             });
		}
	}
	
	private void updateAmbienceUI() {
		if(this.ambiences.size() > 0) {
			
             Platform.runLater(new Runnable() {
                 @Override public void run() {
                	ambiencesList.getChildren().clear();
         			for (int i = 0; i < ambiences.size(); i++) {
         				ambiencesList.add(new MyButtonFX(ambiences.get(i).getName(), ambiences.get(i).getId(), ambiencesList.getPrefWidth(),ambiencesList.getPrefHeight() / NB_OF_AMBIENCES_DISPLAYED, 
         						new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
									}
								}
         				),0,i);
         			}
                 }
             });
		}
	}
}

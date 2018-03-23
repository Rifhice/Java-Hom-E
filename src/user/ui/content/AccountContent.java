package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import user.ClientFX;
import user.models.Right;
import user.models.User;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyButtonImage;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MyTextFieldFX;
import user.ui.componentJavaFX.UserCell;

public class AccountContent extends Content {
	
	private MyRectangle AccountBounds = new MyRectangle(0f, 0F, 0.6f, 0.15f);
	private MyRectangle notAllowedRightsBounds = new MyRectangle(0.5f, 0.15F, 0.25f, 0.85f);
	private MyRectangle allowedRightsBounds = new MyRectangle(0.75f, 0.15F, 0.25f, 0.85f);
	private MyRectangle familyMembersBounds = new MyRectangle(0f, 0.15F, 0.5f, 0.60f);
	
	private MyRectangle newFamilyMemberBounds = new MyRectangle(0f, 0.75F, 0.5f, 0.25f);
	private MyRectangle newFamilyMemberNameLabelBounds = new MyRectangle(0.1f, 0.15f, 0.20f, 0.25f);
	private MyRectangle newFamilyMemberNameTextFieldBounds = new MyRectangle(0.35f, 0.20f, 0.25f, 0.2f);
	private MyRectangle newFamilyMemberFirstNameLabelBounds = new MyRectangle(0.1f, 0.4f, 0.20f, 0.25f);
	private MyRectangle newFamilyMemberFirstNameTextFieldBounds = new MyRectangle(0.35f, 0.45f, 0.25f, 0.2f);
	private MyRectangle newFamilyMemberPasswordLabelBounds = new MyRectangle(0.1f, 0.65f, 0.20f, 0.25f);
	private MyRectangle newFamilyMemberPasswordTextFieldBounds = new MyRectangle(0.35f, 0.7f, 0.25f, 0.2f);
	private MyRectangle newFamilyMemberButtonBounds = new MyRectangle(0.80f, 0.4f,0.1f,0.1f);
	
	
	GridPane notAllowedRightsList = new GridPane();
	GridPane allowedRightsList = new GridPane();
	GridPane familyMembersList = new GridPane();
	
	private List<Right> notAllowedRights;
	private List<Right> allowedRights;
	private List<User> familyMembers;
	
	private int NB_OF_FAMILYMEMBERS_DISPLAYED = 6;

	private static AccountContent content = null;

	private AccountContent() {
		
		MyPane accountPane = new MyPane(AccountBounds.computeBounds(width, height));
		MyPane newFamilyMemberPane = new MyPane(newFamilyMemberBounds.computeBounds(width,height));
		
		MyLabel accountLabel = new MyLabel("Family members account : ", AccountBounds.computeBounds(width,height));
		
		MyLabel newNameLabel = new MyLabel("Nom: ", newFamilyMemberNameLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newNameText = new MyTextFieldFX("Name", newFamilyMemberNameTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		MyLabel newFirstNameLabel = new MyLabel("Prénom: ", newFamilyMemberFirstNameLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newFirstNameText = new MyTextFieldFX("First Name", newFamilyMemberFirstNameTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		MyLabel newPasswordLabel = new MyLabel("Mot de passe: ", newFamilyMemberPasswordLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newPasswordText = new MyTextFieldFX("Password", newFamilyMemberPasswordTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		
		
		
		MyScrollPane notAllowedRightsScrollPane = new MyScrollPane(notAllowedRightsBounds.computeBounds(width, height));
		MyScrollPane allowedRightsScrollPane = new MyScrollPane(allowedRightsBounds.computeBounds(width, height));
		MyScrollPane familyMembersScrollPane = new MyScrollPane(familyMembersBounds.computeBounds(width, height));
		//this.getChildren().add(new Label("Account"));
		Image image = new Image("file:asset/images/check.png");
		MyButtonImage newFamilyMemberButton = new MyButtonImage(image, newFamilyMemberButtonBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		this.getChildren().add(accountPane);
		accountPane.getChildren().add(accountLabel);
		this.getChildren().add(newFamilyMemberPane);
		newFamilyMemberPane.getChildren().add(newNameText);
		newFamilyMemberPane.getChildren().add(newNameLabel);
		newFamilyMemberPane.getChildren().add(newFirstNameText);
		newFamilyMemberPane.getChildren().add(newFirstNameLabel);
		newFamilyMemberPane.getChildren().add(newPasswordLabel);
		newFamilyMemberPane.getChildren().add(newPasswordText);
		newFamilyMemberPane.getChildren().add(newFamilyMemberButton);
		this.getChildren().add(notAllowedRightsScrollPane);		
		this.getChildren().add(allowedRightsScrollPane);
		this.getChildren().add(familyMembersScrollPane);
		notAllowedRights = new ArrayList<Right>();
		
		notAllowedRightsList.setPrefWidth(notAllowedRightsScrollPane.getPrefWidth());
		notAllowedRightsList.setPrefHeight(notAllowedRightsScrollPane.getPrefHeight());
		notAllowedRightsScrollPane.setContent(notAllowedRightsList);
		
		allowedRightsList.setPrefWidth(allowedRightsScrollPane.getPrefWidth());
		allowedRightsList.setPrefHeight(allowedRightsScrollPane.getPrefHeight());
		allowedRightsScrollPane.setContent(allowedRightsList);

		familyMembersList.setPrefWidth(familyMembersScrollPane.getPrefWidth());
		familyMembersList.setPrefHeight(familyMembersScrollPane.getPrefHeight());
		familyMembersScrollPane.setContent(familyMembersList);
		
		this.updateView("user");
		this.updateView("right");

}
	
	public void updateView(String recipient) {
		JSONObject getActuator = new JSONObject();
		getActuator.put("recipient", recipient);
		getActuator.put("action", "getAll");
		System.out.println("\n Message envoyé au serveur :" + getActuator.toString());
		try {
			ClientFX.client.sendToServer(getActuator.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new AccountContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		
		if(message instanceof String) {
			System.out.println("\n Message reçu du serveur :" + message);
			try {
				System.out.println(message.toString());
				JSONObject json = new JSONObject((String)message);
				String recipient = json.getString("recipient");
				String action;
				switch(recipient) {
				case "user":
					action = json.getString("action");
					switch (action) {
					case "getAll":
						this.familyMembers = new ArrayList<User>();
						JSONArray arrArg = json.getJSONArray("users");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							this.familyMembers.add(new User(current.getInt("id"), current.getString("name")));
						}
						updateUsersUI();
						break;
					}
					break;
				case "right":
					action = json.getString("action");
					switch (action) {
					case "getAll":
						this.notAllowedRights = new ArrayList<Right>();
						JSONArray arrArg = json.getJSONArray("rights");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							this.notAllowedRights.add(new Right(current.getInt("id"), current.getString("denomination"), current.getString("description")));
						}
						//updateAmbienceUI();
						break;
					}
					break;
				}
			} catch(Exception e) {
				
			}
		}
		
	}
	
	
	private void updateUsersUI() {
		if(this.familyMembers.size() > 0) {
			
             Platform.runLater(new Runnable() {
                 @Override public void run() {
                	familyMembersList.getChildren().clear();
         			for (int i = 0; i < familyMembers.size(); i++) {
         				familyMembersList.add(new UserCell(familyMembers.get(i) ,familyMembersList.getPrefWidth(),familyMembersList.getPrefHeight() / NB_OF_FAMILYMEMBERS_DISPLAYED, 
         						new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent event) {
										int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
										//changeBehaviourState(pressedButton);
									}
								} 
         				),0,i);
         			}
                 }
             });
		}
	}
}

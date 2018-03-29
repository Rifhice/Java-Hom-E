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
import user.ui.componentJavaFX.RightCell;
import user.ui.componentJavaFX.UserCell;

public class AccountContent extends Content {
	
	private MyRectangle AccountBounds = new MyRectangle(0f, 0F, 0.6f, 0.15f);
	private MyRectangle notAllowedRightsBounds = new MyRectangle(0.5f, 0.15F, 0.25f, 0.85f);
	private MyRectangle allowedRightsBounds = new MyRectangle(0.75f, 0.15F, 0.25f, 0.85f);
	private MyRectangle familyMembersBounds = new MyRectangle(0f, 0.15F, 0.5f, 0.60f);
	
	private List<UserCell> userCells = new ArrayList<UserCell>();
	private List<RightCell> rightCells = new ArrayList<RightCell>();
	
	private MyRectangle newFamilyMemberBounds = new MyRectangle(0f, 0.75F, 0.5f, 0.25f);
	private MyRectangle newFamilyMemberPseudoLabelBounds = new MyRectangle(0.1f, 0.25f, 0.20f, 0.25f);
	private MyRectangle newFamilyMemberPseudoTextFieldBounds = new MyRectangle(0.35f, 0.30f, 0.25f, 0.2f);
	private MyRectangle newFamilyMemberPasswordLabelBounds = new MyRectangle(0.1f, 0.50f, 0.20f, 0.25f);
	private MyRectangle newFamilyMemberPasswordTextFieldBounds = new MyRectangle(0.35f, 0.55f, 0.25f, 0.2f);
	private MyRectangle newFamilyMemberButtonBounds = new MyRectangle(0.80f, 0.4f,0.1f,0.1f);
	
	
	GridPane notAllowedRightsList = new GridPane();
	GridPane allowedRightsList = new GridPane();
	GridPane familyMembersList = new GridPane();
	
	private List<Right> notAllowedRights;
	private List<Right> allowedRights;
	private List<User> familyMembers;
	
	private int NB_OF_FAMILYMEMBERS_DISPLAYED = 6;
	private int NB_OF_RIGHTS_DISPLAYED = 10;

	private static AccountContent content = null;

	private AccountContent() {
		
		MyPane accountPane = new MyPane(AccountBounds.computeBounds(width, height));
		MyPane newFamilyMemberPane = new MyPane(newFamilyMemberBounds.computeBounds(width,height));
		
		MyLabel accountLabel = new MyLabel("Family members account : ", AccountBounds.computeBounds(width,height));
		
		MyLabel newPseudoLabel = new MyLabel("Pseudo: ", newFamilyMemberPseudoLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newPseudoText = new MyTextFieldFX("Pseudo", newFamilyMemberPseudoTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
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
				createFamilyMember(newPseudoText.getText(), newPasswordText.getText());
				
			}
			
		});
		
		
		this.getChildren().add(accountPane);
		accountPane.getChildren().add(accountLabel);
		this.getChildren().add(newFamilyMemberPane);
		newFamilyMemberPane.getChildren().add(newPseudoText);
		newFamilyMemberPane.getChildren().add(newPseudoLabel);
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
		JSONObject getObject = new JSONObject();
		getObject.put("recipient", recipient);
		getObject.put("action", "getAll");
		try {
			ClientFX.client.sendToServer(getObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateViewRight(int id) {
		JSONObject getObject = new JSONObject();
		getObject.put("recipient", "right");
		getObject.put("action", "getByUser");
		getObject.put("id", id);
		try {
			ClientFX.client.sendToServer(getObject.toString());
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
	
	public void createFamilyMember(String userName, String password) {
		JSONObject newFamilyMember = new JSONObject();
		newFamilyMember.put("recipient", "user");
		newFamilyMember.put("action", "createFamilyMember");
		newFamilyMember.put("pseudo", userName );
		newFamilyMember.put("password", password);
		try {
			ClientFX.client.sendToServer(newFamilyMember.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void getAllFamilyMembers(JSONObject json) {
		this.familyMembers = new ArrayList<User>();
		JSONArray arrArg = json.getJSONArray("users");
		for (int j = 0; j < arrArg.length(); j++){
			JSONObject current = arrArg.getJSONObject(j);
			User user = new User(current.getInt("id"), current.getString("pseudo"));
			this.familyMembers.add(user);
			this.userCells.add(new UserCell(user ,familyMembersList.getPrefWidth(),familyMembersList.getPrefHeight() / NB_OF_FAMILYMEMBERS_DISPLAYED, 
					new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							int pressedButton = Integer.parseInt(((MyButtonImage)event.getSource()).getId());
							updateViewRight(pressedButton);//current.getInt("id")
							
						}}));
		}
		updateUsersUI();
		
	}
	
	public void getAllRights(JSONObject json) {
		this.notAllowedRights = new ArrayList<Right>();
		JSONArray arrArg = json.getJSONArray("rights");
		for (int j = 0; j < arrArg.length(); j++){
			JSONObject current = arrArg.getJSONObject(j);
			Right right = new Right(current.getInt("id"), current.getString("denomination"), current.getString("description"));
			this.notAllowedRights.add(right);
			this.rightCells.add(new RightCell(right, notAllowedRightsList.getPrefWidth(), notAllowedRightsList.getPrefHeight()/NB_OF_RIGHTS_DISPLAYED,new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int pressedButton = Integer.parseInt(((MyButtonImage)event.getSource()).getId());
				}}));
		}
		updateRightsUI();
	}
	
	public void getRightsByUser(JSONObject json) {
		this.allowedRights = new ArrayList<Right>();
		JSONArray allRights = json.getJSONArray("rights");
		this.rightCells = new ArrayList<RightCell>();
		for (int j = 0; j < allRights.length(); j++){
			JSONObject current = allRights.getJSONObject(j);
			Right right = new Right(current.getInt("id"), current.getString("denomination"), current.getString("description"));
			this.allowedRights.add(right);
			for (int i = 0; i < notAllowedRights.size(); i++){
				if (notAllowedRights.get(i).getId() ==  current.getInt("id")) {
					notAllowedRights.remove(i);
				}
			}
			this.rightCells.add(new RightCell(right, allowedRightsList.getPrefWidth(), allowedRightsList.getPrefHeight()/NB_OF_RIGHTS_DISPLAYED,new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int pressedButton = Integer.parseInt(((MyButtonImage)event.getSource()).getId());
				}}));
		}
		for (int j = 0; j < notAllowedRights.size(); j++){
			this.rightCells.add(new RightCell(this.notAllowedRights.get(j), notAllowedRightsList.getPrefWidth(), notAllowedRightsList.getPrefHeight()/NB_OF_RIGHTS_DISPLAYED,new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int pressedButton = Integer.parseInt(((MyButtonImage)event.getSource()).getId());
				}}));
		}
		updateRightsUI();
	}
	
	@Override
	public void handleMessage(Object message) {
		
		if(message instanceof String) {
			try {
				JSONObject json = new JSONObject(message.toString());
				String recipient = json.getString("recipient");
				
				String action;
				switch(recipient) {
				case "user":
					action = json.getString("action");
					switch (action) {
					
					case "getAll":
						getAllFamilyMembers(json);
						break;
					case "createFamilyMember":
						int id;
						id = json.getInt("id");
						updateView("user");
						updateViewRight(id);
						break;
					}
					break;
				case "right":
					action = json.getString("action");
					switch (action) {
					case "getAll":
						getAllRights(json);
						break;
					case "getByUser":
						getRightsByUser(json);
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
										int id = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
										updateView("right");
										updateViewRight(id);
									}
								} 
         				),0,i);
         			}

                 }
             });
		}
	}
	
	
	private void updateRightsUI() {
		Platform.runLater(new Runnable() {
            @Override public void run() {
				if(notAllowedRights != null) {
		                	notAllowedRightsList.getChildren().clear();
		         			for (int i = 0; i < notAllowedRights.size(); i++) {
		         				
		         				notAllowedRightsList.add(new RightCell(notAllowedRights.get(i) ,notAllowedRightsList.getPrefWidth(),notAllowedRightsList.getPrefHeight() / NB_OF_RIGHTS_DISPLAYED, 
		         						new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
												//changeBehaviourState(pressedButton);
											}
										} 
		         				),0,i);
		         			
		
		                 
		             };
				}
				if(allowedRights != null) {
			           	allowedRightsList.getChildren().clear();
			    			for (int i = 0; i < allowedRights.size(); i++) {
			    				allowedRightsList.add(new RightCell(allowedRights.get(i) ,allowedRightsList.getPrefWidth(), allowedRightsList.getPrefHeight() / NB_OF_RIGHTS_DISPLAYED, 
			    						new EventHandler<ActionEvent>() {
											@Override
											public void handle(ActionEvent event) {
												int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
												//changeBehaviourState(pressedButton);
											}
										} 
			    				),0,i);
			    			
			
			            
			        };
		       };
            }});
			
		}
}

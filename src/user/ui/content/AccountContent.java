package user.ui.content;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import user.models.Ambience;
import user.models.Behaviour;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MyTextFieldFX;

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
	
	
	GridPane notAllowedRightsList = new GridPane();
	GridPane allowedRightsList = new GridPane();
	GridPane familyMembersList = new GridPane();
	
	private List<Behaviour> notAllowedRights;
	private List<Behaviour> allowedRights;
	private List<Ambience> familyMembers;

	private static AccountContent content = null;

	private AccountContent() {
		
		MyPane accountPane = new MyPane(AccountBounds.computeBounds(width, height));
		MyPane newFamilyMemberPane = new MyPane(newFamilyMemberBounds.computeBounds(width,height));
		
		MyLabel accountLabel = new MyLabel("Family members account : ", AccountBounds.computeBounds(width,height));
		
		MyLabel newNameLabel = new MyLabel("Nom: ", newFamilyMemberNameLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newNameText = new MyTextFieldFX("Name", newFamilyMemberNameTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		MyLabel newFirstNameLabel = new MyLabel("Prénom: ", newFamilyMemberFirstNameLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newFirstNameText = new MyTextFieldFX("Name", newFamilyMemberFirstNameTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		MyLabel newPasswordLabel = new MyLabel("Mot de passe: ", newFamilyMemberPasswordLabelBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()), 1f);
		MyTextFieldFX newPasswordText = new MyTextFieldFX("Password", newFamilyMemberPasswordTextFieldBounds.computeBounds(newFamilyMemberPane.getPrefWidth(), newFamilyMemberPane.getPrefHeight()));
		
		
		
		MyScrollPane notAllowedRightsScrollPane = new MyScrollPane(notAllowedRightsBounds.computeBounds(width, height));
		MyScrollPane allowedRightsScrollPane = new MyScrollPane(allowedRightsBounds.computeBounds(width, height));
		MyScrollPane familyMembersScrollPane = new MyScrollPane(familyMembersBounds.computeBounds(width, height));
		//this.getChildren().add(new Label("Account"));
		
		
		
		this.getChildren().add(accountPane);
		accountPane.getChildren().add(accountLabel);
		this.getChildren().add(newFamilyMemberPane);
		newFamilyMemberPane.getChildren().add(newNameText);
		newFamilyMemberPane.getChildren().add(newNameLabel);
		newFamilyMemberPane.getChildren().add(newFirstNameText);
		newFamilyMemberPane.getChildren().add(newFirstNameLabel);
		newFamilyMemberPane.getChildren().add(newPasswordLabel);
		newFamilyMemberPane.getChildren().add(newPasswordText);
		this.getChildren().add(notAllowedRightsScrollPane);		
		this.getChildren().add(allowedRightsScrollPane);
		this.getChildren().add(familyMembersScrollPane);


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
		
	}
}

package ui;

import java.awt.geom.Rectangle2D;

import org.json.JSONObject;

import ui.componentJavaFX.MyButtonFX;
import ui.componentJavaFX.MyTextFieldFX;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import tools.GraphicalCharter;

public class LoginScene extends MyScene{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private static String TITLE = "Log in";
	
	private Rectangle2D.Float loginBounds = new Rectangle2D.Float(0.5f, 0.47f, 0.16f, 0.07f);
	private Rectangle2D.Float loginAsGuestBounds = new Rectangle2D.Float(0.47f, 0.57f, 0.16f, 0.07f);
	private Rectangle2D.Float pseudoBounds = new Rectangle2D.Float(0.43f, 0.28f, 0.28f, 0.06f);
	private Rectangle2D.Float passwordBounds = new Rectangle2D.Float(0.43f, 0.37f, 0.28f, 0.06f);

	private MyTextFieldFX pseudoTextField;
	private PasswordField passwordTextField = new PasswordField();
	private MyButtonFX loginButton;
	private MyButtonFX loginAsGuestButton;
	
	// ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	public LoginScene(Group root,double width, double height) {
		super(root, width,height, ClientFX.BACKGROUND_COLOR);
        root.requestFocus();
        
        System.out.println("Width : " + this.width + " Height : " + this.height);
                
        pseudoTextField = new MyTextFieldFX("Pseudo", pseudoBounds, this.width, this.height);
		root.getChildren().add(pseudoTextField);		
		
		passwordTextField.setLayoutX(passwordBounds.getX() * width);passwordTextField.setLayoutY(passwordBounds.getY() * height);passwordTextField.setMaxSize(passwordBounds.getWidth()* width, passwordBounds.getHeight()* height);
        root.getChildren().add(passwordTextField);
        passwordTextField.setPromptText("Password");
        
        /**
         * Click on password field handler.
         */
        passwordTextField.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				passwordTextField.setStyle("-fx-background-color: white;");
			}
		});
        
        loginButton = new MyButtonFX("Log in", loginBounds, this.width, this.height, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(pseudoTextField.getText().equals("")) {
					pseudoTextField.setStyle("-fx-background-color: "+GraphicalCharter.RED+";");
				}
				else {
					if(passwordTextField.getText().equals("") ) {
						//Login as guest with pseudo
						JSONObject json = new JSONObject();
						json.put("recipient", "user");
						json.put("action", "loginAsGuest");
						json.put("pseudo", pseudoTextField.getText());
						ClientFX.client.handleMessageFromClientUI(json.toString());
					}
					else {
						//Login as a user (Family member or Owner)
						JSONObject json = new JSONObject();
						json.put("recipient", "user");
						json.put("action", "login");
						json.put("pseudo", pseudoTextField.getText());
						json.put("password", passwordTextField.getText());
						ClientFX.client.handleMessageFromClientUI(json.toString());
					}
				}
			}
		});
        
        root.getChildren().add(loginButton);
        
        /**
         * "Log in as guest" button handler. 
         * No password required. 
         * Pseudo is optionnal.        
         */
        loginAsGuestButton = new MyButtonFX("Log in as guest", loginAsGuestBounds, this.width, this.height, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!pseudoTextField.getText().equals("")){
					if(passwordTextField.getText().equals("")) {
						//Login as guest with pseudo
						JSONObject json = new JSONObject();
						json.put("recipient", "user");
						json.put("action", "loginAsGuest");
						json.put("pseudo", pseudoTextField.getText());
						ClientFX.client.handleMessageFromClientUI(json.toString());
					}
					else {
						passwordTextField.setStyle("-fx-background-color: "+GraphicalCharter.RED+";");
					}
				}
				else {
					//Login as guest without pseudo
					JSONObject json = new JSONObject();
					json.put("recipient", "user");
					json.put("action", "loginAsGuest");
					ClientFX.client.handleMessageFromClientUI(json.toString());
				}
			}
		});
        root.getChildren().add(loginAsGuestButton);
        passwordTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCharacter().equals("\r")) {
					loginButton.fire();
				}
			}
		});
        pseudoTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCharacter().equals("\r")) {
					loginAsGuestButton.fire();
				}
			}
		});
	}
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //
	public void display(String message) {
		System.out.println(message);
	}

	@Override
	public void handleMessage(Object msg) {
		if(msg instanceof String) {
			try {
				JSONObject json = new JSONObject(msg.toString());
				String result = json.getString("result");
				if(result.equals("success")) {
					ClientFX.token = json.getString("token");
					ClientFX.setScene(new ContentScene(new Group(),width,height,ClientFX.BACKGROUND_COLOR));
				}else if(result.equals("wrong_pseudo")){
					pseudoTextField.setText("");
					pseudoTextField.setStyle("-fx-background-color: "+GraphicalCharter.RED+";");
					passwordTextField.setText("");
					passwordTextField.setStyle("-fx-background-color: "+GraphicalCharter.RED+";");
				}
				else if (result.equals("wrong_password")){
					passwordTextField.setText("");
					passwordTextField.setStyle("-fx-background-color: "+GraphicalCharter.RED+";");
				}
			} catch (Exception e) {
				e.printStackTrace();
				display(msg.toString());
			}
		}
	}

}
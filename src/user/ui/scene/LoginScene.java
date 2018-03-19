package user.ui.scene;

import java.awt.geom.Rectangle2D;

import org.json.JSONObject;

import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyTextFieldFX;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import user.ClientFX;
import user.tools.GraphicalCharter;

public class LoginScene extends MyScene{
    
    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
	private static String TITLE = "Log in";
	
	private MyRectangle loginBounds = new MyRectangle(0.5f, 0.47f, 0.1f, 0.05f);
	private MyRectangle loginAsGuestBounds = new MyRectangle(0.47f, 0.57f, 0.1f, 0.05f);
	private MyRectangle pseudoBounds = new MyRectangle(0.43f, 0.28f, 0.28f, 0.06f);
	private MyRectangle passwordBounds = new MyRectangle(0.43f, 0.37f, 0.28f, 0.06f);

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
                
        pseudoTextField = new MyTextFieldFX("Pseudo", pseudoBounds.computeBounds(this.width, this.height));
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
        
        loginButton = new MyButtonFX("Log in", loginBounds.computeBounds(this.width, this.height), new EventHandler<ActionEvent>() {
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
		}).centerX(this.width);
        
        root.getChildren().add(loginButton);
        
        /**
         * "Log in as guest" button handler. 
         * No password required. 
         * Pseudo is optionnal.        
         */
        loginAsGuestButton = new MyButtonFX("Log in as guest", loginAsGuestBounds.computeBounds(this.width, this.height), new EventHandler<ActionEvent>() {
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
		}).centerX(this.width);
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
					ClientFX.setScene(new ContentScene(new Group(),width,height));
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
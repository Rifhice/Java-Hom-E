package client;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Optional;
import componentJavaFX.*;
import javax.swing.*;

import common.ChatIF;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class LoginGUIFX extends Application implements ChatIF {
	
	/**
	 * Title of the frame. 
	 */
	final public static String TITLE = "Log in";
	
	/**
	 * Default port the client can connect to. 
	 */
	final public static int DEFAULT_PORT = 6543;
	
	/**
	 * Default host the client can connect to. 
	 */
	final public static String DEFAULT_HOST = "localhost";
	
	private ChatClient client;
	
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private	float widthRatio = 0.5f;
	private	float heightRatio = 0.5f;
	private int width;
	private int height;
	
	private Rectangle2D.Float loginBounds = new Rectangle2D.Float(0.5f, 0.47f, 0.16f, 0.07f);
	private Rectangle2D.Float loginAsGuestBounds = new Rectangle2D.Float(0.47f, 0.57f, 0.16f, 0.07f);
	private Rectangle2D.Float pseudoBounds = new Rectangle2D.Float(0.43f, 0.28f, 0.28f, 0.06f);
	private Rectangle2D.Float passwordBounds = new Rectangle2D.Float(0.43f, 0.37f, 0.28f, 0.06f);

	private MyTextFieldFX pseudoTextField;
	private MyTextFieldFX passwordTextField;

	@Override
	public void start(Stage primaryStage) throws Exception {
		//client = new ChatClient(DEFAULT_HOST,DEFAULT_PORT,this);
		primaryStage.setTitle(TITLE);
		Group root = new Group();
		this.width = (int)(screenSize.getWidth()* widthRatio);
		this.height = (int)(screenSize.getHeight()* heightRatio);
        Scene scene = new Scene(root, this.width, this.height, Color.LIGHTGREEN);
        
        System.out.println(this.width + " " + this.height);
        
        pseudoTextField = new MyTextFieldFX("Pseudo", pseudoBounds, width, height);
		root.getChildren().add(pseudoTextField);		
		
		passwordTextField = new MyTextFieldFX("Password", passwordBounds, width, height);
		root.getChildren().add(passwordTextField);
        
        
        root.getChildren().add(new MyButtonFX("Log in", loginBounds, this.width, this.height, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(pseudoTextField.getText().equals("") || pseudoTextField.isInitialMessage()) {
					pseudoTextField.setStyle("-fx-background-color: red;");
				}
				else {
					if(passwordTextField.getText().equals("") || passwordTextField.isInitialMessage()) {
						//Login as guest with pseudo
					}
					else {
						//Login as a user (Family member or Owner)
					}
				}
			}
		}));
        
        root.getChildren().add(new MyButtonFX("Log in as guest", loginAsGuestBounds, this.width, this.height, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!pseudoTextField.getText().equals("") && !pseudoTextField.isInitialMessage() && passwordTextField.getText().equals("") || passwordTextField.isInitialMessage()) {
					//Login as guest with pseudo
				}
				else {
					//Login as guest without pseudo
				}
			}
		}));
        
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(LoginGUIFX.class, args);
	}

	@Override
	public void display(String message) {
		System.out.println(message);
	}
	

}

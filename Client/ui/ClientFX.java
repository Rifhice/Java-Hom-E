package ui;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Optional;
import ui.componentJavaFX.*;
import javax.swing.*;

import org.json.JSONObject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.event.Event;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class ClientFX extends Application{
	final public static int DEFAULT_PORT = 1111;
	final public static String DEFAULT_HOST = "localhost";
	
	public static String token;
	
	public static ChatClient client;
	private static Stage primaryStage;
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Color BACKGROUND_COLOR = Color.LIGHTGREEN;
	
	// ==================== //
	// ==== ATTRIBUTES ==== //
	// ==================== //
	private float widthRatio = 0.5f;
	private float heightRatio = 0.5f;
	
	public static int width;
	public static int height;
	
	// ================= //
    // ==== METHODS ==== //
    // ================= //
	
	/**
	 * Launch the client UI. It tries to connect to the server. 
	 * The first scene is Login.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientFX.primaryStage = primaryStage;
		
		width = (int)(ClientFX.screenSize.getWidth()* widthRatio);
		height = (int)(ClientFX.screenSize.getHeight()* heightRatio);
		LoginScene firstScene = new LoginScene(new Group(),this.width,this.height);
		
		try {
			client = new ChatClient(DEFAULT_HOST,DEFAULT_PORT,firstScene);	
			setScene(firstScene);
		} catch (Exception e) {
			System.out.println("Connexion impossible");
		}
	}
	
	/**
	 * Change the scene of the client UI.
	 * @param scene
	 */
	public static void setScene(MyScene scene) {
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		        primaryStage.setScene((Scene) scene);
		        primaryStage.setTitle(scene.getTitle());
		        client.setScene(scene);
		        primaryStage.show();
		    }
		});
	}
	
	// ============== //
    // ==== MAIN ==== //
    // ============== //
	public static void main(String[] args) {
		Application.launch(ClientFX.class, args);
	}
	

}

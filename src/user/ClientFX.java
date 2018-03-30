package user;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import user.communication.UserClient;
import user.tools.GraphicalCharter;
import user.ui.scene.LoginScene;
import user.ui.scene.MyScene;

public class ClientFX extends Application{
	final public static int DEFAULT_PORT = 1111;
	final public static String DEFAULT_HOST = "localhost";
	
	public static String token;
	
	public static UserClient client;
	public static Stage primaryStage;
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static Color BACKGROUND_COLOR = Color.web(GraphicalCharter.LIGHT_GREEN);
	
	// ==================== //
	// ==== ATTRIBUTES ==== //
	// ==================== //
	public static final float widthRatio = 0.7f;
	public static final float heightRatio = 0.7f;
	
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
		primaryStage.getIcons().add(new Image("file:asset/images/logo.png"));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent arg0) {
		        Platform.exit();
		        System.exit(0);
			}
		});
		width = (int)(ClientFX.screenSize.getWidth()* widthRatio);
		height = (int)(ClientFX.screenSize.getHeight()* heightRatio);
		
		LoginScene firstScene = new LoginScene(new Group(),this.width,this.height);
		
		try {
			client = new UserClient(DEFAULT_HOST,DEFAULT_PORT,firstScene);	
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
		        client.setClientUI(scene);
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

package client;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Optional;
import componentJavaFX.*;
import javax.swing.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class LoginGUIFX extends Application {
	
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
	
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private	float widthRatio = 0.5f;
	private	float heightRatio = 0.5f;
	private int width;
	private int height;
	
	private Rectangle2D.Float loginBounds = new Rectangle2D.Float(0.83f, 0.87f, 0.09f, 0.07f);

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle(TITLE);
		Group root = new Group();
		this.width = (int)(screenSize.getWidth()* widthRatio);
		this.height = (int)(screenSize.getHeight()* heightRatio);
        Scene scene = new Scene(root, this.width, this.height, Color.LIGHTGREEN);
        root.getChildren().add(new MyButtonFX("Log in", loginBounds, this.width, this.height, new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// to do
			}
		}));
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		Application.launch(LoginGUIFX.class, args);
	}
	

}

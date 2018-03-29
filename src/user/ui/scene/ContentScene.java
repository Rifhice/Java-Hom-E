package user.ui.scene;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import user.ClientFX;
import user.tools.GraphicalCharter;
import user.ui.InfoBar;
import user.ui.Menu;
import user.ui.content.AccountContent;
import user.ui.content.ActuatorContent;
import user.ui.content.AmbiencesContent;
import user.ui.content.BehavioursCommandsContent;
import user.ui.content.CategoriesContent;
import user.ui.content.Content;
import user.ui.content.ControlContent;
import user.ui.content.HomeContent;
import user.ui.content.SensorContent;
import user.ui.delegate.MenuDelegate;

public class ContentScene extends MyScene implements MenuDelegate {
	
	private Menu menu;
	private Content content;
	private Pane infoBar;
	private BorderPane scenePane;

	public final static double infoBarWidthRatio = 1;
	public final static double infoBarHeightRatio = 0.1;
	
	public final static double menuWidthRatio = 0.2;
	public final static double menuHeightRatio = 0.9;
	
	public final static double contentWidthRatio = 0.8;
	public final static double contentHeightRatio = 0.9;
	
	private static ContentScene instance = null;
	
    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
	private ContentScene(Group root, double width, double height) {
		super(root, width, height, ClientFX.BACKGROUND_COLOR);
		scenePane = new BorderPane();
		scenePane.setMinSize(ClientFX.width, ClientFX.height);
		
		this.infoBar = new InfoBar();
		this.infoBar.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_BLUE), CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.menu = Menu.getInstance(this);
		this.menu.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		this.content = HomeContent.getInstance();
		
		scenePane.setLeft(this.menu);
		scenePane.setCenter(this.content);
		scenePane.setTop(infoBar);
		root.getChildren().add(scenePane);
		this.title = "Hom-E";
		System.out.println(ClientFX.token);
	}

	// ================= //
    // ==== METHODS ==== //
    // ================= //
	@Override
	public void handleMessage(Object msg) {
		this.content.handleMessage(msg);
	}

	public static ContentScene instanciate(Group root, double width, double height) {
		if(instance == null) {
			instance = new ContentScene(root, width, height);
		}
		return instance;
	}
	
	public static ContentScene getInstance() {
		return instance;
	}
	
	@Override
	public void display(String message) {
		System.out.println(message);
	}

	@Override
	public void chanteCurrentContent(CONTENT content) {
		switch(content) {
			case HOME:
				this.changeContent(HomeContent.getInstance());
				break;
			case CONTROLS:
				this.changeContent(ControlContent.getInstance());
				break;
			case SENSORS:
				this.changeContent(SensorContent.getInstance());
				break;
			case ACTUATORS:
				this.changeContent(ActuatorContent.getInstance());
				break;
			case BEHAVIOURS_COMMANDS:
				this.changeContent(BehavioursCommandsContent.getInstance());
				break;
			case AMBIENCES:
				this.changeContent(AmbiencesContent.getInstance());
				break;
			case CATEGORIES:
				this.changeContent(CategoriesContent.getInstance());
				break;
			case ACCOUNTS:
				this.changeContent(AccountContent.getInstance());
				break;
			default:
				break;
		}
	}
	
	public void changeContent(Content content) {
		this.content = content;
		scenePane.setCenter(this.content);
	}
	
	
	
}

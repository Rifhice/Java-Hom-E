package user.ui.content;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import server.managers.ActuatorCategorieManager;
import server.models.argument.Argument;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import user.ClientFX;
import user.models.ActuatorCategory;
import user.models.SensorCategory;
import user.tools.GraphicalCharter;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;
import user.ui.componentJavaFX.MyTextArea;
import javafx.scene.layout.GridPane;

public class CategoriesContent extends Content {

	private static final int NB_OF_CAT_DISPLAYED = 8;
	
	private static CategoriesContent content = null;

	private ArrayList<ActuatorCategory> actuator = new ArrayList<ActuatorCategory>();
	private ArrayList<SensorCategory> sensor = new ArrayList<SensorCategory>();
	
	private ActuatorCategory currentActuatorCategory = null;
	private SensorCategory currentSensorCategory = null;
	
	private GridPane actuatorListGrid = new GridPane();
	private GridPane sensorListGrid = new GridPane();
	
	private MyRectangle actuatorScrollPaneBounds = new MyRectangle(0f,0.1f,0.2f,1f);
	private MyRectangle actuatorInfoBounds = new MyRectangle(0.2f,0.1f,0.3f,1f);
	private MyRectangle sensorScrollPaneBounds = new MyRectangle(0.5f,0.1f,0.2f,1f);
	private MyRectangle sensorInfoBounds = new MyRectangle(0.7f,0.1f,0.3f,1f);
	
	private MyRectangle actuatorLabelBounds = new MyRectangle(0.05f,0f,0.5f,0.1f);
	private MyRectangle sensorLabelBounds = new MyRectangle(0.55f,0f,0.5f,0.1f);
	
	private MyRectangle nameLabelBounds = new MyRectangle(0.05f,0f,1f,0.1f);
	private MyRectangle descriptionLabelBounds = new MyRectangle(0.05f,0.1f,0.85f,0.3f);
	private MyRectangle modifyButtonBounds = new MyRectangle(0.15f,0.5f,0.3f,0.05f);
	private MyRectangle deleteButtonBounds = new MyRectangle(0.55f,0.5f,0.3f,0.05f);
	private MyRectangle newButtonBounds = new MyRectangle(0.4f,0.7f,0.35f,0.05f);
	
	private MyLabel currentActuatorCategoryName;
	private MyTextArea currentActuatorCategoryDescription;
	
	private MyLabel currentSensorCategoryName;
	private MyTextArea currentSensorCategoryDescription;
	
	private CategoriesContent() {
		MyScrollPane actuatorList = new MyScrollPane(actuatorScrollPaneBounds.computeBounds(width,height));
		actuatorListGrid.setPrefWidth(actuatorList.getPrefWidth());
		actuatorListGrid.setPrefHeight(actuatorList.getPrefHeight());
		actuatorList.setContent(actuatorListGrid);
		
		MyPane actuatorInfo = new MyPane(actuatorInfoBounds.computeBounds(width,height));
		actuatorInfo.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_BLUE), CornerRadii.EMPTY, Insets.EMPTY)));
		
		currentActuatorCategoryName = new MyLabel("Name",nameLabelBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()));
		currentActuatorCategoryDescription = new MyTextArea("description",descriptionLabelBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight())).lockText();
		
		actuatorInfo.getChildren().add(currentActuatorCategoryName);
		actuatorInfo.getChildren().add(currentActuatorCategoryDescription);
		actuatorInfo.getChildren().add(new MyButtonFX("Modify",modifyButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}));
		actuatorInfo.getChildren().add(new MyButtonFX("Delete",deleteButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}));
		actuatorInfo.getChildren().add(new MyButtonFX("Create New",newButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}).centerX(actuatorInfo.getPrefWidth()));
		
		
		
		
		MyScrollPane sensorList = new MyScrollPane(sensorScrollPaneBounds.computeBounds(width,height));
		sensorListGrid.setPrefWidth(sensorList.getPrefWidth());
		sensorListGrid.setPrefHeight(sensorList.getPrefHeight());
		sensorList.setContent(sensorListGrid);
		
		MyPane sensorInfo = new MyPane(sensorInfoBounds.computeBounds(width,height));
		sensorInfo.setBackground(new Background(new BackgroundFill(Color.web(GraphicalCharter.LIGHT_BLUE), CornerRadii.EMPTY, Insets.EMPTY)));
		
		currentSensorCategoryName = new MyLabel("Name",nameLabelBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()));
		currentSensorCategoryDescription = new MyTextArea("description",descriptionLabelBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight())).lockText();
		
		sensorInfo.getChildren().add(currentSensorCategoryName);
		sensorInfo.getChildren().add(currentSensorCategoryDescription);
		sensorInfo.getChildren().add(new MyButtonFX("Modify",modifyButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}));
		sensorInfo.getChildren().add(new MyButtonFX("Delete",deleteButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}));
		sensorInfo.getChildren().add(new MyButtonFX("Create New",newButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		}).centerX(actuatorInfo.getPrefWidth()));
		
		this.getChildren().add(new MyLabel("Actuator categories", actuatorLabelBounds.computeBounds(width, height)));
		this.getChildren().add(new MyLabel("Sensor categories", sensorLabelBounds.computeBounds(width, height)));
		
		
		this.getChildren().add(actuatorList);
		this.getChildren().add(actuatorInfo);
		
		this.getChildren().add(sensorList);
		this.getChildren().add(sensorInfo);
		
		updateData();
	}

	public void updateData() {
		JSONObject getSensor = new JSONObject();
		getSensor.put("recipient", "sensorCategories");
		getSensor.put("action", "getAll");
		
		JSONObject getActuator = new JSONObject();
		getActuator.put("recipient", "actuatorCategories");
		getActuator.put("action", "getAll");
		
		try {
			ClientFX.client.sendToServer(getSensor.toString());
			ClientFX.client.sendToServer(getActuator.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateActuatorUI() {
		if(actuator.size() != 0) {
			if(currentActuatorCategory == null) {
				currentActuatorCategory = actuator.get(0);
			}
			for (int i = 0; i < actuator.size(); i++) {
				actuatorListGrid.add(new MyButtonFX(actuator.get(i).getName(),i,actuatorListGrid.getPrefWidth(),actuatorListGrid.getPrefHeight() / NB_OF_CAT_DISPLAYED, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
						currentActuatorCategory = actuator.get(pressedButton);
						currentActuatorCategoryName.setText(currentActuatorCategory.getName());
						currentActuatorCategoryDescription.setText(currentActuatorCategory.getDescription());
					}
				}),0,i);
			}
			currentActuatorCategoryName.setText(currentActuatorCategory.getName());
			currentActuatorCategoryDescription.setText(currentActuatorCategory.getDescription());
		}
	}
	
	public void updateSensorUI() {
		if(sensor.size() != 0) {
			if(currentSensorCategory == null) {
				currentSensorCategory = sensor.get(0);
			}
			for (int i = 0; i < sensor.size(); i++) {
				sensorListGrid.add(new MyButtonFX(sensor.get(i).getName(),i,sensorListGrid.getPrefWidth(),sensorListGrid.getPrefHeight() / NB_OF_CAT_DISPLAYED, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
						currentSensorCategory = sensor.get(pressedButton);
						currentSensorCategoryName.setText(currentSensorCategory.getName());
						currentSensorCategoryDescription.setText(currentSensorCategory.getDescription());
					}
				}),0,i);
			}
			currentSensorCategoryName.setText(currentSensorCategory.getName());
			currentSensorCategoryDescription.setText(currentSensorCategory.getDescription());
		}
	}
	
	public static Content getInstance() {
		if(content == null) {
			content = new CategoriesContent();
		}
		return content;
	}
	
	@Override
	public void handleMessage(Object message) {
		if(message instanceof String) {
			try {
				System.out.println(message.toString());
				JSONObject json = new JSONObject((String)message);
				if(json.getString("recipient").equals("sensorCategories")) {
					String action = json.getString("action");
					switch (action) {
					case "getAll":
						JSONArray arrArg = json.getJSONArray("categories");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							sensor.add(new SensorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
						}
						updateSensorUI();
						System.out.println(sensor);
						break;
					default:
						break;
					}
				}
				else if(json.getString("recipient").equals("actuatorCategories")) {
					String action = json.getString("action");
					switch (action) {
					case "getAll":
						JSONArray arrArg = json.getJSONArray("categories");
						for (int j = 0; j < arrArg.length(); j++){
							JSONObject current = arrArg.getJSONObject(j);
							actuator.add(new ActuatorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
						}
						updateActuatorUI();
						System.out.println(actuator);
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

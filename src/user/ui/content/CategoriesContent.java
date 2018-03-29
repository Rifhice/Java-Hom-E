	package user.ui.content;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
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
import user.ui.componentJavaFX.MyTextDialog;
import javafx.scene.layout.GridPane;

public class CategoriesContent extends Content {

    private static final int NB_OF_CAT_DISPLAYED = 8;

    private static CategoriesContent content = null;

    private ArrayList<ActuatorCategory> actuator = new ArrayList<ActuatorCategory>();
    private ArrayList<SensorCategory> sensor = new ArrayList<SensorCategory>();

    private int currentActuatorCategoryIndex = -1;
    private int currentSensorCategoryIndex = -1;

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
                Pair<String,String> result = new MyTextDialog("Modify actuator category", "Modify actuator category", actuator.get(currentActuatorCategoryIndex).getName(), actuator.get(currentActuatorCategoryIndex).getDescription()).showAndReturn();
                if(result != null && ( !result.getKey().equals(actuator.get(currentActuatorCategoryIndex).getName()) || !result.getValue().equals(actuator.get(currentActuatorCategoryIndex).getDescription()) )){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "actuatorCategories");
                    json.put("action", "update");
                    json.put("id", actuator.get(currentActuatorCategoryIndex).getId());
                    json.put("name", result.getKey());
                    json.put("description", result.getValue());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        actuatorInfo.getChildren().add(new MyButtonFX("Delete",deleteButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JSONObject json = new JSONObject();
                json.put("recipient", "actuatorCategories");
                json.put("action", "delete");
                json.put("id", actuator.get(currentActuatorCategoryIndex).getId());
                try {
                    ClientFX.client.sendToServer(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentActuatorCategoryIndex--;
            }
        }));
        actuatorInfo.getChildren().add(new MyButtonFX("Create New",newButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pair<String,String> result = new MyTextDialog("Create actuator category", "Create actuator category", "", "").showAndReturn();
                if(result != null && !result.getKey().equals("") && !result.getValue().equals("")){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "actuatorCategories");
                    json.put("action", "create");
                    json.put("name", result.getKey());
                    json.put("description", result.getValue());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
                Pair<String,String> result = new MyTextDialog("Modify sensor category", "Modify sensor category", sensor.get(currentSensorCategoryIndex).getName(), sensor.get(currentSensorCategoryIndex).getDescription()).showAndReturn();
                if(result != null && ( !result.getKey().equals(sensor.get(currentSensorCategoryIndex).getName()) || !result.getValue().equals(sensor.get(currentSensorCategoryIndex).getDescription()) )){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "sensorCategories");
                    json.put("action", "update");
                    json.put("id", sensor.get(currentSensorCategoryIndex).getId());
                    json.put("name", result.getKey());
                    json.put("description", result.getValue());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        sensorInfo.getChildren().add(new MyButtonFX("Delete",deleteButtonBounds.computeBounds(sensorInfo.getPrefWidth(),sensorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JSONObject json = new JSONObject();
                json.put("recipient", "sensorCategories");
                json.put("action", "delete");
                json.put("id", sensor.get(currentSensorCategoryIndex).getId());
                try {
                    ClientFX.client.sendToServer(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                currentSensorCategoryIndex--;
            }
        }));
        sensorInfo.getChildren().add(new MyButtonFX("Create New",newButtonBounds.computeBounds(actuatorInfo.getPrefWidth(),actuatorInfo.getPrefHeight()),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pair<String,String> result = new MyTextDialog("Create sensor category", "Create sensor category", "", "").showAndReturn();
                if(result != null && !result.getKey().equals("") && !result.getValue().equals("")){
                    JSONObject json = new JSONObject();
                    json.put("recipient", "sensorCategories");
                    json.put("action", "create");
                    json.put("name", result.getKey());
                    json.put("description", result.getValue());
                    try {
                        ClientFX.client.sendToServer(json.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).centerX(actuatorInfo.getPrefWidth()));

        this.getChildren().add(new MyLabel("Actuator categories", actuatorLabelBounds.computeBounds(width, height)));
        this.getChildren().add(new MyLabel("Sensor categories", sensorLabelBounds.computeBounds(width, height)));


        this.getChildren().add(actuatorList);
        this.getChildren().add(actuatorInfo);

        this.getChildren().add(sensorList);
        this.getChildren().add(sensorInfo);

        updateActuatorData();
        updateSensorData();
    } 
    // ===== END Constructor ===== //

    public void updateActuatorData() {
        JSONObject getActuator = new JSONObject();
        getActuator.put("recipient", "actuatorCategories");
        getActuator.put("action", "getAll");

        try {
            ClientFX.client.sendToServer(getActuator.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSensorData() {
        JSONObject getSensor = new JSONObject();
        getSensor.put("recipient", "sensorCategories");
        getSensor.put("action", "getAll");

        try {
            ClientFX.client.sendToServer(getSensor.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateActuatorUI() {
        if(actuator.size() != 0) {
            if(currentActuatorCategoryIndex == -1) {
                currentActuatorCategoryIndex = 0;
            }
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    actuatorListGrid.getChildren().clear();
                    for (int i = 0; i < actuator.size(); i++) {
                        actuatorListGrid.add(new MyButtonFX(actuator.get(i).getName(),i,actuatorListGrid.getPrefWidth(),actuatorListGrid.getPrefHeight() / NB_OF_CAT_DISPLAYED, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
                                currentActuatorCategoryIndex = pressedButton;
                                currentActuatorCategoryName.setText(actuator.get(currentActuatorCategoryIndex).getName());
                                currentActuatorCategoryDescription.setText(actuator.get(currentActuatorCategoryIndex).getDescription());
                            }
                        }),0,i);
                    }
                    currentActuatorCategoryName.setText(actuator.get(currentActuatorCategoryIndex).getName());
                    currentActuatorCategoryDescription.setText(actuator.get(currentActuatorCategoryIndex).getDescription());
                }
            });
        }
    }

    public void updateSensorUI() {
        if(sensor.size() != 0) {
            if(currentSensorCategoryIndex == -1) {
                currentSensorCategoryIndex = 0;
            }

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    sensorListGrid.getChildren().clear();
                    for (int i = 0; i < sensor.size(); i++) {
                        sensorListGrid.add(new MyButtonFX(sensor.get(i).getName(),i,sensorListGrid.getPrefWidth(),sensorListGrid.getPrefHeight() / NB_OF_CAT_DISPLAYED, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
                                currentSensorCategoryIndex = pressedButton;
                                currentSensorCategoryName.setText(sensor.get(currentSensorCategoryIndex).getName());
                                currentSensorCategoryDescription.setText(sensor.get(currentSensorCategoryIndex).getDescription());
                            }
                        }),0,i);
                    }
                    currentSensorCategoryName.setText(sensor.get(currentSensorCategoryIndex).getName());
                    currentSensorCategoryDescription.setText(sensor.get(currentSensorCategoryIndex).getDescription());
                }
            });
        }
    }

    public static Content getInstance() {
        if(content == null) {
            content = new CategoriesContent();
        }
        content.updateSensorData();
        content.updateActuatorData();
        return content;
    }

    @Override
    public void handleMessage(Object message) {
        if(message instanceof String) {
            try {
                JSONObject json = new JSONObject((String)message);
                if(json.getString("recipient").equals("sensorCategories")) {
                    String action = json.getString("action");
                    switch (action) {
                    case "getAll":
                        sensor = new ArrayList<>();
                        JSONArray arrArg = json.getJSONArray("categories");
                        for (int j = 0; j < arrArg.length(); j++){
                            JSONObject current = arrArg.getJSONObject(j);
                            sensor.add(new SensorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
                        }
                        updateSensorUI();
                        break;
                    case "create":
                        if(json.getString("result").equals("success")) {
                            updateSensorData();
                        }
                        break;
                    case "update":
                        if(json.getString("result").equals("success")) {
                            updateSensorData();
                        }
                        break;
                    case "delete":
                        if(json.getString("result").equals("success")) {
                            updateSensorData();
                        }
                        break;
                    default:
                        break;
                    }
                }
                else if(json.getString("recipient").equals("actuatorCategories")) {
                    String action = json.getString("action");
                    switch (action) {
                    case "getAll":
                        actuator = new ArrayList<>();
                        JSONArray arrArg = json.getJSONArray("categories");
                        for (int j = 0; j < arrArg.length(); j++){
                            JSONObject current = arrArg.getJSONObject(j);
                            actuator.add(new ActuatorCategory(current.getInt("id"), current.getString("name"), current.getString("description")));
                        }
                        updateActuatorUI();
                        break;
                    case "create":
                        if(json.getString("result").equals("success")) {
                            updateActuatorData();
                        }
                        break;
                    case "update":
                        if(json.getString("result").equals("success")) {
                            updateActuatorData();
                        }
                        break;
                    case "delete":
                        if(json.getString("result").equals("success")) {
                            updateActuatorData();
                        }
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

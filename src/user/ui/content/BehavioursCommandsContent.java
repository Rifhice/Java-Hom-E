package user.ui.content;

import java.io.IOException;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import user.ClientFX;
import user.models.Behaviour;
import user.ui.componentJavaFX.MyButtonFX;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyLabel;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;
import user.ui.componentJavaFX.MyScrollPane;

public class BehavioursCommandsContent extends Content {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
   
    // ==== UI attributes ==== // 
    private static final float MARGIN = 0.05f;
    
    private MyRectangle behavioursListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
    private MyRectangle currentBehaviourBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle commandsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
    private MyGridPane behavioursGrid = new MyGridPane(new MyRectangle(0f, 0f, 1f, 1f));
    
    private MyRectangle nameLabelBounds = new MyRectangle(0.0f,0.05f,1f,0.1f);
    private MyRectangle descriptionLegendBounds = new MyRectangle(MARGIN, 0.15f, 1.0f, 0.1f);
    private MyRectangle descriptionLabelBounds = new MyRectangle(MARGIN, 0.25f, 1.0f, 0.1f);

    private MyLabel currentBehaviourName;
    private MyLabel currentBehaviourDescription;

    // ==== Content attributes ==== // 
    
    private static final int NB_OF_BEHAVIOURS_DISPLAYED = 8;

    private int currentBehaviourIndex = -1;
    
    private static BehavioursCommandsContent content = null;
    private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    private BehavioursCommandsContent() {
        
        // ==== Behaviours List (left) 
        MyScrollPane behavioursListPane = new MyScrollPane(behavioursListBounds.computeBounds(width, height));
        
        // Grid
        behavioursGrid.setPrefWidth(behavioursListPane.getPrefWidth());
        behavioursGrid.setPrefHeight(behavioursListPane.getHeight());
        behavioursListPane.setContent(behavioursGrid);

        // ==== Current Behaviour (center)
        MyPane selectedBehaviourPane = new MyPane(currentBehaviourBounds.computeBounds(width, height));
        selectedBehaviourPane.setStyle("-fx-background-color: rgb(50, 50, 255)");
        
        // Name
        currentBehaviourName = new MyLabel("Behaviour name",nameLabelBounds.computeBounds(selectedBehaviourPane.getPrefWidth(),selectedBehaviourPane.getPrefHeight()));
        currentBehaviourName.setStyle("-fx-background-color: rgb(50, 255, 50)");
        currentBehaviourName.centerX(selectedBehaviourPane.getPrefWidth());
        currentBehaviourName.setAlignment(Pos.CENTER);
        selectedBehaviourPane.getChildren().add(currentBehaviourName);
        
        // Description
        selectedBehaviourPane.getChildren().add(new MyLabel("Description",descriptionLegendBounds.computeBounds(selectedBehaviourPane.getPrefWidth(),selectedBehaviourPane.getPrefHeight())));
        currentBehaviourDescription = new MyLabel("Behaviour description",descriptionLabelBounds.computeBounds(selectedBehaviourPane.getPrefWidth(),selectedBehaviourPane.getPrefHeight()));
        currentBehaviourDescription.setStyle("-fx-background-color: rgb(50, 255, 255)");
        currentBehaviourDescription.centerX(selectedBehaviourPane.getPrefWidth());
        selectedBehaviourPane.getChildren().add(currentBehaviourDescription);

        
        // ==== Commands List (right)
        MyPane commandsPane = new MyPane(commandsBounds.computeBounds(width, height));
        commandsPane.setStyle("-fx-background-color: rgb(255, 255, 50)");
        commandsPane.getChildren().add(new Label("All commands"));

        this.getChildren().add(behavioursListPane);
        this.getChildren().add(selectedBehaviourPane);
        this.getChildren().add(commandsPane);
    }

    public static Content getInstance() {
        if(content == null) {
            content = new BehavioursCommandsContent();
        }
        content.updateBehavioursData();
        return content;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //
    
    /**
     * Ask the server to get all the behaviours.
     */
    public void updateBehavioursData() {
        JSONObject getBehaviours = new JSONObject();
        getBehaviours.put("recipient", "behaviour");
        getBehaviours.put("action", "getAll");
        try {
            ClientFX.client.sendToServer(getBehaviours.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessage(Object message) {
        System.out.println("BEHAVIOURS PANE - Received: " + message);
        if(message instanceof String) {
            try {
                JSONObject json = new JSONObject((String)message);
                if(json.getString("recipient").equals("behaviour")) {
                    String action = json.getString("action");
                    switch (action) {
                    case "getAll":
                        behaviours = new ArrayList<>();
                        JSONArray arrArg = json.getJSONArray("behaviours");
                        for (int j = 0; j < arrArg.length(); j++){
                            JSONObject current = arrArg.getJSONObject(j);
                            
                            // TODO : decomment 1rst line and delete 2nd one when getAll() functional
                            // behaviours.add(new Behaviour(current.getInt("id"), current.getString("name"), current.getBoolean("isActivated")));
                            behaviours.add(new Behaviour(current.getInt("id"), current.getString("name")));
                        }
                        this.updateBehaviourUI();
                        break;
                        /*
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
                    */
                    default:
                        break;
                    }           
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Update the UI parts relatives to the behaviours
     */
    public void updateBehaviourUI() {
        System.out.println("Updating behaviours");
        
        if(behaviours.size() != 0) {
            if(currentBehaviourIndex == -1) {
                currentBehaviourIndex = 0;
            }

            Platform.runLater(new Runnable() {
                @Override public void run() {
                    behavioursGrid.getChildren().clear();
                    
                    for (int i = 0; i < behaviours.size(); i++) {
                        behavioursGrid.add(new MyButtonFX(behaviours.get(i).getName(),i,behavioursGrid.getPrefWidth(),behavioursGrid.getPrefHeight() / NB_OF_BEHAVIOURS_DISPLAYED, new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                int pressedButton = Integer.parseInt(((MyButtonFX)event.getSource()).getId());
                                currentBehaviourIndex = pressedButton;
                                currentBehaviourName.setText(behaviours.get(currentBehaviourIndex).getName());
                            }
                        }),0,i);
                    }
                    currentBehaviourName.setText(behaviours.get(currentBehaviourIndex).getName());
                }
            });
        }
    }
}

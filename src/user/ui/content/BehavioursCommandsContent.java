package user.ui.content;

import javafx.scene.control.Label;
import user.ui.componentJavaFX.MyGridPane;
import user.ui.componentJavaFX.MyPane;
import user.ui.componentJavaFX.MyRectangle;

public class BehavioursCommandsContent extends Content {

    // ==================== //
    // ==== ATTRIBUTES ==== //
    // ==================== //
    private MyRectangle behavioursListBounds = new MyRectangle(0f, 0f, 0.25f, 1.0f);
    private MyRectangle selectedBehaviourBounds = new MyRectangle(0.25f, 0f, 0.375f, 1.0f);
    private MyRectangle commandsBounds = new MyRectangle(0.625f, 0f, 0.375f, 1.0f);
    
    private MyGridPane behavioursGrid = new MyGridPane(new MyRectangle(0f, 0f, 1f, 1f));

    private static BehavioursCommandsContent content = null;

    // ====================== //
    // ==== CONSTRUCTORS ==== //
    // ====================== //
    private BehavioursCommandsContent() {
        MyPane behavioursListPane = new MyPane(behavioursListBounds.computeBounds(width, height));
        behavioursListPane.setStyle("-fx-background-color: rgb(255, 50, 50)");
        behavioursListPane.getChildren().add(new Label("Actuators List"));

        MyPane selectedBehaviourPane = new MyPane(selectedBehaviourBounds.computeBounds(width, height));
        selectedBehaviourPane.setStyle("-fx-background-color: rgb(50, 50, 255)");
        selectedBehaviourPane.getChildren().add(new Label("Selected Behaviour"));
        
        MyPane commandsPane = new MyPane(commandsBounds.computeBounds(width, height));
        commandsPane.setStyle("-fx-background-color: rgb(50, 255, 50)");
        commandsPane.getChildren().add(new Label("All commands"));

        this.getChildren().add(behavioursListPane);
        this.getChildren().add(selectedBehaviourPane);
        this.getChildren().add(commandsPane);
    }

    public static Content getInstance() {
        if(content == null) {
            content = new BehavioursCommandsContent();
        }
        return content;
    }
    
    // ================= //
    // ==== METHODS ==== //
    // ================= //

    @Override
    public void handleMessage(Object message) {

    }
}

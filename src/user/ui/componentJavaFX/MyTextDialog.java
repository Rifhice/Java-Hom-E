package user.ui.componentJavaFX;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class MyTextDialog{
	
	Dialog<Pair<String, String>> dialog;
	Pair<String, String> res = null;
	
	public MyTextDialog(String title, String header, String nameString,String descriptionString) {

		    	dialog = new Dialog<>();
		    	dialog.setTitle(title);
		    	dialog.setHeaderText(header);
		    	
		    	// Set the button types.
		    	ButtonType validButtonType = new ButtonType("Valider");
		    	dialog.getDialogPane().getButtonTypes().addAll(validButtonType, ButtonType.CANCEL);

		    	// Create the username and password labels and fields.
		    	GridPane grid = new GridPane();
		    	grid.setHgap(10);
		    	grid.setVgap(10);

		    	TextField name = new TextField();
		    	name.setText(nameString);
		    	TextArea description = new TextArea();
		    	description.setText(descriptionString);

		    	grid.add(new Label("Name:"), 0, 0);
		    	grid.add(name, 1, 0);
		    	grid.add(new Label("Description:"), 0, 1);
		    	grid.add(description, 1, 1);

		    	dialog.getDialogPane().setContent(grid);

		    	Platform.runLater(() -> name.requestFocus());

		    	dialog.setResultConverter(dialogButton -> {
		    	    if (dialogButton == validButtonType) {
		    	        return new Pair<>(name.getText(), description.getText());
		    	    }
		    	    return null;
		    	});
	}
	
	public Pair<String, String> showAndReturn(){
    	Optional<Pair<String, String>> result = dialog.showAndWait();

    	result.ifPresent(resu -> {
    		res = resu;
    	});
    	return res;
	}
	/*
	TextInputDialog dialog = new TextInputDialog("walter");
	dialog.setTitle("Text Input Dialog");
	dialog.setHeaderText("Look, a Text Input Dialog");
	dialog.setContentText("Please enter your name:");

	// Traditional way to get the response value.
	Optional<String> result = dialog.showAndWait();
	if (result.isPresent()){
	    System.out.println("Your name: " + result.get());
	}

	// The Java 8 way to get the response value (with lambda expression).
	result.ifPresent(name -> System.out.println("Your name: " + name));*/
	
}

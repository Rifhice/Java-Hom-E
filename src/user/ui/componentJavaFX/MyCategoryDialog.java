package user.ui.componentJavaFX;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.StringConverter;
import user.models.Category;
import user.tools.Triplet;


public class MyCategoryDialog{
	
	Dialog<Triplet<String, String, Category>> dialog;
	Triplet<String, String, Category> res = null;
	
	public MyCategoryDialog(String title, String header, String nameString,String descriptionString,Category category, ArrayList<Category> categories) {

		    	dialog = new Dialog<>();
		    	dialog.setTitle(title);
		    	dialog.setHeaderText(header);
		    	
		    	// Set the button types. 
		    	ButtonType validButtonType = new ButtonType("Validate", ButtonBar.ButtonData.OK_DONE);
		    	ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE); 
	            dialog.getDialogPane().getButtonTypes().add(cancelButtonType);    
		    	dialog.getDialogPane().getButtonTypes().add(validButtonType);

		    	GridPane grid = new GridPane();
		    	grid.setHgap(10);
		    	grid.setVgap(10);

		    	TextField name = new TextField();
		    	name.setText(nameString);
		    	TextArea description = new TextArea();
		    	description.setText(descriptionString);
		    	for (int i = 0; i < categories.size(); i++) {
					if(categories.get(i).getId() < 0) {
						categories.remove(i);
						i--;
					}
				}
		    	MyComboBox<Category> cat = new MyComboBox<>(categories);
		    	cat.setConverter(new StringConverter<Category>() {

					@Override
					public Category fromString(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public String toString(Category arg0) {
						// TODO Auto-generated method stub
						return arg0 == null ? "" : arg0.getName();
					}
		        });
		    	cat.getSelectionModel().select(category);
		    	grid.add(new Label("Name:"), 0, 0);
		    	grid.add(name, 1, 0);
		    	grid.add(new Label("Description:"), 0, 1);
		    	grid.add(description, 1, 1);
		    	grid.add(new Label("Category:"), 0, 2);
		    	grid.add(cat, 1, 2);

		    	dialog.getDialogPane().setContent(grid);

		    	Platform.runLater(() -> name.requestFocus());

		    	dialog.setResultConverter(dialogButton -> {
		    	    if (dialogButton == validButtonType) {
		    	        return new Triplet<String, String, Category>(name.getText(), description.getText(), cat.getValue());
		    	    }
		    	    return null;
		    	});
	}
	
	public Triplet<String, String, Category> showAndReturn(){
    	Optional<Triplet<String, String, Category>> result = dialog.showAndWait();

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

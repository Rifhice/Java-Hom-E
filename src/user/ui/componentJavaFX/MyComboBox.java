package user.ui.componentJavaFX;
import java.util.ArrayList;

import javafx.scene.control.ComboBox;
public class MyComboBox extends ComboBox<String>{

	public MyComboBox(ArrayList<String> options) {
		getItems().addAll(
			    options
			);
	}
	
}

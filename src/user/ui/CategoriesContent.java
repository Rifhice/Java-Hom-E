package user.ui;

import javafx.scene.control.Label;

public class CategoriesContent extends Content {

	private static CategoriesContent content = null;

	private CategoriesContent() {
		this.getChildren().add(new Label("Categories"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new CategoriesContent();
		}
		return content;
	}
}

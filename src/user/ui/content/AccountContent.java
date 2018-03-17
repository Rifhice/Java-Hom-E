package user.ui.content;

import javafx.scene.control.Label;

public class AccountContent extends Content {

	private static AccountContent content = null;

	private AccountContent() {
		this.getChildren().add(new Label("Account"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new AccountContent();
		}
		return content;
	}
}

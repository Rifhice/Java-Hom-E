package user.ui.content;
import javafx.scene.control.Label;

public class HomeContent extends Content {

	private static HomeContent content = null;

	private HomeContent() {
		this.getChildren().add(new Label("Home"));
	}

	public static Content getInstance() {
		// TODO Auto-generated method stub
		if(content == null) {
			content = new HomeContent();
		}
		return content;
	}
	
    public void updateData() {

    }
	
	@Override
	public void handleMessage(Object message) {
		
	}
}

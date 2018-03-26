package user.ui.componentJavaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import user.models.Right;
import user.tools.GraphicalCharter;

public class RightCell extends MyPane{
	
	MyRectangle labelButtonBounds;
	MyRectangle modifyButtonBounds;
	
	MyButtonFX labelButton = null;
	MyButtonFX modifyButton = null;
	
	Right Right;
	
	EventHandler<ActionEvent> modifyRightAction;
	

	public RightCell(Right Right, double width, double height, EventHandler<ActionEvent> infoEvent) {
		super(width, height);
		this.Right = Right;
		this.modifyRightAction = infoEvent;
		updateCell();
	}
	
	public void updateCell() {
			labelButtonBounds = new MyRectangle(0f, 0f, 0.76f, 1f);
			modifyButtonBounds = new MyRectangle(0.76f, 0f, 0.2f, 1f);

		if(labelButton != null) {
			this.getChildren().remove(labelButton);
		}
		if(modifyButton != null) {
			this.getChildren().remove(modifyButton);
		}
		labelButton = new MyButtonFX(Right.getDenomination(), Right.getId(), labelButtonBounds.computeBounds(getPrefWidth(), getPrefHeight()), modifyRightAction);
		
		this.getChildren().add(labelButton);
		
		labelButton.setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	

}

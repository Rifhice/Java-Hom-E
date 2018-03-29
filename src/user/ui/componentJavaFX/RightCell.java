package user.ui.componentJavaFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import user.models.Right;
import user.tools.GraphicalCharter;

public class RightCell extends MyPane{
	
	private static Image redArrow = new Image("file:asset/images/redArrow.png");
	private static Image greenArrow = new Image("file:asset/images/greenArrow.png");
	
	MyRectangle labelButtonBounds;
	MyRectangle modifyButtonBounds;
	
	MyButtonFX labelButton = null;
	MyButtonImage modifyButton = null;
	
	Right Right;
	Boolean allowed;
	
	Image arrow;
	
	EventHandler<ActionEvent> modifyRightAction;
	

	public RightCell(boolean allowed, Right Right, double width, double height, EventHandler<ActionEvent> infoEvent) {
		super(width, height);
		this.Right = Right;
		this.modifyRightAction = infoEvent;
		this.allowed = allowed;
		updateCell();
	}
	
	public void updateCell() {
			labelButtonBounds = new MyRectangle(0f, 0f, 0.76f, 1f);
			modifyButtonBounds = new MyRectangle(0.76f, 0f, 0.2f, 1f);
			if(allowed) {
				labelButtonBounds = new MyRectangle(0.2f, 0f, 0.76f, 1f);
				modifyButtonBounds = new MyRectangle(0f, 0f, 0.2f, 1f);
				arrow = RightCell.redArrow;
			} else {
				labelButtonBounds = new MyRectangle(0f, 0f, 0.76f, 1f);
				modifyButtonBounds = new MyRectangle(0.76f, 0f, 0.2f, 1f);
				arrow = RightCell.greenArrow;
			}
		if(labelButton != null) {
			this.getChildren().remove(labelButton);
		}
		if(modifyButton != null) {
			this.getChildren().remove(modifyButton);
		}
		
		modifyButton = new MyButtonImage(arrow, modifyButtonBounds.computeBounds(getPrefWidth(), getPrefHeight()), modifyRightAction, Right.getId());
		this.getChildren().add(modifyButton);
		
		labelButton = new MyButtonFX(Right.getDenomination(), Right.getId(), labelButtonBounds.computeBounds(getPrefWidth(), getPrefHeight()), modifyRightAction);
		this.getChildren().add(labelButton);
		
		labelButton.setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
	}
	
	

}

package user.ui.componentJavaFX;
import java.awt.geom.Rectangle2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import user.tools.GraphicalCharter;

/**
 * Custom Home-E text area.
 * By default : 
 *  - words wrap activated
 *  - size and layout rounded (fix blurry bug in some cases) 
 * @author Clm-Roig
 *
 */
public class MyTextArea extends TextArea{
	
	public MyTextArea(String text,Rectangle2D.Float bounds) {	
		// Round the width & height to resolve "blurry" textArea bug
		DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        
		int widthRounded = Integer.parseInt(df.format(bounds.getWidth()));
	    int heightRounded = Integer.parseInt(df.format(bounds.getHeight()));
	    int xRounded = Integer.parseInt(df.format(bounds.getX()));
	    int yRounded = Integer.parseInt(df.format(bounds.getY()));

	    setPrefWidth(widthRounded);
        setPrefHeight(heightRounded);
        setLayoutX(xRounded);
        setLayoutY(yRounded);
        
        setText(text);
        setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE));
        setWrapText(true);
	}
	
	public MyTextArea(String text,Rectangle2D.Float bounds,float fontMultiplier) {	
		// Round the width & height to resolve "blurry" textArea bug
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(0);
        
        int widthRounded = Integer.parseInt(df.format(bounds.getWidth()));
        int heightRounded = Integer.parseInt(df.format(bounds.getHeight()));
        int xRounded = Integer.parseInt(df.format(bounds.getX()));
        int yRounded = Integer.parseInt(df.format(bounds.getY()));
        
        setPrefWidth(widthRounded);
        setPrefHeight(heightRounded);
        setLayoutX(xRounded);
        setLayoutY(yRounded);
        
        setText(text);
        setFont(Font.font(GraphicalCharter.FONT,GraphicalCharter.FONT_SIZE * fontMultiplier));
        setWrapText(true);
	}
	
	public MyTextArea lockText() {
		setEditable(false);
		return this;
	}
	
}

package user.ui.componentJavaFX;

import java.awt.geom.Rectangle2D;

public class MyRectangle extends Rectangle2D.Float{

	public MyRectangle(float x, float y, float width, float height) {
		super(x,y,width,height);
	}
	
	public Rectangle2D.Float computeBounds(double width,double height){
		return new Rectangle2D.Float((float)(getX() * width), (float)(getY() * height), (float)(getWidth() * width), (float)(getHeight() * height));
	}
	
	public Rectangle2D.Float computeBounds(double x, double y,double width,double height){
		return new Rectangle2D.Float((float)(getX() * width + x), (float)(getY() * height + y), (float)(getWidth() * width), (float)(getHeight() * height));
	}
}

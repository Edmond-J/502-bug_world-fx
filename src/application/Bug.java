package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bug extends ImageView {
	double speed;
	double width;
	double height;
	double dx, dy;
	double direction;
	double offset;
	boolean pose;

	public Bug() {
		// TODO Auto-generated constructor stub
	}

	public Bug(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public Bug(Image image, double s) {
		super(image);
		speed = s;
		width = image.getWidth();
		height = image.getHeight();
		setTranslateX(Math.random()*0.8*1800);
		setTranslateY(Math.random()*0.8*1200);
		direction = Math.random()*2*Math.PI;
	}

	public void changeDirection(double gameSpeed) {
		if (Math.random() > 0.85)
			offset = (Math.random()-0.5)*0.2;
//		if (pose && offset > 0.4)
//			offset = -Math.abs(offset);
//		if (!pose && offset > 0.4)
//			offset = Math.abs(offset);
//		pose = offset > 0;
		if (Math.random() > 0.75)
			direction += offset*2;
//		else if (Math.random() > 0.5)
//			direction += offset*0.2;
		setRotate(direction/Math.PI*180+90);
		dx = Math.cos(direction)*speed*gameSpeed;// (speed*(0.5+Math.random()*0.5))
		dy = Math.sin(direction)*speed*gameSpeed;
		setTranslateX(getTranslateX()+dx);
		setTranslateY(getTranslateY()+dy);
//		setCenterX(getCenterX()+dx);
//		setCenterY(getCenterY()+dy);
	}
}

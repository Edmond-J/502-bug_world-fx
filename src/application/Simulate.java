package application;

import javafx.scene.shape.Circle;

public class Simulate extends Circle {
//	double x=100, y=100;
//	double r=30;
//	double width, height;
	double speed=5;
	double dx, dy;
	double direction = 2*Math.PI;

	public Simulate(double x, double y, double r) {
		super(x, y, r);
		// TODO Auto-generated constructor stub
	}

	public void changeDirection() {
		if (Math.random() > 0.95)
			direction += (Math.random()-0.5)*0.8;
		else if (Math.random() > 0.9)
			direction += (Math.random()-0.5)*0.4;
		dx=Math.cos(direction)*speed;//(speed*(0.5+Math.random()*0.5))
		dy=Math.sin(direction)*speed;
		setCenterX(getCenterX()+dx);
		setCenterY(getCenterY()+dy);
	}
}

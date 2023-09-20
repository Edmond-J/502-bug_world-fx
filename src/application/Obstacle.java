package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends ImageView {
	double width;
	double height;
	public Obstacle(Image image) {
		super(image);
		width = image.getWidth();
		height = image.getHeight();
		setTranslateX(Math.random()*0.8*1800);
		setTranslateY(Math.random()*0.8*1200);
	}
}

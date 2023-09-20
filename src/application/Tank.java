package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tank extends ImageView {
	int speed;
	int cannon;
	double speedUp;
	double armor;
	boolean shield;

	public Tank() {
		// TODO Auto-generated constructor stub
	}

	public Tank(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public Tank(Image image) {
		super(image);
		speed = 8;
		armor=100;
		// TODO Auto-generated constructor stub
	}
}

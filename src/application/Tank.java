package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tank extends ImageView {
	static int speed = 8;
	static int cannon;
	static double speedUp;
	static int armor = 100;
	static boolean shield;
	static double rotate;
	static double lastFire;
	static double lastAttacked;
	static double fireInterval = 0.2;
	static double damage=10;

	public Tank() {
		// TODO Auto-generated constructor stub
	}

	public Tank(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public Tank(Image image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
}

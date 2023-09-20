package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Missile extends ImageView {
	private double rotate;
	private double speed;
	private int damage=8;

	public Missile(Image image, double x, double y, double rotate) {
		super(image);
		speed=25;
		this.rotate=rotate;
		
		setLayoutX(x);
		setLayoutY(y);
	}
	
	public Bug hitBug(){
		return null;
		
	}
	
	public void hitTank(){
		
	}
	
	public void flying(){
		if(rotate==0){
//			setRotate(0);
			setLayoutY(getLayoutY()-speed);
		}else if(rotate==270){
//			setRotate(270);
			setLayoutX(getLayoutX()-speed);
		}else if(rotate==180){
//			setRotate(180);
			setLayoutY(getLayoutY()+speed);
		}else if((rotate==90)){
//			setRotate(90);
			setLayoutX(getLayoutX()+speed);
		}
	}

	public int getDamage() {
		return damage;
	}
	
	
}

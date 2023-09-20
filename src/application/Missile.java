package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Missile extends ImageView {
	double rotate;
	double speed;
	double damage=8;

	public Missile() {
		// TODO Auto-generated constructor stub
	}

	public Missile(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

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
}

package application;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Bug extends ImageView {
	private String name;
	private double width;
	private double height;
	private double direction;
	private double offset;
	private boolean pose;
	private double speed;
	private int HP;
	private int damage;
	private double lastHit;
	Label label;

	public Bug(Image image, double s,int hp, int dam) {
		super(image);
		name=image.getUrl();
		speed = s;
		width = image.getWidth();
		height = image.getHeight();
		setTranslateX(Math.random()*0.8*BugWorldFX_Main.width);
		setTranslateY(Math.random()*0.8*BugWorldFX_Main.height);
		direction = Math.random()*2*Math.PI;// the direction used for rotating the image is in angle unit
		HP=hp;
		damage=dam;
		label=new Label(""+HP);
		label.setTextFill(Color.DARKGRAY);
		Font font=new Font("Microsoft YaHei Bold",15);
		label.setFont(font);
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
		double dx = Math.cos(direction)*speed*gameSpeed;//the global game speed will take affect here.
		double dy = Math.sin(direction)*speed*gameSpeed;
		setTranslateX(getTranslateX()+dx);
		setTranslateY(getTranslateY()+dy);
		label.setTranslateX(getTranslateX()+width/2);
		label.setTranslateY(getTranslateY()-25);
		label.setText(""+HP);
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public double getLastHit() {
		return lastHit;
	}

	public void setLastHit(double lastHit) {
		this.lastHit = lastHit;
	}

	public int getDamage() {
		return damage;
	}

//	public void showHP() {
//		Rectangle HPbox = new Rectangle(getLayoutX(), getLayoutY()+110, width, 16);
//		HPbox.setFill(Color.GREEN);
//		HPbox.setTranslateX(getTranslateX());
//		HPbox.setTranslateY(getTranslateY());
//	}
	
	
}

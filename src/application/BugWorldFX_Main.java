package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BugWorldFX_Main extends Application {
	static double width = 1920;
	static double height = 1080;
	static double gameSpeed = 1.0;
	static boolean pause = false;
	static int bugQuantity = 1;

	public ArrayList<Bug> generateBugs() {
		ArrayList<Bug> bugList = new ArrayList<>();
		for (int i = 0; i < bugQuantity; i++) {
			bugList.add(new Bug(new Image("img/butterfly1.png"), 10, 20, 8));// speed, HP, damage
			bugList.add(new Bug(new Image("img/butterfly2.png"), 8, 20, 10));
			bugList.add(new Bug(new Image("img/beetle1.png"), 5, 30, 6));
			bugList.add(new Bug(new Image("img/beetle2.png"), 4, 35, 8));
			bugList.add(new Bug(new Image("img/bee.png"), 12, 25, 12));
			bugList.add(new Bug(new Image("img/moth.png"), 7, 40, 5));
			bugList.add(new Bug(new Image("img/ladybug.png"), 3, 15, 10));
		}
		return bugList;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Timeline timeline = new Timeline();
		ArrayList<Bug> bugList = generateBugs();
		ArrayList<Missile> misList = new ArrayList<Missile>();
		Tank tank = new Tank(new Image("img/tank.png"));
//		double factor=1/1.75;
//		Scale scale = new Scale(factor, factor);
		tank.setFitWidth(96);
		tank.setFitHeight(96);
		tank.setTranslateX(width/2);
		tank.setTranslateY(height/2);
		System.out.println("start");
		Rectangle tankHP = new Rectangle(0, 110, 96, 9);//translateY会在初始化Y的基础上增加，所以110不能省略
		tankHP.setTranslateX(width/2);//translateX会在初始化X的基础上增加
		tankHP.setTranslateY(height/2);
		tankHP.setFill(Color.LIGHTGRAY);
		Rectangle currentHP = new Rectangle(0, 110, 96*Tank.armor/100, 9);
		currentHP.setTranslateX(width/2);
		currentHP.setTranslateY(height/2);
		currentHP.setFill(Color.GREEN);
		Simulate circle1 = new Simulate(200, 200, 20);
		circle1.setFill(Color.LIGHTSALMON);
//		Group gameGroup = new Group();// 也可以直接加到game pane里
		Pane container = new Pane();
		Pane game = new Pane();
		game.getChildren().add(circle1);
		game.getChildren().add(tank);
		game.getChildren().add(tankHP);
		game.getChildren().add(currentHP);
		for (Bug b : bugList) {
			game.getChildren().add(b);
			game.getChildren().add(b.label);
		}
		for (int i = 0; i < 5; i++) {
			game.getChildren().add(new Obstacle(new Image("img/tree.png")));
		}
		game.getChildren().add(new Obstacle(new Image("img/home.png")));
		game.getChildren().add(new Obstacle(new Image("img/house.png")));
		Pane gameOver = FXMLLoader.load(getClass().getResource("gameOver.fxml"));
		gameOver.setVisible(false);
		gameOver.setTranslateX(width/2-300);
		gameOver.setTranslateY(height/2-200);
		Pane setting = FXMLLoader.load(getClass().getResource("setting.fxml"));// new Pane(settingGroup);
		setting.setVisible(false);
		setting.setStyle("-fx-background-color: lightgray;");
		container.getChildren().addAll(game, setting, gameOver);
//		container.getTransforms().add(scale);
		KeyFrame frame = new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (circle1.getCenterX() < circle1.getRadius() || circle1.getCenterX()+circle1.getRadius() > width) {
					circle1.direction = 2*Math.PI-circle1.direction;// when the circle touch the edge, it will bounce back
																	// at the right angle
				}
				if (circle1.getCenterY() < circle1.getRadius() || circle1.getCenterY()+circle1.getRadius() > height) {
					circle1.direction = 2*Math.PI-circle1.direction;// touching the Y edge is different with touching
																	// the X edge
				}
				if ((circle1.getCenterX()-tank.getTranslateX()-48)*(circle1.getCenterX()-tank.getTranslateX()-48)
						+(circle1.getCenterY()-tank.getTranslateY()-48)*(circle1.getCenterY()-tank.getTranslateY()-48) <= 50
								*50) {
					circle1.direction = Math.PI-circle1.direction;
				}
				for (Bug b : bugList) {
					b.changeDirection(gameSpeed);
					if (b.getTranslateX() < 0 || b.getTranslateX()+200 > width) {
						b.setDirection(Math.PI-b.getDirection());
						b.setTranslateX(Math.min(width-200,Math.max(0, b.getTranslateX())));
					}
					if (b.getTranslateY() < 0 || b.getTranslateY()+100 > height) {
						b.setDirection(2*Math.PI-b.getDirection());
						b.setTranslateY(Math.min(height-100,Math.max(0, b.getTranslateY())));
					}
					double dx = b.getTranslateX()-tank.getTranslateX();
					double dy = b.getTranslateY()-tank.getTranslateY();
					if (dx*dx+dy*dy <= 100*100 && System.currentTimeMillis()-Tank.lastAttacked > 300) {
						Tank.armor -= b.getDamage();
						Tank.lastAttacked = System.currentTimeMillis();// there is a min interval for being attacked
						System.out.println(Tank.armor);
						currentHP.setWidth(96*Tank.armor/100);
						if (Tank.armor <= 0) {
							timeline.stop();// stop the game
							gameOver.setVisible(true);
//							System.out.println("game over");
						}
					}
				}
				for (int i = 0; i < misList.size(); i++) {
					misList.get(i).flying();
					Iterator<Bug> iterator = bugList.iterator();
					while (iterator.hasNext()) {
						Bug b = iterator.next();
						double dx = b.getTranslateX()-misList.get(i).getLayoutX();
						double dy = b.getTranslateY()-misList.get(i).getLayoutY();
						if (dx*dx+dy*dy <= 60*60 && System.currentTimeMillis()-b.getLastHit() > 200) {
//							System.out.println("hit");
							b.setLastHit(System.currentTimeMillis());
							b.setHP(b.getHP()-misList.get(i).getDamage());
							if (b.getHP() <= 0) {
								game.getChildren().remove(b);
								game.getChildren().remove(b.label);
								iterator.remove();
							}
//							misList.remove(misList.get(i));
							game.getChildren().remove(misList.get(i));
						}
					}
				}
				circle1.changeDirection();
			}
		});
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.pause();
		Scene scene = new Scene(container, width, height);
		scene.setOnKeyPressed(e -> {
			if (Tank.armor <= 0)
				return;
			if (e.getCode() == KeyCode.UP) {
				tank.setRotate(0);
				Tank.rotate = 0;
				tank.setTranslateY(tank.getTranslateY()-Tank.speed);
			} else if (e.getCode() == KeyCode.LEFT) {
				tank.setRotate(270);
				Tank.rotate = 270;
				tank.setTranslateX(tank.getTranslateX()-Tank.speed);
			} else if (e.getCode() == KeyCode.DOWN) {
				tank.setRotate(180);
				Tank.rotate = 180;
				tank.setTranslateY(tank.getTranslateY()+Tank.speed);
			} else if (e.getCode() == KeyCode.RIGHT) {
				tank.setRotate(90);
				Tank.rotate = 90;
				tank.setTranslateX(tank.getTranslateX()+Tank.speed);
			} else if (e.getCode() == KeyCode.ESCAPE) {// press ESC will pause the game and show the setting option
				pause = !pause;
				if (!pause) {
					timeline.play();
					setting.setVisible(false);
				} else {
					timeline.pause();
					setting.setVisible(true);
				}
			} else if (e.getCode() == KeyCode.SPACE && System.currentTimeMillis()-Tank.lastFire > 500) {
				Missile missile = new Missile(new Image("img/missile.png"), tank.getTranslateX()+24, tank.getTranslateY(),
						Tank.rotate);
//				System.out.println(missile.getX());
//				System.out.println(missile.getLayoutX());
//				System.out.println(missile.getTranslateX());
//				missile.setTranslateX(tank.getTranslateX()+24);
//				missile.setTranslateY(tank.getTranslateY());
				missile.setRotate(Tank.rotate);// rotate the missile pic so that it face the same direction with the gun
				Tank.lastFire = System.currentTimeMillis();// record the tank fire time for next fire validate
				game.getChildren().add(missile);
				misList.add(missile);
			}
			tankHP.setTranslateX(tank.getTranslateX());// the HP box of the tank
			tankHP.setTranslateY(tank.getTranslateY());
			currentHP.setTranslateX(tank.getTranslateX());
			currentHP.setTranslateY(tank.getTranslateY());
			
		});
		Image icon = new Image("img/ladybug.png");
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Bug World FX");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

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
import javafx.scene.Group;
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
			bugList.add(new Bug(new Image("butterfly1.png"), 10, 20, 8));// speed, HP, damage
			bugList.add(new Bug(new Image("butterfly2.png"), 8, 20, 10));
			bugList.add(new Bug(new Image("beetle1.png"), 5, 30, 6));
			bugList.add(new Bug(new Image("beetle2.png"), 4, 35, 8));
			bugList.add(new Bug(new Image("bee.png"), 12, 25, 12));
			bugList.add(new Bug(new Image("moth.png"), 7, 40, 5));
			bugList.add(new Bug(new Image("ladybug.png"), 3, 15, 10));
		}
		return bugList;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Timeline timeline = new Timeline();
		ArrayList<Bug> bugList = generateBugs();
		ArrayList<Missile> misList = new ArrayList<Missile>();
		Tank tank = new Tank(new Image("tank.png"));
		tank.setFitWidth(96);
		tank.setFitHeight(96);
		tank.setLayoutX(width/2);
		tank.setLayoutY(height/2);
		Rectangle tankHP = new Rectangle(tank.getX(), tank.getY()+110, 96, 9);
		tankHP.setFill(Color.LIGHTGRAY);
		Rectangle currentHP = new Rectangle(tank.getX(), tank.getY()+110, 96*Tank.armor/100, 9);
		currentHP.setFill(Color.GREEN);
		Simulate circle1 = new Simulate(200, 200, 20);
		circle1.setFill(Color.LIGHTSALMON);
		Group gameGroup = new Group();
		gameGroup.getChildren().add(circle1);
		gameGroup.getChildren().add(tank);
		gameGroup.getChildren().add(tankHP);
		gameGroup.getChildren().add(currentHP);
		for (Bug b : bugList) {
			gameGroup.getChildren().add(b);
			gameGroup.getChildren().add(b.label);
		}
		for (int i = 0; i < 5; i++) {
			gameGroup.getChildren().add(new Obstacle(new Image("tree.png")));
		}
		gameGroup.getChildren().add(new Obstacle(new Image("home.png")));
		gameGroup.getChildren().add(new Obstacle(new Image("house.png")));
//		Group settingGroup = new Group();
//		Button settingButton = new Button("Apply");
//		settingGroup.getChildren().add(settingButton);
		Pane container = new Pane();
		Pane game = new Pane(gameGroup);
		Pane setting = FXMLLoader.load(getClass().getResource("setting.fxml"));// new Pane(settingGroup); 
//		setting.setVisible(false);
		setting.setStyle("-fx-background-color: lightgray;");
		container.getChildren().addAll(game, setting);
		KeyFrame frame = new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (circle1.getCenterX() < circle1.getRadius() || circle1.getCenterX()+circle1.getRadius() > width) {
					circle1.direction = Math.PI-circle1.direction;
				}
				if (circle1.getCenterY() < circle1.getRadius() || circle1.getCenterY()+circle1.getRadius() > height) {
					circle1.direction = 2*Math.PI-circle1.direction;
				}
				if ((circle1.getCenterX()-tank.getLayoutX()-48)*(circle1.getCenterX()-tank.getLayoutX()-48)
						+(circle1.getCenterY()-tank.getLayoutY()-48)*(circle1.getCenterY()-tank.getLayoutY()-48) <= 50
								*50) {
					circle1.direction = Math.PI-circle1.direction;
				}
				for (Bug b : bugList) {
					b.changeDirection(gameSpeed);
					if (b.getTranslateX() < 50 || b.getTranslateX()+50 > width) {
						b.setDirection(Math.PI-b.getDirection());
					}
					if (b.getTranslateY() < 50 || b.getTranslateY()+50 > height) {
						b.setDirection(2*Math.PI-b.getDirection());
					}
					double dx = b.getTranslateX()-tank.getLayoutX();
					double dy = b.getTranslateY()-tank.getLayoutY();
					if (dx*dx+dy*dy <= 100*100 && System.currentTimeMillis()-Tank.lastAttacked > 300) {
						Tank.armor -= b.getDamage();
						Tank.lastAttacked = System.currentTimeMillis();
						System.out.println(Tank.armor);
						currentHP.setWidth(96*Tank.armor/100);
//						Rectangle currentHP = new Rectangle(tank.getX(), tank.getY()+110, 96*Tank.armor/100, 9);
//						currentHP.setFill(Color.GREEN);
						if (Tank.armor <= 0) {
							timeline.stop();
							System.out.println("game over");
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
								gameGroup.getChildren().remove(b);
								gameGroup.getChildren().remove(b.label);
								iterator.remove();
							}
//							misList.remove(misList.get(i));
							gameGroup.getChildren().remove(misList.get(i));
						}
					}
				}
				circle1.changeDirection();
//				HPbox.setTranslateX(tank.getTranslateX());
//				HPbox.setTranslateY(tank.getTranslateY());
			}
		});
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.pause();
		Scene scene = new Scene(container, width, height);
		scene.setOnKeyPressed(e -> {
			if (Tank.armor <= 0)
				return;
			tankHP.setLayoutX(tank.getLayoutX());
			tankHP.setLayoutY(tank.getLayoutY());
			currentHP.setLayoutX(tank.getLayoutX());
			currentHP.setLayoutY(tank.getLayoutY());
			if (e.getCode() == KeyCode.UP) {
				tank.setRotate(0);
				Tank.rotate = 0;
				tank.setLayoutY(tank.getLayoutY()-Tank.speed);
			} else if (e.getCode() == KeyCode.LEFT) {
				tank.setRotate(270);
				Tank.rotate = 270;
				tank.setLayoutX(tank.getLayoutX()-Tank.speed);
			} else if (e.getCode() == KeyCode.DOWN) {
				tank.setRotate(180);
				Tank.rotate = 180;
				tank.setLayoutY(tank.getLayoutY()+Tank.speed);
			} else if (e.getCode() == KeyCode.RIGHT) {
				tank.setRotate(90);
				Tank.rotate = 90;
				tank.setLayoutX(tank.getLayoutX()+Tank.speed);
//				tankHP.setTranslateX(tank.getX());
//				tankHP.setTranslateY(tank.getY()+110);
			} else if (e.getCode() == KeyCode.ESCAPE) {
				pause = !pause;
				if (!pause) {
					timeline.play();
					setting.setVisible(false);
				} else {
					timeline.pause();
					setting.setVisible(true);
				}
			} else if (e.getCode() == KeyCode.SPACE && System.currentTimeMillis()-Tank.lastFire > 500) {
				Missile missile = new Missile(new Image("missile.png"), tank.getLayoutX()+24, tank.getLayoutY(),
						Tank.rotate);
				missile.setRotate(Tank.rotate);
				Tank.lastFire = System.currentTimeMillis();
				gameGroup.getChildren().add(missile);
				misList.add(missile);
			}
//			System.out.println("lay:"+tank.getLayoutX());
//			System.out.println("x:"+tank.getX());
//			System.out.println("tran:"+tank.getTranslateX());
		});
		Image icon = new Image("ladybug.png");
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

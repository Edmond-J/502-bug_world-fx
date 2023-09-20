package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class BugWorldFX_Main extends Application {
//	@FXML
//	Spinner quantitySpinner;
//	Slider speedSlider;
	static double width = 1920;
	static double height = 1080;
	static double gameSpeed=0.8;
	static boolean pause = false;
	static int bugQuantity = 1;

	public ArrayList<Bug> generateBugs() {
		ArrayList<Bug> bugList = new ArrayList<>();
		for (int i = 0; i < bugQuantity; i++) {
			bugList.add(new Bug(new Image("butterfly1.png"), 10));
			bugList.add(new Bug(new Image("butterfly2.png"), 8));
			bugList.add(new Bug(new Image("beetle1.png"), 5));
			bugList.add(new Bug(new Image("beetle2.png"), 4));
			bugList.add(new Bug(new Image("bee.png"), 12));
			bugList.add(new Bug(new Image("moth.png"), 7));
			bugList.add(new Bug(new Image("ladybug.png"), 3));
		}
		return bugList;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Tank tank = new Tank(new Image("tank.png"));
		tank.setFitWidth(96);
		tank.setFitHeight(96);
		tank.setX(width/2);
		tank.setY(height/2);
//		ProgressBar tankHP = new ProgressBar();
//		tankHP.setPrefWidth(96);
//		tankHP.setPrefHeight(16);
//		tankHP.setLayoutX(tank.getX());
//		tankHP.setLayoutY(tank.getY()+110);
		ArrayList<Bug> bugList = generateBugs();
		Simulate circle1 = new Simulate(200, 200, 20);
		Group gameGroup = new Group();
		gameGroup.getChildren().add(circle1);
		gameGroup.getChildren().add(tank);
//		gameGroup.getChildren().add(tankHP);
		for (Bug b : bugList) {
			gameGroup.getChildren().add(b);
		}
		Group settingGroup = new Group();
		Button settingButton = new Button("Apply");
		settingGroup.getChildren().add(settingButton);
		Pane container = new Pane();
		Pane game = new Pane(gameGroup);
		Pane setting = FXMLLoader.load(getClass().getResource("setting.fxml"));// new Pane(settingGroup); // //
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
				for (Bug b : bugList) {
					if (b.getTranslateX()-10 < 0 || b.getTranslateX()+b.width > width) {
						b.direction = Math.PI-b.direction;
					}
					if (b.getTranslateY()-10 < 0 || b.getTranslateY()+b.height > height) {
						b.direction = 2*Math.PI-b.direction;
					}
					b.changeDirection(gameSpeed);
				}
				circle1.changeDirection();
			}
		});
		Timeline timeline = new Timeline();
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.pause();
		Scene scene = new Scene(container, width, height);
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.W) {
				tank.setRotate(0);
				tank.setLayoutY(tank.getLayoutY()-10);
			} else if (e.getCode() == KeyCode.A) {
				tank.setRotate(270);
				tank.setLayoutX(tank.getLayoutX()-10);
			} else if (e.getCode() == KeyCode.S) {
				tank.setRotate(180);
				tank.setLayoutY(tank.getLayoutY()+10);
			} else if (e.getCode() == KeyCode.D) {
				tank.setRotate(90);
				tank.setLayoutX(tank.getLayoutX()+10);
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
			}
		});
		Image icon = new Image("ladybug.png");
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Bug World FX");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

//	public void setGameSpeed(ActionEvent e) {
//		double speed=speedSlider.getValue();
//		System.out.println(speed);
//	}

	public static void main(String[] args) {
		launch(args);
	}
}

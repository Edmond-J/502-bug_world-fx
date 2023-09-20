package application;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class KeyboardEventHandler implements EventHandler<KeyEvent> {
	public KeyboardEventHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(KeyEvent event) {
		ImageView tank = (ImageView)event.getSource();
		System.out.println("clicked");
//		switch (event.getCode()) {
//		case A -> {
//			System.out.println("a pressed");
//		}
//		}
//		tank.setRotate(Math.PI*180+90);
		// TODO Auto-generated method stub
	}
}

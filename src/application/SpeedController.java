package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class SpeedController implements Initializable {
	@FXML
	Slider speedSlider;
	@FXML
	Label speedLabel;

	public SpeedController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		speedLabel.setText("1.0");
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override// line 24~line 28 are learned from YouTube Course(2:14:00):
						// https://www.youtube.com/watch?v=9XJicRt_FaI
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				System.out.println(speedSlider.getValue());
				double value = speedSlider.getValue();
				value = (double)((int)(value*100))/100;
				BugWorldFX_Main.gameSpeed = value;
				speedLabel.setText(Double.toString(value));
			}
		});
	}
}

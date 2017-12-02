package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {
	public boolean simpleInterest = true;
	private static final String SimpleInterest = "Simple Interest     ";
	private static final String CompoundInterest = "Compound Interest     ";

	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			Scene scene = new Scene(root, 590, 370);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			/*
			 * Title box
			 */
			Label titleLabel = new Label(" Financial Calculator 1.0");
			titleLabel.setId("login-title");

			HBox titleBox = new HBox();
			titleBox.getChildren().add(titleLabel);
			titleBox.setAlignment(Pos.CENTER);

			/*
			 * Error Message Box
			 */
			HBox errorBox = new HBox();
			Label errorField = new Label("");
			errorBox.setMinHeight(30);
			errorField.setId("error-field");
			errorBox.getChildren().add(errorField);
			errorBox.setAlignment(Pos.BOTTOM_LEFT);
			
			
			/*
			 * Create text field and label for time
			 */
			HBox timeBox = new HBox();
			TextField timeField = new TextField();
			timeField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					timeField.setText(newValue);
				}
			});

			Label timeLabel = new Label("Time in months");
			timeLabel.setId("time-label");

			timeBox.setSpacing(5);
			timeBox.getChildren().add(timeField);
			timeBox.getChildren().add(timeLabel);
			timeBox.setPadding(new Insets(10));

			/*
			 * Create text field and label for PRINCIPAL amount
			 */
			HBox principalBox = new HBox();
			TextField principalField = new TextField();
			principalField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					principalField.setText(newValue);
				}
			});

			Label principalLabel = new Label("Principle Amount");
			principalLabel.setId("principal-label");

			principalBox.setSpacing(5);
			principalBox.getChildren().add(principalField);
			principalBox.getChildren().add(principalLabel);
			principalBox.setPadding(new Insets(10));

			/*
			 * Create slider
			 */
			HBox sliderBox = new HBox();
			Label sliderLabelText = new Label("Interest Rate: ");
			Label sliderValueText = new Label("50%");
			sliderLabelText.setId("interest-label");
			sliderValueText.setId("interest-label");
			Slider slider = new Slider();
			slider.setPrefWidth(300);
			slider.setMin(0);
			slider.setMax(100);
			slider.setValue(50);
			slider.setShowTickLabels(true);
			slider.setShowTickMarks(true);
			slider.setMajorTickUnit(50);
			slider.setMinorTickCount(5);
			slider.setBlockIncrement(10);

			slider.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					if (new_val.floatValue() < 10) {
						sliderValueText.setText('0' + String.format("%.2f", new_val) + '%');
					} else {
						sliderValueText.setText(String.format("%.2f", new_val) + '%');
					}
				}
			});

			sliderBox.setSpacing(5);
			sliderBox.getChildren().add(sliderLabelText);
			sliderBox.getChildren().add(sliderValueText);
			sliderBox.getChildren().add(slider);
			sliderBox.setAlignment(Pos.CENTER_LEFT);
			sliderBox.setPadding(new Insets(10));

			/*
			 * Create text field and label for CALCULATED amount
			 */
			HBox calculatedBox = new HBox();
			Text calculatedLabel = new Text("Calculated Amount:");
			TextField calculatedField = new TextField();
			calculatedField.setId("calculated-amount");
			calculatedField.setDisable(false);
			
			calculatedBox.setSpacing(5);
			calculatedBox.getChildren().add(calculatedLabel);
			calculatedBox.getChildren().add(calculatedField);
			calculatedBox.setPadding(new Insets(10));
			calculatedBox.setAlignment(Pos.CENTER);
			calculatedBox.setDisable(false);

			/*
			 * Create option for simple interest or compound interest mode
			 */
			HBox interestTextBox = new HBox();
			Text curInterestText = new Text("Interest Mode: ");
			curInterestText.setId("interest-text");
			Text curInterestRate = new Text(SimpleInterest);
			curInterestRate.setId("interest-mode");
			Button changeInterestBtn = new Button("Click to change interest mode");
			changeInterestBtn.setId("interest-button");
			changeInterestBtn.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent me) {
					simpleInterest = !simpleInterest;
					if (simpleInterest) {
						curInterestRate.setText(SimpleInterest);
					} else {
						curInterestRate.setText(CompoundInterest);
					}

				}

			});

			interestTextBox.getChildren().add(curInterestText);
			interestTextBox.getChildren().add(curInterestRate);
			interestTextBox.getChildren().add(changeInterestBtn);
			interestTextBox.setPadding(new Insets(10));

			/*
			 * Create Click to compute button box
			 */
			HBox computeBtnBox = new HBox();
			Button computeButton = new Button("Click to Calculate");
			computeButton.setId("compute-button");
			computeButton.setOnMouseReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					if (simpleInterest) {
						try {
							double principal = Double.parseDouble(principalField.getText());
							double time = Double.parseDouble(timeField.getText());
							double interestRate = slider.getValue()/100;
							
							calculatedField.setText(String.valueOf(principal*(1.0+interestRate*time)));
							errorField.setText("");

						} catch (NumberFormatException nfe) {
							errorField.setText("Invalid Input: enter numbers only");
						}
					} else {
						try {
							double principal = Double.parseDouble(principalField.getText());
							double time = Double.parseDouble(timeField.getText());
							double interestRate = slider.getValue()/100;

							calculatedField.setText(String.valueOf(principal*Math.pow((1.0+interestRate), time)));
							errorField.setText("");

						} catch (NumberFormatException nfe) {
							errorField.setText("Invalid Input: enter numbers only");
						}
					}

				}

			});

			computeBtnBox.getChildren().add(computeButton);
			computeBtnBox.setAlignment(Pos.CENTER);

			/**
			 * Space between principal box and slider HBox space2 = new HBox();
			 * space2.setMinHeight(60);
			 **/

			root.getChildren().add(titleBox);
			root.getChildren().add(errorBox);
			root.getChildren().add(timeBox);
			root.getChildren().add(principalBox);
			root.getChildren().add(sliderBox);
			root.getChildren().add(interestTextBox);
			root.getChildren().add(calculatedBox);
			root.getChildren().add(computeBtnBox);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

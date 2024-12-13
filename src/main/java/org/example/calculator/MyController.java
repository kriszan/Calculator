package org.example.calculator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.script.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class MyController {
    @FXML
    private Label outputLabel;
    @FXML
    private TextField inputRight, inputLeft;

    private static Map<String, BiFunction<Double, Double, Double>> operators;

    public void initialize() {

         operators = new HashMap<>();

        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> {
            if (b == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Zero no good");
                alert.show();
            }
            return a / b;
        });
        operators.put("%", (a, b) -> a % b);

        inputLeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                if (!newVal.matches("-?(0|[1-9]\\d*)")) {
                    inputLeft.setText(newVal.replaceAll("[^\\d-]", ""));
                }
            }
        });

        inputRight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                if (!newVal.matches("-?(0|[1-9]\\d*)")) {
                    inputRight.setText(newVal.replaceAll("[^\\d-]", ""));
                }
            }
        });
    }

    public void MyButtonClick(ActionEvent actionEvent) {
        try {
            Button btn = (Button) actionEvent.getSource();
            String input = btn.getText();
            Double leftText = Double.parseDouble(inputLeft.getText());
            Double rightText = Double.parseDouble(inputRight.getText());
            double result = operators.get(input).apply(leftText, rightText);

            outputLabel.setText(String.format("%.2f", result));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid calculation: " + e.getMessage());
            alert.show();
        }
    }
}
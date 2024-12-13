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

public class MyController {
    @FXML
    private Label outputLabel;
    @FXML
    private TextField inputLeft;
    @FXML
    private TextField inputRight;
    @FXML
    private Button buttonPlus;
    @FXML
    private Button buttonMinus;
    @FXML
    private Button buttonStar;
    @FXML
    private Button buttonPer;
    @FXML
    private Button buttonSzazalek;

    public void initialize() {
        buttonPlus.setOnMouseClicked(myMouseClickEvent());
        buttonMinus.setOnMouseClicked(myMouseClickEvent());
        buttonStar.setOnMouseClicked(myMouseClickEvent());
        buttonPer.setOnMouseClicked(myMouseClickEvent());
        buttonSzazalek.setOnMouseClicked(myMouseClickEvent());


        inputLeft.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                if (!newVal.matches("-?(0|[1-9]\\d*)")) {
                    //inputLeft.setText(inputLeft.getText().substring(0,inputLeft.getLength()-1));
                    inputLeft.setText(newVal.replaceAll("[^\\d-]", ""));
                }
            }
        });

        inputRight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                if (!newVal.matches("-?(0|[1-9]\\d*)")) {
                    //inputLeft.setText(inputLeft.getText().substring(0,inputLeft.getLength()-1));
                    inputRight.setText(newVal.replaceAll("[^\\d-]", ""));
                }
            }
        });
    }

    private EventHandler<MouseEvent> myMouseClickEvent() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Button o = (Button) mouseEvent.getSource();
                MyButtonClick(o.getText());
            }
        };
    }


    protected void MyButtonClick(String inputparam) {
        String leftText = inputLeft.getText();
        String rightText = inputRight.getText();

        try {
            double leftOperand = Double.parseDouble(leftText);
            double rightOperand = Double.parseDouble(rightText);
            double result = 0;
            switch (inputparam) {
                case "+":
                    result = leftOperand + rightOperand;
                    break;
                case "-":
                    result = leftOperand - rightOperand;
                    break;
                case "*":
                    result = leftOperand * rightOperand;
                    break;
                case "%":
                    result = leftOperand % rightOperand;
                    break;
                case "/":
                    result = leftOperand / rightOperand; // Handle division by zero
                    if (rightOperand == 0) {
                        throw new ArithmeticException("Cannot divide by zero.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation.");
            }

            outputLabel.setText(new DecimalFormat("##.00").format(result));
            if (result instanceof Double d) {
                outputLabel.setText(new DecimalFormat("##.00").format(d));
            } else {
                outputLabel.setText("Error: Invalid calculation.");
            }


            //JAVA 8+ környezettől nincsen beépítve a ScriptEngineManager :( megoldás lehet a GraalVM
            //String operation = String.join("", leftText, inputparam, rightText);
            //outputLabel.setText(new DecimalFormat("##.00").format(new ScriptEngineManager().getEngineByName("JavaScript").eval(operation)).toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid calculation: " + e.getMessage());
            alert.show();
        }
    }
}
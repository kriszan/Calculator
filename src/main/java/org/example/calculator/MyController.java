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
                    inputLeft.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });

        inputRight.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldVal, String newVal) {
                if (!newVal.matches("-?(0|[1-9]\\d*)")) {
                    //inputLeft.setText(inputLeft.getText().substring(0,inputLeft.getLength()-1));
                    inputRight.setText(newVal.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private EventHandler<MouseEvent> myMouseClickEvent() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Button o = (Button) mouseEvent.getSource();
                MyButtonClick(o.getText().toString());
            }
        };
    }


    protected void MyButtonClick(String inputparam) {
        String leftText = inputLeft.getText();
        String rightText = inputRight.getText();

        try {
            String operation = String.join("", leftText, inputparam, rightText);

            //ScriptEngineManager mgr = new ScriptEngineManager(null);
            //ScriptEngine engine = new ScriptEngineManager().getEngineByName("");
            //Object tmp = engine.eval(operation);
            //System.err.print(new DecimalFormat("##.00").format(mgr.getEngineByName("JavaScript").eval(operation).toString()));
            //System.err.print(new DecimalFormat("##.00").format(mgr.getEngineByName("JavaScript").eval(operation).toString()));
            //outputLabel.setText(new DecimalFormat("##.00").format(.toString()));
            outputLabel.setText(new DecimalFormat("##.00").format(new ScriptEngineManager().getEngineByName("JavaScript").eval(operation)).toString());
        } catch (Exception e) {
            System.err.print("Invalid param: " + leftText + " || " + rightText);


            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setContentText(e.toString());
            a.show();
        }
    }
}
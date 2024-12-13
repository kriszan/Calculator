module org.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.scripting;
    requires java.logging;


    opens org.example.calculator to javafx.fxml;
    exports org.example.calculator;
}
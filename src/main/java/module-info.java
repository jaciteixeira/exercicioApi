module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;


    opens com.example to javafx.fxml;
    exports com.example;
}

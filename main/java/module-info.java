module com.example.insurance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.insurance to javafx.fxml;
    exports com.example.insurance;
}
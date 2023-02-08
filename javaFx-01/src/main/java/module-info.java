module com.example.javafx01 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.javafx01 to javafx.fxml;
    exports com.example.javafx01;
}
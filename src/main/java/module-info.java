module com.example.gymproj {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.gymproj to javafx.fxml;
    exports com.example.gymproj;
}
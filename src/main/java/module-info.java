module com.example.studentgradetracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentgradetracker to javafx.fxml;
    exports com.example.studentgradetracker;
}
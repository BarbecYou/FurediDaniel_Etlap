module hu.home.etlap {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.home.etlap to javafx.fxml;
    exports hu.home.etlap;
}
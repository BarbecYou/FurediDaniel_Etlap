module hu.home.etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hu.home.etlap to javafx.fxml;
    exports hu.home.etlap;
}
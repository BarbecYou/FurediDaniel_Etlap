package hu.home.etlap;

import javafx.scene.control.Alert;

public class Controller {

    public void alert(Alert.AlertType alertType, String text){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(text);
        alert.showAndWait();
    }
}

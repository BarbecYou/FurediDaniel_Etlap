package hu.home.etlap;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class InsertController extends Controller{
    @FXML
    private Button insertButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Spinner<Integer> priceSpinner;
    @FXML
    private ComboBox<String> categoryCombo;

    private Db db;

    @FXML
    public void initialize() {
        categoryCombo.setItems(FXCollections.observableArrayList(
                new String("előétel"),
                new String("főétel"),
                new String("desszert")
        ));
        SpinnerValueFactory<Integer> valFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0, 50);
        priceSpinner.setValueFactory(valFac);
    }

    @FXML
    public void insertClick(){
        String name = nameField.getText().toString().trim();
        String desc = descriptionArea.getText().toString().trim();
        int price = priceSpinner.getValue();
        String cat = categoryCombo.getValue().toString().trim();
        Food newFood = new Food(name, desc, price, cat);
        try {
            db = new Db();
            if (db.createData(newFood)){
                alert(Alert.AlertType.WARNING, "Új adat sikeresen beszúrva!");
                nameField.setText("");
                descriptionArea.setText("");
                priceSpinner.getValueFactory().setValue(0);
                categoryCombo.setValue("Kategóriák");
            }else {
                alert(Alert.AlertType.ERROR, "Adat felvétele hibába ütközött, próbáld meg újra!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
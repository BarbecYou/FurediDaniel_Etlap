package hu.home.etlap;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MainController extends Controller{

    @FXML
    private TableView<Food> tableView;
    @FXML
    private TableColumn<Food, String> nameCol;
    @FXML
    private TableColumn<Food, String> categoryCol;
    @FXML
    private TableColumn<Food, Integer> priceCol;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button newButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button priceChangeButton;
    @FXML
    private RadioButton fixValueRadio;
    @FXML
    private ToggleGroup priceChangeGroup;
    @FXML
    private RadioButton percentValueRadio;
    @FXML
    private Spinner<Integer> priceChangeSpinner;

    private Db db;

    @FXML
    private void initialize(){
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadData();
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                descriptionLabel.setText(newSelection.getDescription());
            }
        });
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 3000, 0, 50);
        priceChangeSpinner.setValueFactory(valueFactory);
        fixValueRadio.setOnAction((e) -> {
            SpinnerValueFactory<Integer> valFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 3000, 0, 50);
            priceChangeSpinner.setValueFactory(valFac);
        });
        percentValueRadio.setOnAction((e) -> {
            SpinnerValueFactory<Integer> valFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 50, 0, 5);
            priceChangeSpinner.setValueFactory(valFac);
        });
    }

    public void loadData() {
        try {
            db = new Db();
            tableView.getItems().clear();
            tableView.getItems().addAll(db.readData());
        } catch (SQLException e) {
            Platform.runLater(() -> {
                alert(Alert.AlertType.ERROR, "Sikertelen kapcsolat az adatb??zissal!");
                Platform.exit();
            });
        }
    }

    @FXML
    public void newData(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("insert-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("??tel felv??tele");
        stage.setScene(scene);
        stage.show();
        //TODO: adatok felv??tele
    }

    @FXML
    public void deleteData(ActionEvent actionEvent) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert(Alert.AlertType.WARNING, "V??lasszon ki egy ??telt a t??rl??shez!");
            return;
        }
        Food selected = tableView.getSelectionModel().getSelectedItem();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan t??r??lni szeretn?? az ??telt?");
        Optional<ButtonType> buttonTypeOptional = confirm.showAndWait();

        if (buttonTypeOptional.isEmpty()){
            alert(Alert.AlertType.ERROR,"Ismeretlen hiba t??rt??nt!");
            return;
        }
        ButtonType clicked = buttonTypeOptional.get();
        if (clicked.equals(ButtonType.OK) || clicked.equals(ButtonType.YES)){
            //TODO: adatok t??rl??se
        }
    }

    @FXML
    public void priceChange(ActionEvent actionEvent) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        Alert confirm;
        if (selectedIndex == -1){
            confirm = new Alert(Alert.AlertType.CONFIRMATION, "Biztosan meg szeretn??d emelni az ??sszes ??tel ??r??t?");
        }else {
            Food selected = tableView.getSelectionModel().getSelectedItem();
            confirm = new Alert(Alert.AlertType.CONFIRMATION, String.format("Biztosan szeretn??d emelni ennek az ??telnek az ??r??t: %s?", selected.getName()));
        }
        Optional<ButtonType> buttonTypeOptional = confirm.showAndWait();

        if (buttonTypeOptional.isEmpty()){
            alert(Alert.AlertType.ERROR,"Ismeretlen hiba t??rt??nt!");
            return;
        }
        ButtonType clicked = buttonTypeOptional.get();
        if (clicked.equals(ButtonType.OK) || clicked.equals(ButtonType.YES)){
            //TODO: adatok friss??t??se
        }
    }
}
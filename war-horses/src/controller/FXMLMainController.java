package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class FXMLMainController implements Initializable {

    @FXML
    private BorderPane rootModule;
    @FXML
    private ComboBox<String> levelComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> levelList = FXCollections.observableArrayList("Beginner", "Amateur", "Expert");
        levelComboBox.setItems(levelList);
        levelComboBox.setValue("Beginner");
    }

    @FXML
    private void OnHandlePlay(ActionEvent event) {
        String gameLevel = levelComboBox.getSelectionModel().getSelectedItem().toLowerCase();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLGame.fxml"));
            rootModule.setCenter(loader.load());
            FXMLGameController control = loader.getController();
            control.setGameLevel(gameLevel);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

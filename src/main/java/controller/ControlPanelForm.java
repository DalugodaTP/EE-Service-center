package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlPanelForm {
    public AnchorPane controlPanelPane;

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("CustomerForm window in the path is missing");
        }
    }

    public void InventoryButtonOnAction(ActionEvent actionEvent) {
    }

    public void SettingsButtonOnAction(ActionEvent actionEvent) {
    }

    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
    }
}

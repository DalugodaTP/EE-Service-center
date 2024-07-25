package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlPanelForm {
    public AnchorPane controlPanelPane;

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("CustomerForm window in the path is missing");
        }
    }

    public void SettingsButtonOnAction(ActionEvent actionEvent) {
        //        No implementation
    }

    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
        //        Current Pane
    }

    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderManagementForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Order Management window in the path is missing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/InventoryForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Inventory window in the path is missing");
        }
    }
}

package controller;

import dto.StaffDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlPanelForm {
    public AnchorPane controlPanelPane;
    public Label lblUserName;
    private StaffDto loggedStaff;

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Here CustomerForm window in the path is missing");
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
            System.out.println("Inventory window in the path is missing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void logOutButtonOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Login window in the path is missing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void orderDetailsButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)controlPanelPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderDetailViewForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Inventory window in the path is missing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void initLoggedUser(StaffDto staff){
        //--Initialize logged user from LoginFormController
        this.loggedStaff = staff;
          //Authenticate user
          //Change the label text
        lblUserName.setText(staff.getEmail());

        //--Loading initial view

        //--Initial Login welcome Alert

    }
}

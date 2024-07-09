package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderManagementFormController {
    public AnchorPane orderManagementPane;

    public void dashboardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void settingButtonOnAction(ActionEvent actionEvent) {
        //        No implemenation
    }

    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        //        Current pane
    }

    public void manageCustomersButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)orderManagementPane.getScene().getWindow();
        try {
            //stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ManageCustomersForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("CustomerForm window in the path is missing");
        }
    }
}

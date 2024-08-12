package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import dao.util.BoType;
import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManageCustomersFormController {
    //--Table
    public JFXTreeTableView tblManageCustomers;

    //--Columns
    public TreeTableColumn customerIdCol;
    public TreeTableColumn firstNameCol;
    public TreeTableColumn lastNameCol;
    public TreeTableColumn emailCol;
    public TreeTableColumn contactNoCol;

    //--Text fields
    public MFXTextField txtCustomerId;
    public MFXTextField txtFirstName;
    public MFXTextField txtlastName;
    public MFXTextField txtCustomerAddress;
    public MFXTextField txtEmailAddress;
    public MFXTextField txtContactNumber;
    private CustomerTm selectedCustomer;

    //--Create an instance of the customerModel using factory design pattern
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);

    public void initialize() throws SQLException, ClassNotFoundException {
        loadCustomerTable();

        //--Get Selection Model

    }

    //--Set selected row to the fields
    private void setData(ObservableMap<Integer, CustomerTm> newValue) {
        if (newValue != null && !newValue.isEmpty()) {
            selectedCustomer = newValue.values().iterator().next();
            if (selectedCustomer != null) {
                txtCustomerId.setText(selectedCustomer.getId());
                txtFirstName.setText(selectedCustomer.getFirstName());
                txtlastName.setText(selectedCustomer.getLastName());
                txtCustomerAddress.setText(selectedCustomer.getAddress());
                txtEmailAddress.setText(selectedCustomer.getEmailAddress());
                txtContactNumber.setText(selectedCustomer.getContactNumber());
            }
        }
    }

    private void loadCustomerTable() throws SQLException, ClassNotFoundException {

    }

    private void deleteCustomer(CustomerTm id) throws SQLException, ClassNotFoundException {
        Alert confirmAlert = new Alert
                (Alert.AlertType.CONFIRMATION,
                        "Do you want to delete this item?",
                        ButtonType.YES, ButtonType.NO);

        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            if (customerBo.deleteCustomer(id.getId())) {
                operationSuccessAlert("Deleted!", "Item Deleted Successfully!");
            }
            confirmAlert.close();
            clearFields();
//            tblManageCustomers.setItems(getUsers());
        }

    }

    private ObservableList<CustomerTm> getUsers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> returnList = FXCollections.observableArrayList();


        return returnList;
    }

    public void dashBoardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblManageCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void SettingsButtonOnAction(ActionEvent actionEvent) {
        //--No implementations
    }

    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblManageCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderManagementForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, NullPointerException {
//        //--Create an object and store the data from the fields
//        CustomerDto c = new CustomerDto(txtCustomerId.getText(),
//                txtCustomerName.getText(),
//                txtCustomerAddress.getText(),
//                Double.parseDouble(txtCustomerSalary.getText()
//                ));
//        //--Use customerModelImpl to save the customer
//        boolean isCustomerSaved = customerBo.saveCustomer(c);
//
//        if (isCustomerSaved){
//            new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
//           initialize();
//           loadCustomerTable();
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtFirstName.clear();
        txtlastName.clear();
        txtCustomerAddress.clear();
        txtEmailAddress.clear();
        txtContactNumber.clear();
        txtCustomerId.setEditable(false);
    }

    public void updateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        CustomerDto c = new CustomerDto(
//                txtCustomerId.getText(),
//                txtCustomerName.getText(),
//                txtCustomerAddress.getText(),
//                Double.parseDouble(txtCustomerSalary.getText())
//        );

        //--Use customerModel to update the entry
//        boolean isCustomerUpdated = customerBo.updateCustomer(c);
//
//        if (isCustomerUpdated){
//            new Alert(Alert.AlertType.INFORMATION,"Customer "+c.getId()+" Updated!").show();
//            loadCustomerTable();
//            clearFields();
//        }

    }

    void operationSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void ManageCustomersButtonOnAction(ActionEvent actionEvent) {
        //--This window
    }

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblManageCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/InventoryForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Inventory window in the path is missing");
        }
    }

    public void viewReportButtonOnAction(ActionEvent actionEvent){
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/customer_report.jrxml");
            //
            //
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
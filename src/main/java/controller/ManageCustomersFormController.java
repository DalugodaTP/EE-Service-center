package controller;

import com.jfoenix.controls.JFXButton;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import static java.awt.event.MouseEvent.MOUSE_PRESSED;

public class ManageCustomersFormController {

    public MFXTableView<CustomerTm> tblManageCustomers;
    public MFXTextField txtCustomerId;
    public MFXTextField txtCustomerName;
    public MFXTextField txtCustomerAddress;
    public MFXTextField txtCustomerSalary;

    private CustomerTm selectedCustomer;

    //--Create an instance of the customerModel
    CustomerModel customerModel = new CustomerModelImpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        loadCustomerTable();
        tblManageCustomers.getSelectionModel().selectionProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    //--Set selected row to the fields
    private void setData(ObservableMap<Integer, CustomerTm> newValue) {
        if (newValue != null && !newValue.isEmpty()) {
            selectedCustomer = newValue.values().iterator().next();
            if (selectedCustomer != null) {
                txtCustomerId.setText(selectedCustomer.getId());
                txtCustomerName.setText(selectedCustomer.getName());
                txtCustomerAddress.setText(selectedCustomer.getAddress());
                txtCustomerSalary.setText(String.valueOf(selectedCustomer.getSalary()));
            }
        }
    }

    private void loadCustomerTable() throws SQLException, ClassNotFoundException {
        tblManageCustomers.setPrefHeight(100);

        MFXTableColumn<CustomerTm> customerIdColumn =
                new MFXTableColumn<>("Customer ID", false, Comparator.comparing(CustomerTm::getId));
        MFXTableColumn<CustomerTm> customerNameColumn =
                new MFXTableColumn<>("Name", false, Comparator.comparing(CustomerTm::getName));
        MFXTableColumn<CustomerTm> customerAddressColumn =
                new MFXTableColumn<>("Address", false, Comparator.comparing(CustomerTm::getAddress));
        MFXTableColumn<CustomerTm> customerSalaryColumn =
                new MFXTableColumn<>("Salary", false, Comparator.comparing(CustomerTm::getSalary));
        MFXTableColumn<CustomerTm> customerDeleteColumn =
                new MFXTableColumn<>("Action", false);

        customerIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getId));
        customerNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getName));
        customerAddressColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getAddress));
        customerSalaryColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getSalary));

        customerDeleteColumn.setRowCellFactory(item -> {
            MFXTableRowCell<CustomerTm, String> mfxTableRowCell = new MFXTableRowCell<>(CustomerTm::getAction);
            MFXButton btnDelete = new MFXButton("Delete");
            btnDelete.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                try {
                    deleteCustomer(item);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            mfxTableRowCell.setLeadingGraphic(btnDelete);
            mfxTableRowCell.setAlignment(Pos.CENTER);
            mfxTableRowCell.mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    mfxTableRowCell.setMouseTransparent(false);
                }
            });

            return mfxTableRowCell;
        });

        customerIdColumn.setAlignment(Pos.CENTER);
        customerNameColumn.setAlignment(Pos.CENTER);
        customerAddressColumn.setAlignment(Pos.CENTER);
        customerSalaryColumn.setAlignment(Pos.CENTER);
        customerDeleteColumn.setAlignment(Pos.CENTER);

        double tableWidth = tblManageCustomers.getPrefWidth();
        double columnWidth = (tableWidth) / 5;

        customerIdColumn.setPrefWidth(columnWidth);
        customerNameColumn.setPrefWidth(columnWidth);
        customerAddressColumn.setPrefWidth(columnWidth);
        customerSalaryColumn.setPrefWidth(columnWidth);
        customerDeleteColumn.setPrefWidth(columnWidth);

        tblManageCustomers.getTableColumns().addAll(
                customerIdColumn,
                customerNameColumn,
                customerAddressColumn,
                customerSalaryColumn,
                customerDeleteColumn
        );

        setFilters();
        tblManageCustomers.setItems(getUsers());
        tblManageCustomers.getStylesheets().add(getClass().getResource("../css/TableStyles.css").toExternalForm());
        tblManageCustomers.getStyleClass().add("mfx-table-view");
    }

    private void deleteCustomer(CustomerTm id) throws SQLException, ClassNotFoundException {
        Alert confirmAlert = new Alert
                (Alert.AlertType.CONFIRMATION,
                        "Do you want to delete this item?",
                        ButtonType.YES, ButtonType.NO);

        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            if (customerModel.deleteCustomer(id)) {
                operationSuccessAlert("Deleted!", "Item Deleted Successfully!");
            }
            confirmAlert.close();
            clearFields();
            tblManageCustomers.setItems(getUsers());
            return;
        }

    }

    private void setFilters() {
        tblManageCustomers.getFilters().addAll(
                new StringFilter<>("Id", CustomerTm::getId),
                new StringFilter<>("Name", CustomerTm::getName),
                new StringFilter<>("Address", CustomerTm::getAddress),
                new DoubleFilter<>("Salary", CustomerTm::getSalary)
        );
    }

    private ObservableList<CustomerTm> getUsers() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> returnList = FXCollections.observableArrayList();
        List<CustomerDto> dtoList = customerModel.allCustomers();

        for (CustomerDto dto : dtoList) {
            JFXButton deleteBtn = new JFXButton("Delete");

            CustomerTm customerTm = new CustomerTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getAddress(),
                    dto.getSalary(),
                    ""
            );

            returnList.add(customerTm);
        }

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
        //--Create an object and store the data from the fields
        CustomerDto c = new CustomerDto(txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText()
                ));
        //--Use customerModelImpl to save the customer
        boolean isCustomerSaved = customerModel.saveCustomer(c);

        if (isCustomerSaved){
            new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
           initialize();
           loadCustomerTable();
        }
    }

    private void clearFields() {
        txtCustomerSalary.clear();
        txtCustomerAddress.clear();
        txtCustomerName.clear();
        txtCustomerId.clear();
        txtCustomerId.setEditable(false);
    }

    public void updateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDto c = new CustomerDto(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );

        //--Use customerModel to update the entry
        boolean isCustomerUpdated = customerModel.updateCustomer(c);

        if (isCustomerUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Customer "+c.getId()+" Updated!").show();
            loadCustomerTable();
            clearFields();
        }

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
}
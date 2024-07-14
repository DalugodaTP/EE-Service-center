package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
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
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;

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
                new MFXTableColumn<>("Options", false);

        customerIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getId){{
            setAlignment(Pos.CENTER); }});
        customerNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getName){{
            setAlignment(Pos.CENTER); }});
        customerAddressColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getAddress){{
            setAlignment(Pos.CENTER); }});
        customerSalaryColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerTm::getSalary){{
            setAlignment(Pos.CENTER); }});

        customerDeleteColumn.setRowCellFactory(item -> {
            MFXTableRowCell<CustomerTm, JFXButton> mfxTableRowCell = new MFXTableRowCell<>(CustomerTm::getBtn);
            mfxTableRowCell.setAlignment(Pos.CENTER);
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
                    deleteBtn
            );

            deleteBtn.setOnAction(actionEvent -> {
                customerModel.deleteCustomer(dto);
                returnList.remove(customerTm);
            });

            returnList.add(customerTm);
        }

        return returnList;
    }

    public void dashboardButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblManageCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ControlPanelForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void settingButtonOnAction(ActionEvent actionEvent) {
        //--No implementations
    }

    public void orderManagementButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblManageCustomers.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OrderManagementForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("Dashboard window in the path is missing");
        }
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {
        //--Create an object and store the data from the fields
        CustomerDto c = new CustomerDto(txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText()
                ));
        //--Concat and get the query
        String sql = "INSERT INTO customer VALUES('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"',"+c.getSalary()+")";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtCustomerSalary.clear();
        txtCustomerAddress.clear();
        txtCustomerName.clear();
        txtCustomerId.clear();
        txtCustomerId.setEditable(false);
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        CustomerDto c = new CustomerDto(
                txtCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                Double.parseDouble(txtCustomerSalary.getText())
        );
        String sql = "UPDATE customer SET name='"+c.getName()+"', address='"+c.getAddress()+"', salary="+c.getSalary()+" WHERE id='"+c.getId()+"'";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer "+c.getId()+" Updated!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
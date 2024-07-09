package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import dto.CustomerDto;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;

public class ManageCustomersFormController {

    public MFXTableView tblManageCustomers;

    public void initialize(){

        loadItemTable();
    }

    private void loadItemTable() {
        //--Define the height of the table
        tblManageCustomers.setPrefHeight(100);
        //--Create Observable list to pass the data into the table
        ObservableList<CustomerDto> tmList = FXCollections.observableArrayList();

        //--Create columns for the table
        MFXTableColumn<CustomerDto> customerIdColumn = new MFXTableColumn<>("Customer ID", false, Comparator.comparing(CustomerDto::getId));
        MFXTableColumn<CustomerDto> customerNameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(CustomerDto::getName));
        MFXTableColumn<CustomerDto> customerAddressColumn = new MFXTableColumn<>("Address", false, Comparator.comparing(CustomerDto::getAddress));
        MFXTableColumn<CustomerDto> customerSalaryColumn = new MFXTableColumn<>("Salary", false, Comparator.comparing(CustomerDto::getSalary));


        customerIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getId){{
            //--Alignment of rows
            setAlignment(Pos.CENTER);
        }});
        customerNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getName){{
            //--Alignment of rows
            setAlignment(Pos.CENTER);
        }});
        customerAddressColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getAddress){{
            //--Alignment of rows
            setAlignment(Pos.CENTER);
        }});
        customerSalaryColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getSalary){{
            setAlignment(Pos.CENTER);
        }});

        //--Set the alignment of the table headings
        customerIdColumn.setAlignment(Pos.CENTER);
        customerNameColumn.setAlignment(Pos.CENTER);
        customerAddressColumn.setAlignment(Pos.CENTER);
        customerSalaryColumn.setAlignment(Pos.CENTER);

        //--Customize the table width
        // Calculate the width of each column based on the table width
        double tableWidth = tblManageCustomers.getPrefWidth();
        double columnWidth = (tableWidth-40) / 4;

        System.out.println("Column width is "+columnWidth);

        customerIdColumn.setPrefWidth(columnWidth);
        customerNameColumn.setPrefWidth(columnWidth);
        customerAddressColumn.setPrefWidth(columnWidth);
        customerSalaryColumn.setPrefWidth(columnWidth);

        //--Add the columns to the table
        tblManageCustomers.getTableColumns().addAll(
                customerIdColumn,
                customerNameColumn,
                customerAddressColumn,
                customerSalaryColumn
        );

        //--Ser filters to the columns
        tblManageCustomers.getFilters().addAll(
                new StringFilter<>("Id", CustomerDto::getId),
                new StringFilter<>("Name", CustomerDto::getName),
                new StringFilter<>("Address", CustomerDto::getAddress),
                new DoubleFilter<>("Salary", CustomerDto::getSalary)
        );

        //--Create the sql statement
        String sql = "SELECT * FROM customer";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            //--Return a set
            ResultSet result = stm.executeQuery(sql);

            //--Load into the observable arraylist from db
            while(result.next()){
                JFXButton btn = new JFXButton("Delete");

                CustomerDto c = new CustomerDto(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4)
                );
                //--Bundle the action of deletion at the place of creating the btn reference (lamda expression)
                btn.setOnAction(actionEvent -> {
                   // deleteCustomer(c.getId());
                });

                tmList.add(c);
            }

            //--Populate the table using the observable list
            tblManageCustomers.setItems(tmList);

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("failed to fetch customer list");
        }

        // Load the CSS file
        tblManageCustomers.getStylesheets().add(getClass().getResource("../css/TableStyles.css").toExternalForm());
        tblManageCustomers.getStyleClass().add("mfx-table-view");


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

    public void inventoryButtonOnAction(ActionEvent actionEvent) {
    }

    public void settingButtonOnAction(ActionEvent actionEvent) {
    }
}

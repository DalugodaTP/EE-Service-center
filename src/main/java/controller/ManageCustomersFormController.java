package controller;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import dto.CustomerDto;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        //MFXTableColumn<CustomerDto> customerOptionsColumn = new MFXTableColumn<>("Options", false, null);


        customerIdColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getId));
        customerNameColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getName));
        customerAddressColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getAddress));
        customerSalaryColumn.setRowCellFactory(item -> new MFXTableRowCell<>(CustomerDto::getSalary));
        //customerOptionsColumn.setRowCellFactory(item -> new MFXTableRowCell<>(null));

        //--Customize the table width
        customerIdColumn.setPrefWidth(150);
        customerNameColumn.setPrefWidth(150);
        customerAddressColumn.setPrefWidth(150);
        customerSalaryColumn.setPrefWidth(150);
       // customerOptionsColumn.setPrefWidth(150);

        //--Add the columns to the table
        tblManageCustomers.getTableColumns().addAll(
                customerIdColumn,
                customerNameColumn,
                customerAddressColumn,
                customerSalaryColumn
                //,customerOptionsColumn
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


    }
}

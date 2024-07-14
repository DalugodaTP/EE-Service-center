package model.impl;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDto dto) {
        return false;
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {
        //--Create an arrayList to store the objects
        List<CustomerDto> list = new ArrayList<>();

        //--Create the sql statement
        String sql = "SELECT * FROM customer";

        //--Singleton connection to pass the SQL
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Execute the query and get the result set
        ResultSet result = pstm.executeQuery();

        //--Process the result set
        while (result.next()) {
            //--Add the result set into the list as CustomerDto
            list.add(new CustomerDto(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            ));
        }

        return list;
    }

    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }
}

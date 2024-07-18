package model.impl;

import db.DBConnection;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModelImpl implements CustomerModel {
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        //--Prepare SQL Query
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";

        //--Retrieve the db connection
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Set the values to the prepareStatement
        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setDouble(4, dto.getSalary());

        int result = pstm.executeUpdate();
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";

        //--Get the connection
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Set values to the fields
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setDouble(3, dto.getSalary());
        pstm.setString(4, dto.getId());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, dto.getId());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
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
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        CustomerDto resultDto = new CustomerDto();
        String sql = "SELECT * FROM customer WHERE id = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet result = pstm.executeQuery();

        //--Process the result set
        while (result.next()) {
            //--Add the result set into the list as CustomerDto
            resultDto = new CustomerDto(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            );
        }
        return resultDto;
    }
}

//This is the main branch
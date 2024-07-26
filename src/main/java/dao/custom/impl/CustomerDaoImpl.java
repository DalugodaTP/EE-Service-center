package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import dao.custom.CustomerDao;
import entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


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

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(), entity.getSalary());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(), entity.getId());
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";
        return CrudUtil.execute(sql,value);
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        //--Create an arrayList to store the objects
        List<Customer> list = new ArrayList<>();

        //--Create the sql statement
        String sql = "SELECT * FROM customer";


        //--Execute the query and get the result set
        ResultSet result = CrudUtil.execute(sql);


        //--Process the result set
        while (result.next()) {
            //--Add the result set into the list as CustomerDto
            list.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            ));
        }

        return list;
    }
}

//This is the main branch
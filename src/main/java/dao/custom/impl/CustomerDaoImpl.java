package dao.custom.impl;

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
        //--Prepare SQL Query
        String sql = "INSERT INTO customer VALUES(?,?,?,?)";

        //--Retrieve the db connection
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Set the values to the prepareStatement
        pstm.setString(1, entity.getId());
        pstm.setString(2, entity.getName());
        pstm.setString(3, entity.getAddress());
        pstm.setDouble(4, entity.getSalary());

        int result = pstm.executeUpdate();
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";

        //--Get the connection
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Set values to the fields
        pstm.setString(1, entity.getName());
        pstm.setString(2, entity.getAddress());
        pstm.setDouble(3, entity.getSalary());
        pstm.setString(4, entity.getId());

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE id=?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, value);

        int result = pstm.executeUpdate();

        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        //--Create an arrayList to store the objects
        List<Customer> list = new ArrayList<>();

        //--Create the sql statement
        String sql = "SELECT * FROM customer";

        //--Singleton connection to pass the SQL
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);

        //--Execute the query and get the result set
        ResultSet result = pstm.executeQuery();

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
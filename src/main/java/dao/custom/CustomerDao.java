package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDao extends CrudDao<Customer> {
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;

}

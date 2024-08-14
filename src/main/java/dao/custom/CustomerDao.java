package dao.custom;

import dao.CrudDao;
import dto.CustomerDto;
import entity.CustomerEntity;

import java.sql.SQLException;

public interface CustomerDao extends CrudDao<CustomerEntity> {
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;

}

package bo.custom.impl;

import bo.custom.CustomerBo;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.util.DaoType;
import dao.util.HibernateUtil;
import dto.CustomerDto;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    ObjectMapper mapper = new ObjectMapper();
    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.update(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.Delete(id);
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {
        List<Customer> entityList = customerDao.getAll();
        List<CustomerDto> list = new ArrayList<>();
        for (Customer customer:entityList) {
            list.add( new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }
        return list;
    }
}

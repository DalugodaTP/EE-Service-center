package bo.custom.impl;

import bo.custom.CustomerBo;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.util.DaoType;
import dto.CustomerDto;
import entity.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    ObjectMapper mapper = new ObjectMapper();
    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new CustomerEntity(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getContact_no()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.update(new CustomerEntity(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getContact_no()
        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.Delete(id);
    }

    @Override
    public List<CustomerDto> allCustomers() throws SQLException, ClassNotFoundException {
        List<CustomerEntity> entityList = customerDao.getAll();
        List<CustomerDto> list = new ArrayList<>();
        for (CustomerEntity customerEntity :entityList) {
            list.add( new CustomerDto(
                    customerEntity.getId(),
                    customerEntity.getFirstName(),
                    customerEntity.getLastName(),
                    customerEntity.getEmail(),
                    customerEntity.getContact_no()
            ));
        }
        return list;
    }
}

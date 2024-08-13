package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.HibernateUtil;
import dto.CustomerDto;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        //--begin Transaction
        Transaction transaction = session.beginTransaction();

        //--find the customer using id and store as a local variable (Persistent state)
        Customer customer = session.find(Customer.class, entity.getId());

        //--update data of that customer
//        customer.setName(entity.getName());
//        customer.setAddress(entity.getAddress());
//        customer.setSalary(entity.getSalary());

        //--save back the customer
        session.save(customer);

        //--Commit Transaction
        transaction.commit();

        //--Detached state
        session.close();

        return true;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        //--Get session from util package
        Session session = HibernateUtil.getSession();

        //--Begin Transaction
        Transaction transaction = session.beginTransaction();

        //--delete the customer with pk that match the value
        session.delete(session.find(Customer.class,value));

        //--Commit Transaction
        transaction.commit();

        //--Detached state
        session.close();

        return true;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        session.close();
        return list;
    }

    public CustomerDto searchCustomer(String id) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer c WHERE id =:id");
        query.setParameter("id", id);
        try {
            CustomerDto entity = (CustomerDto) query.getSingleResult();
            session.close();
            return entity;
        } catch (NoResultException nre) {
            session.close();
            return null;
        }
    }

}


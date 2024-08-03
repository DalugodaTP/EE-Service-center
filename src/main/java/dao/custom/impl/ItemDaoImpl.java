package dao.custom.impl;

import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.CustomerDto;
import dto.ItemDto;
import dao.custom.ItemDao;
import entity.Customer;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        //--Get a session from HibernateUtil
        Session session = HibernateUtil.getSession();

        //--Begin a Transaction
        Transaction transaction = session.beginTransaction();

        //--Use the session to save
        session.save(entity);

        //--Permanently save it into the db
        transaction.commit();

        //--Close the session after saving
        session.close();

        //--Return true if the session was sucessful
        return true;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        //--Get a session from HibernateUtil
        Session session = HibernateUtil.getSession();

        //--Start a new Transaction
        Transaction transaction = session.beginTransaction();

        //--Use session to find the entry and then update the object
        Item item = session.find(Item.class, entity.getCode());

        //--Set new values to the object
        item.setDescription(entity.getDescription());
        item.setQtyOnHand(entity.getQtyOnHand());
        item.setUnitPrice(entity.getUnitPrice());

        //--Save back the Item
        session.save(item);

        //--Commit the changes
        transaction.commit();

        //--end the session
        session.close();

        return true;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        //--Get a new session
        Session session = HibernateUtil.getSession();

        //--Start a new Trasaction
        Transaction transaction = session.beginTransaction();

        //-perform the delete
        session.delete(session.find(Item.class, value));

        //--Commit the changes
        transaction.commit();

        //--close the session
        session.close();
        return true;
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> list = query.list();
        session.close();
        return list;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item c WHERE code =:code");
        query.setParameter("code", code);
        try {
            ItemDto entity = (ItemDto) query.getSingleResult();
            session.close();
            return entity;
        } catch (NoResultException nre) {
            session.close();
            return null;
        }
    }

    @Override
    public ItemDto searchItem(String code) throws SQLException, ClassNotFoundException {
        ItemDto resultDto = new ItemDto();
        String sql = "SELECT * FROM item WHERE code = ?";

        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet result = pstm.executeQuery();

        //--Process the result set
        while (result.next()) {
            //--Add the result set into the list as CustomerDto
            resultDto = new ItemDto(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return resultDto;
    }
}



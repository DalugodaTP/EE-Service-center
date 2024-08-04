package dao.custom.impl;

import dao.DaoFactory;
import dao.util.DaoType;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY orderid DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return new OrderDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                null
            );
        }
        return null;
    }

    @Override
    public boolean save(OrderDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order = new Orders(
                dto.getOrderId(),
                dto.getDate()
        );
        order.setCustomer(session.find(Customer.class,dto.getCustId()));
        session.save(order);

        List<OrderDetailsDto> list = dto.getDto(); //dto type

        for (OrderDetailsDto detailDto:list) {
            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(detailDto.getOrderId(), detailDto.getItemCode()),
                    session.find(Item.class, detailDto.getItemCode()),
                    order,
                    detailDto.getQty(),
                    detailDto.getUnitPrice()
            );
            session.save(orderDetail);
        }
        //--If saving both Orders and OrderDetails are successful, then commit the changes
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean Delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
